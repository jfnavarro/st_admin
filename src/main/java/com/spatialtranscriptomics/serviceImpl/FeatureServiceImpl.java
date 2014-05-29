/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.spatialtranscriptomics.controller.DatasetController;
import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import com.spatialtranscriptomics.model.Dataset;
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

	
	public List<Feature> findForDataset(String datasetId){
		String url = appConfig.getProperty("url.feature");
		 url += "?dataset=" + datasetId;
		 logger.debug("Feature URL: " + url);
		Feature[] features = secureRestTemplate.getForObject(url, Feature[].class);
		return Arrays.asList(features);
	}
	
	public List<Feature> findForSelection(String selectionId){
		String url = appConfig.getProperty("url.feature");
		 url += "?selection=" + selectionId;
		 logger.debug("Feature URL: " + url);
		Feature[] features = secureRestTemplate.getForObject(url, Feature[].class);
		return (features == null ? new ArrayList<Feature>(0) : Arrays.asList(features));
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
		Feature[] featuresResponse = secureRestTemplate.postForObject(url, features, Feature[].class);
		return Arrays.asList(featuresResponse);
	}

	
	public List<Feature> update(List<Feature> features, String datasetId) {
		String url = appConfig.getProperty("url.feature");
		url += "?dataset=" + datasetId;
		// delete features for dataset
		this.deleteAll(datasetId);
		// add new features for dataset
		Feature[] featuresResponse = secureRestTemplate.postForObject(url, features, Feature[].class);
		List<Feature> fs = Arrays.asList(featuresResponse);
		return fs;
	}
	
	
	public void deleteAll(String datasetId) {
		String url = appConfig.getProperty("url.feature");
		url += "?dataset=" + datasetId;
		secureRestTemplate.delete(url);

	}
	
	
	private void updateStats(List<Feature> features, int[] stats, double[] overall_hit_quartiles, double[] gene_pooled_hit_quartiles) {
		
		int n = features.size();
		int sum = 0;
		List<Integer> hits = new ArrayList<Integer>(n);
		HashMap<String, Integer> pooledHits = new HashMap<String, Integer>(n);
		HashSet<String> pooledBarcodes = new HashSet<String>(n);
		for (Feature f : features) {
			String gene = f.getGene().toUpperCase();
			String barcode = f.getBarcode().toUpperCase();
			int h = f.getHits();
			sum += h;
			hits.add(h);
			if (pooledHits.containsKey(gene)) {
				pooledHits.put(gene, pooledHits.get(gene) + h);
			} else {
				pooledHits.put(gene, h);
			}
			pooledBarcodes.add(barcode);
		}
		Collections.sort(hits);
		ArrayList<Integer> poolHits = new ArrayList<Integer>(pooledHits.values());
		Collections.sort(poolHits);
		
		// overall_feature_count, overall_hit_count, unique_gene_count, unique_barcode_count
		stats = new int[] { n, sum, poolHits.size(), pooledBarcodes.size() };	
		
		overall_hit_quartiles = computeQuartiles(hits);
		gene_pooled_hit_quartiles = computeQuartiles(hits);
		
	}
	
		
	private double[] computeQuartiles(List<Integer> hits) {
		int n = hits.size();
		if (n == 1) {
			return new double[] { hits.get(0), hits.get(0), hits.get(0), hits.get(0), hits.get(0) };
		}
		
		double[] q = new double[] { -1, -1, -1, -1, -1 };
		
		// Linear interpolation for intermediate values, exact at endpoints.
		q[0] = hits.get(0);
		q[4] = hits.get(n - 1);
		double[] idx = new double[] { 0.25*n - 0.25,  0.5*n - 0.5,  0.75*n - 0.75 };
		for (int i = 0; i < 3; ++i) {
			int floor = (int) (Math.floor(idx[i]));
			int ceil = (int) (Math.ceil(idx[i]));
			double delta = idx[i] - floor;
			q[i + 1] = hits.get(floor) * (1.0 - delta) + hits.get(ceil) * delta;  // No prob if ceil==floor...
		}
		return q;
	}

}
