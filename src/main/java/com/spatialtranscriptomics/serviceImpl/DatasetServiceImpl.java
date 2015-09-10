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
import com.spatialtranscriptomics.model.Dataset;
import com.spatialtranscriptomics.service.DatasetService;

/**
 * This class implements the store/retrieve logic to the ST 
 * API for the data model class "Dataset".
 * The connection to the ST API is handled in a RestTemplate object, 
 * which is configured in mvc-dispather-servlet.xml
 */
@Service
public class DatasetServiceImpl implements DatasetService {

    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(DatasetServiceImpl.class);

    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;

    @Override
    public Dataset find(String id) {
	String url = appConfig.getProperty("url.dataset") + "/" + id;
	return secureRestTemplate.getForObject(url, Dataset.class);
    }

    @Override
    public List<Dataset> list() {
	String url = appConfig.getProperty("url.dataset");
	return Arrays.asList(secureRestTemplate.getForObject(url, Dataset[].class));
    }
	
    @Override
    public List<Dataset> listForAccount(String accountId) {
	String url = appConfig.getProperty("url.dataset") + "/?account=" + accountId;
	Dataset[] dsArray = secureRestTemplate.getForObject(url, Dataset[].class);
	//TODO is this necessary?
        if (dsArray == null) { 
            return null; 
        }
	
        return Arrays.asList(dsArray);
    }

    @Override
    public Dataset add(Dataset dataset) {
	String url = appConfig.getProperty("url.dataset");
        return secureRestTemplate.postForObject(url, dataset, Dataset.class);
    }

    @Override
    public void update(Dataset dataset) {
	String url = appConfig.getProperty("url.dataset") + "/" + dataset.getId();
	secureRestTemplate.put(url, dataset);
    }

    @Override
    public void delete(String id) {
	String url = appConfig.getProperty("url.dataset") + "/" + id;
	secureRestTemplate.delete(url);
    }
	
        
}
