/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.serviceImpl;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
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
import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.service.S3Service;
import com.spatialtranscriptomics.util.EMROutputParser;

/**
 * This class implements the read/write logic to the Amazon S3 file system.
 * The connection to the S3 is handled in a AmazonS3Client object, which is configured in mvc-dispather-servlet.xml
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
	}
	
        @Override
	public List<String> getInputFolders() {
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
	public InputStream getFeaturesAsJson(String experimentId) {
		return new EMROutputParser().getJSON(getOutputFromS3(experimentId));
	}

	@Override
	public InputStream getFeaturesAsCSV(String experimentId) {
		return new EMROutputParser().getCSV(getOutputFromS3(experimentId));
	}

	@Override
	public List<Feature> getFeaturesAsList(String experimentId) {
		return new EMROutputParser().getFeatures(getOutputFromS3(experimentId));
	}

	// Returns output from an S3 experiment as a stream.
	private InputStream getOutputFromS3(String experimentId) {
		String path = experimentsPath + experimentId + "/output";
		// String path = "experiments/" + "test" + "/output";

		ObjectListing objects = s3Client.listObjects(pipelineBucket, path);
		List<S3ObjectSummary> objs = objects.getObjectSummaries();
		objs.remove(0); // remove root directory
		// logger.error("S3 objs for get output files: " + objs.size());

		List<InputStream> streams = new ArrayList<InputStream>();

		for (S3ObjectSummary os : objs) {
			S3Object obj = s3Client.getObject(pipelineBucket, os.getKey());
			streams.add(obj.getObjectContent());
		}

		InputStream is = new SequenceInputStream(
				Collections.enumeration(streams));

		return is;

	}

        // Cleans the name by returning the last string before '/'.
	private String getCleanName(String path) {
		String[] tokens = path.split("[/]");
		return tokens[tokens.length - 1];
	}

}
