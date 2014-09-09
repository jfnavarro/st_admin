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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spatialtranscriptomics.model.PipelineStats;
import com.spatialtranscriptomics.service.PipelineStatsService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "PipelineStats".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class PipelineStatsServiceImpl implements PipelineStatsService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(PipelineStatsServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	@Override
	public PipelineStats find(String id) {
		String url = appConfig.getProperty("url.pipelinestats");
		url += id;
		PipelineStats stats = secureRestTemplate.getForObject(url, PipelineStats.class);
		return stats;
	}

	@Override
	public List<PipelineStats> list() {
		String url = appConfig.getProperty("url.pipelinestats");
		PipelineStats[] eArray = secureRestTemplate.getForObject(url,
				PipelineStats[].class);
		List<PipelineStats> eList = Arrays.asList(eArray);
		return eList;
	}

	@Override
	public PipelineStats findForPipelineExperiment(String expId) {
		String url = appConfig.getProperty("url.pipelinestats");
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		url += "?pipelineexperiment=" + expId;
		PipelineStats[] arr = secureRestTemplate.getForObject(url, PipelineStats[].class);
		if (arr == null || arr.length == 0) {
			return null;
		}
		return arr[0];
	}

	@Override
	public PipelineStats add(PipelineStats stats) {
		String url = appConfig.getProperty("url.pipelinestats");
		PipelineStats eResponse = secureRestTemplate.postForObject(url, stats,
				PipelineStats.class);
		return eResponse;
	}

	@Override
	public void update(PipelineStats stats) {
		String url = appConfig.getProperty("url.pipelinestats");
		String id = stats.getId();
		secureRestTemplate.put(url + id, stats);
	}

	@Override
	public void delete(String id) {
		String url = appConfig.getProperty("url.pipelinestats");
		secureRestTemplate.delete(url + id);
	}
	

}
