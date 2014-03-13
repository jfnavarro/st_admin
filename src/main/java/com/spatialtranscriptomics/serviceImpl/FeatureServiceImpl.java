/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.service.FeatureService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Feature".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class FeatureServiceImpl implements FeatureService {

	private static final Logger logger = Logger
			.getLogger(FeatureServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	
	public List<Feature> find(String datasetId){
		String url = appConfig.getProperty("url.feature");
		 url += "?dataset=" + datasetId;
		 logger.debug("Feature URL: " + url);
		Feature[] features = secureRestTemplate.getForObject(url, Feature[].class);
		return Arrays.asList(features);
	}

	
	public List<Feature> parse(CommonsMultipartFile featureFile) {
		Feature[] features;
		try {
			ObjectMapper mapper = new ObjectMapper();
			features = (Feature[]) mapper.readValue(featureFile.getBytes(),
					Feature[].class);
			logger.debug(" features in file: " + features.length);
		} catch (Exception e) {
			e.printStackTrace();
			GenericExceptionResponse resp = new GenericExceptionResponse();
			resp.setError("Parse error");
			resp.setError_description("Could not parse feature file. Wrong format?");
			throw new GenericException(resp);
		}
		return Arrays.asList(features);

	}

	
	public List<Feature> add(List<Feature> features, String datasetId) {
		String url = appConfig.getProperty("url.feature");
		url += "?dataset=" + datasetId;
		Feature[] featuresResponse = secureRestTemplate.postForObject(url,
				features, Feature[].class);
		return Arrays.asList(featuresResponse);
	}

	
	public List<Feature> update(List<Feature> features, String datasetId) {
		String url = appConfig.getProperty("url.feature");
		url += "?dataset=" + datasetId;
		// delete features for dataset
		this.deleteAll(datasetId);
		// add new features for dataset
		Feature[] featuresResponse = secureRestTemplate.postForObject(url, features, Feature[].class);
		return Arrays.asList(featuresResponse);
	}
	
	
	public void deleteAll(String datasetId) {
		String url = appConfig.getProperty("url.feature");
		url += "?dataset=" + datasetId;
		secureRestTemplate.delete(url);

	}

}
