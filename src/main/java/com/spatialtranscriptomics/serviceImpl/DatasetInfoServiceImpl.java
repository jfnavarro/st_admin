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

import com.spatialtranscriptomics.model.DatasetInfo;
import com.spatialtranscriptomics.service.DatasetInfoService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Dataset".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class DatasetInfoServiceImpl implements DatasetInfoService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(DatasetInfoServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	
	public DatasetInfo find(String id) {
		String url = appConfig.getProperty("url.datasetinfo");
		url += id;
		DatasetInfo datasetinfo = secureRestTemplate.getForObject(url, DatasetInfo.class);
		return datasetinfo;
	}

	
	public List<DatasetInfo> list() {
		String url = appConfig.getProperty("url.datasetinfo");
		DatasetInfo[] dsArray = secureRestTemplate.getForObject(url, DatasetInfo[].class);
		List<DatasetInfo> dsList = Arrays.asList(dsArray);
		return dsList;
	}

	
	public List<DatasetInfo> listForAccount(String accountId) {
		String url = appConfig.getProperty("url.datasetinfo") + "?account=" + accountId;
		DatasetInfo[] dsArray = secureRestTemplate.getForObject(url, DatasetInfo[].class);
		List<DatasetInfo> dsList = Arrays.asList(dsArray);
		return dsList;
	}


	public List<DatasetInfo> listForDataset(String datasetId) {
		String url = appConfig.getProperty("url.datasetinfo") + "?dataset=" + datasetId;
		DatasetInfo[] dsArray = secureRestTemplate.getForObject(url, DatasetInfo[].class);
		List<DatasetInfo> dsList = Arrays.asList(dsArray);
		return dsList;
	}
	
	
	public DatasetInfo add(DatasetInfo datasetinfo) {
		String url = appConfig.getProperty("url.datasetinfo");
		System.out.println(url);
		DatasetInfo dsResponse = secureRestTemplate.postForObject(url, datasetinfo,
				DatasetInfo.class);
		System.out.println(dsResponse);
		return dsResponse;
	}

	
	public void update(DatasetInfo datasetinfo) {
		String url = appConfig.getProperty("url.datasetinfo");
		String id = datasetinfo.getId();
		secureRestTemplate.put(url + id, datasetinfo);
	}

	
	public void delete(String id) {
		String url = appConfig.getProperty("url.datasetinfo");
		secureRestTemplate.delete(url + id);
	}
	
	public void deleteForDataset(String datasetId) {
		List<DatasetInfo> dsis = listForDataset(datasetId);
		if (dsis ==  null) { return; }
		String url = appConfig.getProperty("url.datasetinfo");
		for (DatasetInfo dsi : dsis) {
			secureRestTemplate.delete(url + dsi.getId());
		}
	}

	public void deleteForAccount(String accountId) {
		List<DatasetInfo> dsis = listForAccount(accountId);
		if (dsis ==  null) { return; }
		String url = appConfig.getProperty("url.datasetinfo");
		for (DatasetInfo dsi : dsis) {
			secureRestTemplate.delete(url + dsi.getId());
		}
	}

}
