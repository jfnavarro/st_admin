/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.serviceImpl;

import org.apache.commons.io.IOUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.spatialtranscriptomics.service.S3Service;
import com.spatialtranscriptomics.util.EMROutputParser;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class implements the read/write logic to the Amazon S3 file system with
 * regards to managing pipeline experiment input and output. The
 * connection to the S3 is handled in a AmazonS3Client object, which is
 * configured in mvc-dispather-servlet.xml
 */
//TODO methods share common code that can be factored out
//TODO the logic to merge output from EMR and retrieve must be re-designed
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

    private @Value("${s3.idspath}")
    String idfilesPath;

    private @Value("${s3.annotationspath}")
    String annotationsPath;

    private @Value("${s3.genomespath}")
    String genomesPath;

    private @Value("${s3.contaminantpath}")
    String contaminantPath;

    @Override
    public List<String> getInputFolders() {
        
        logger.info("Assembling pipeline experiment input folders from Amazon S3");
        ObjectListing objects = s3Client.listObjects(pipelineBucket, inputPath);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory
        
        //clean folder names and add them to result
        List<String> result = new ArrayList<String>();
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
        ObjectListing objects = s3Client.listObjects(pipelineBucket, idfilesPath);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        //clean file names and add them to result
        List<String> result = new ArrayList<String>();
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
        ObjectListing objects = s3Client.listObjects(pipelineBucket, annotationsPath);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory
        
        //add file name to results
        List<String> result = new ArrayList<String>();
        for (S3ObjectSummary o : objs) {
            result.add(getCleanName(o.getKey()));
        }

        return result;
    }

    @Override
    public List<String> getReferenceGenome() {
        
        logger.info("Assembling pipeline experiment reference genomes from Amazon S3");
        ObjectListing objects = s3Client.listObjects(pipelineBucket, genomesPath);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory
        
        //clear file names and add them to result
        List<String> result = new ArrayList<String>();
        for (S3ObjectSummary o : objs) {
            String s = o.getKey();
            String[] tokens = s.split("[/]");
            result.add(tokens[2]);
        }

        return result;
    }

    @Override
    public List<String> getContaminantGenome() {
        
        logger.info("Assembling pipeline experiment RNA filter files from Amazon S3");
        ObjectListing objects = s3Client.listObjects(pipelineBucket, contaminantPath);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory

        //clear file names and add them to result
        List<String> result = new ArrayList<String>();
        for (S3ObjectSummary o : objs) {
            String s = o.getKey();
            String[] tokens = s.split("[/]");
            result.add(tokens[2]);
        }

        return result;
    }

    @Override
    public byte[] getFeaturesAsJson(String experimentId) throws IOException {
        logger.info("About to obtain JSON features from Amazon "
                + "S3 for pipeline experiment " + experimentId);
        return new EMROutputParser().getJSON(getOutputFromS3(experimentId, "features"));
    }
    
    @Override
    public byte[] getExperimentQA(String experimentId) throws IOException {
        logger.info("About to obtain JSON qa stats from Amazon "
                + "S3 for pipeline experiment " + experimentId);
        return new EMROutputParser().getQA(getOutputFromS3(experimentId, "qa"));
    }

    @Override
    public byte[] getFeaturesAsCSV(String experimentId) throws IOException {
        logger.info("About to obtain CSV features from Amazon "
                + "S3 for pipeline experiment " + experimentId);
        return new EMROutputParser().getCSV(getOutputFromS3(experimentId, "features"));
    }

    /**
     * Helper function that returns output from an S3 experiment as a byte array.
     * @param keyword to distinguish between features, reads or qa
     * @param experimentId the ID of the experiment to chose the folder name
     * TODO this function needs redign. QA files need to be merged with
     * a special method to aggregate. Besides, it is not good to rely on filenames.
     * Ideally, an extra EMR step should be added to merge features.json, reads.json
     * and qa.json
     */
    private byte[] getOutputFromS3(String experimentId, String keyword) throws IOException {
        String path = experimentsPath + experimentId + "/output";
        logger.info("Obtaining raw data from Amazon S3 bucket " + pipelineBucket +  " at " + path);
        
        ObjectListing objects = s3Client.listObjects(pipelineBucket, path);
        List<S3ObjectSummary> objs = objects.getObjectSummaries();
        objs.remove(0); // remove root directory
        
        //merge the files in the folder filtering by keyword. Files need
        //to be merged as EMR will generate one file for each running node
        ByteArrayOutputStream bos = new ByteArrayOutputStream(30 * 1024 * 1024);
        for (S3ObjectSummary os : objs) {
            S3Object obj = s3Client.getObject(pipelineBucket, os.getKey());
            //Filter : features | reads | QA
            if (obj.getKey().contains(keyword)) {
                IOUtils.copy(obj.getObjectContent(), bos);
                bos.flush();
                obj.close();   // Close as soon as possible!
            }
        }
        
        bos.close();
        return bos.toByteArray();
    }

    /**
     * Helper function that cleans the name by returning the last string before '/'.
     * @param path the folder path
     */
    private String getCleanName(String path) {
        String[] tokens = path.split("[/]");
        return tokens[tokens.length - 1];
    }
}
