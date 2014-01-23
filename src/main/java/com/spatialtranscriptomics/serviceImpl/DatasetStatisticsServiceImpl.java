package com.spatialtranscriptomics.serviceImpl;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spatialtranscriptomics.model.DatasetStatistics;
import com.spatialtranscriptomics.service.DatasetStatisticsService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "DatasetStatistics".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispatcher-servlet.xml
 */

@Service
public class DatasetStatisticsServiceImpl implements DatasetStatisticsService {

	private static final Logger logger = Logger
			.getLogger(FeatureServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	public DatasetStatistics find(String datasetId){
		String url = appConfig.getProperty("url.datasetstatistics");
		 url += "?dataset=" + datasetId;
		 logger.debug("DatasetStatistics URL: " + url);
		DatasetStatistics stats = secureRestTemplate.getForObject(url, DatasetStatistics.class);
		return stats;
	}
}
