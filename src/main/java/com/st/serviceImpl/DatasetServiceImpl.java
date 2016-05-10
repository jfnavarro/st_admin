/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.st.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.st.model.Dataset;
import com.st.service.DatasetService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Dataset".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class DatasetServiceImpl implements DatasetService {

    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(DatasetServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

        @Override
	public Dataset find(String id) {
		String url = appConfig.getProperty("url.dataset") + "all/";
		url += id;
		Dataset dataset = secureRestTemplate.getForObject(url, Dataset.class);
		return dataset;
	}

        @Override
	public List<Dataset> list() {
		String url = appConfig.getProperty("url.dataset") + "all/";
		Dataset[] dsArray = secureRestTemplate.getForObject(url,
				Dataset[].class);
		List<Dataset> dsList = Arrays.asList(dsArray);
		return dsList;
	}
	
        @Override
	public List<Dataset> listForAccount(String accountId) {
		String url = appConfig.getProperty("url.dataset") + "all/?account=" + accountId;
		Dataset[] dsArray = secureRestTemplate.getForObject(url, Dataset[].class);
		if (dsArray == null) { return null; }
		List<Dataset> dsList = Arrays.asList(dsArray);
		return dsList;
	}

        @Override
	public Dataset add(Dataset dataset) {
		String url = appConfig.getProperty("url.dataset");
		Dataset dsResponse = secureRestTemplate.postForObject(url, dataset,
				Dataset.class);
		return dsResponse;
	}

        @Override
	public void update(Dataset dataset) {
		String url = appConfig.getProperty("url.dataset");
		String id = dataset.getId();
		secureRestTemplate.put(url + id, dataset);
	}

        @Override
	public void delete(String id) {
		String url = appConfig.getProperty("url.dataset");
		secureRestTemplate.delete(url + id + "?cascade=true");
	}
	
        
}
