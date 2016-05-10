/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.serviceImpl;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.st.model.Feature;
import com.st.service.S3Service;
import com.st.util.EMROutputParser;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class implements the read/write logic to the Amazon S3 file system with
 * regards to managing pipeline experiment input and output. The
 * connection to the S3 is handled in a AmazonS3Client object, which is
 * configured in mvc-dispather-servlet.xml
 */
@Service
public class S3ServiceImpl implements S3Service {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(S3ServiceImpl.class);

    @Autowired
    AmazonS3Client s3Client;

    private @Value("${s3.pipelinebucket}")
    String pipelineBucket;

    private @Value("${s3.inputpath}")
    String inputPath;

    private @Value("${s3.experimentspath}")
    String experimentsPath;

    private @Value("${s3.idfilespath}")
    String idfilesPath;

    private @Value("${s3.annotationspath}")
    String annotationsPath;

    private @Value("${s3.genomespath}")
    String genomesPath;

    private @Value("${s3.bowtiepath}")
    String bowtiePath;

    @Override
    public void deleteExperimentData(String experimentId) {
        String path = experimentsPath + experimentId;
        ObjectListing objects = s3Client.listObjects(pipelineBucket, path);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        if (objs.isEmpty()) {
            return;
        }
        List<DeleteObjectsRequest.KeyVersion> keysToDelete = new ArrayList<DeleteObjectsRequest.KeyVersion>();
        for (S3ObjectSummary o : objs) {
            KeyVersion kv = new DeleteObjectsRequest.KeyVersion(o.getKey());
            keysToDelete.add(kv);
        }
        if (keysToDelete.isEmpty()) {
            return;
        }
        DeleteObjectsRequest req = new DeleteObjectsRequest(pipelineBucket);
        req.setKeys(keysToDelete);
        s3Client.deleteObjects(req);
        logger.info("Deleted pipeline experiment data on S3 for ID " + experimentId);
    }

    @Override
    public List<String> getInputFolders() {
        logger.info("Assembling pipeline experiment input folders from Amazon S3");
        List<String> result = new ArrayList<String>();

        ObjectListing objects = s3Client.listObjects(pipelineBucket, inputPath);

        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        for (S3ObjectSummary o : objs) {
            String s = o.getKey();
            String[] tokens = s.split("[/]");
            result.add(tokens[1]);
        }
        
        return result;
    }

    @Override
    public List<String> getIDFiles() {
        logger.info("Assembling pipeline experiment ID files from Amazon S3");
        List<String> result = new ArrayList<String>();

        ObjectListing objects = s3Client.listObjects(pipelineBucket,
                idfilesPath);

        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        for (S3ObjectSummary o : objs) {
            String s = o.getKey();
            String[] tokens = s.split("[/]");
            result.add(tokens[2]);
        }
        return result;
    }

    @Override
    public List<String> getReferenceAnnotation() {
        logger.info("Assembling pipeline experiment ref annotations from Amazon S3");
        List<String> result = new ArrayList<String>();

        ObjectListing objects = s3Client.listObjects(pipelineBucket,
                annotationsPath);

        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        for (S3ObjectSummary o : objs) {
            result.add(getCleanName(o.getKey()));
        }

        return result;
    }

    @Override
    public List<String> getReferenceGenome() {
        logger.info("Assembling pipeline experiment reference genomes from Amazon S3");
        List<String> result = new ArrayList<String>();

        ListObjectsRequest req = new ListObjectsRequest();
        req.setBucketName(pipelineBucket);
        req.setPrefix(genomesPath);
        // req.setDelimiter("/");
        ObjectListing objects = s3Client.listObjects(req);

        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        for (S3ObjectSummary o : objs) {
            String s = o.getKey();
            String[] tokens = s.split("[/]");
            result.add(tokens[2]);
        }

        return result;
    }

    @Override
    public List<String> getBowtieFiles() {
        logger.info("Assembling pipeline experiment Bowtie files from Amazon S3");
        List<String> result = new ArrayList<String>();

        ListObjectsRequest req = new ListObjectsRequest();
        req.setBucketName(pipelineBucket);
        req.setPrefix(bowtiePath);
        // req.setDelimiter("/");
        ObjectListing objects = s3Client.listObjects(req);

        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        for (S3ObjectSummary o : objs) {
            String s = o.getKey();
            String[] tokens = s.split("[/]");
            result.add(tokens[2]);
        }

        return result;
    }

    @Override
    public byte[] getFeaturesAsJson(String experimentId) throws IOException {
        logger.info("About to obtain JSON data from Amazon S3 for pipeline experiment " + experimentId);
        return new EMROutputParser().getJSON(getOutputFromS3(experimentId));
    }

    @Override
    public byte[] getFeaturesAsCSV(String experimentId) throws IOException {
        logger.info("About to obtain CSV data from Amazon S3 for pipeline experiment " + experimentId);
        return new EMROutputParser().getCSV(getOutputFromS3(experimentId));
    }

    @Override
    public List<Feature> getFeaturesAsList(String experimentId) throws IOException {
        logger.info("About to obtain Feature[] from Amazon S3 for pipeline experiment " + experimentId);
        return new EMROutputParser().getFeatures(getOutputFromS3(experimentId));
    }

    /**
     * Returns output from an S3 experiment as a byte array.
     */
    private byte[] getOutputFromS3(String experimentId) throws IOException {
        String path = experimentsPath + experimentId + "/output";
        logger.info("Obtaining raw data from Amazon S3 bucket " + pipelineBucket +  " at " + path);
        
        ObjectListing objects = s3Client.listObjects(pipelineBucket, path);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        ByteArrayOutputStream bos = new ByteArrayOutputStream(30 * 1024 * 1024);
        for (S3ObjectSummary os : objs) {
            S3Object obj = s3Client.getObject(pipelineBucket, os.getKey());
            IOUtils.copy(obj.getObjectContent(), bos);
            bos.flush();
            obj.close();   // Close as soon as possible!
        }
        bos.close();
        return bos.toByteArray();
    }

    
    /**
     * Cleans the name by returning the last string before '/'.
     */
    private String getCleanName(String path) {
        String[] tokens = path.split("[/]");
        return tokens[tokens.length - 1];
    }

}
