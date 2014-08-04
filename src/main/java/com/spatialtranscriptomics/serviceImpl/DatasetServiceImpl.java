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
 * This class implements the store/retrieve logic to the ST API for the data model class "Dataset".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class DatasetServiceImpl implements DatasetService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(DatasetServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

        @Override
	public Dataset find(String id) {
		String url = appConfig.getProperty("url.dataset");
		url += id;
		Dataset dataset = secureRestTemplate.getForObject(url, Dataset.class);
		return dataset;
	}

        @Override
	public List<Dataset> list() {
		String url = appConfig.getProperty("url.dataset");
		Dataset[] dsArray = secureRestTemplate.getForObject(url,
				Dataset[].class);
		List<Dataset> dsList = Arrays.asList(dsArray);
		return dsList;
	}
	
        @Override
	public List<Dataset> listForAccount(String accountId) {
		String url = appConfig.getProperty("url.dataset") + "?account=" + accountId;
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
		secureRestTemplate.delete(url + id);
	}
	
        @Override
	public void setUnabledForImageAlignment(String imalId) {
		List<Dataset> ds = list();
		if (ds == null) { return; }
		for (Dataset d : ds) {
			if (d.getImage_alignment_id() != null && d.getImage_alignment_id().equals(imalId)) {
				d.setEnabled(false);
				d.setImage_alignment_id(null);
				update(d);
			}
		}
	}
        
        @Override
        public void clearAccountCreator(String accountId) {
            List<Dataset> l = list();
            for (Dataset d : l) {
                if (d.getCreated_by_account_id() != null && d.getCreated_by_account_id().equals(accountId)) {
                    d.setCreated_by_account_id(null);
                    update(d);
                }
            }
        }

}
