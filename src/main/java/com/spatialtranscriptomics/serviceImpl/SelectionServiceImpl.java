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

import com.spatialtranscriptomics.model.Selection;
import com.spatialtranscriptomics.service.SelectionService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Selection".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class SelectionServiceImpl implements SelectionService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(SelectionServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	
	public Selection find(String id) {
		String url = appConfig.getProperty("url.selection");
		url += id;
		Selection sel = secureRestTemplate.getForObject(url, Selection.class);
		return sel;
	}

	
	public List<Selection> list() {
		String url = appConfig.getProperty("url.selection");
		Selection[] eArray = secureRestTemplate.getForObject(url,
				Selection[].class);
		List<Selection> eList = Arrays.asList(eArray);
		return eList;
	}

	
	public Selection add(Selection sel) {
		String url = appConfig.getProperty("url.selection");
		Selection eResponse = secureRestTemplate.postForObject(url, sel, Selection.class);
		return eResponse;
	}

	
	public void update(Selection sel) {
		String url = appConfig.getProperty("url.selection");
		String id = sel.getId();
		secureRestTemplate.put(url + id, sel);
	}

	
	public void delete(String id) {
		String url = appConfig.getProperty("url.selection");
		secureRestTemplate.delete(url + id);
	}
	
	public void deleteForDataset(String datasetId) {
		List<Selection> sels = findForDataset(datasetId);
		if (sels == null) { return; }
		String url = appConfig.getProperty("url.selection");
		for (Selection sel : sels) {
			secureRestTemplate.delete(url + sel.getId());
		}
	}


	public List<Selection> findForAccount(String accountId) {
		String url = appConfig.getProperty("url.selection") + "?account=" + accountId;
		Selection[] arr = secureRestTemplate.getForObject(url, Selection[].class);
		List<Selection> list = Arrays.asList(arr);
		return list;
	}


	public List<Selection> findForDataset(String datasetId) {
		String url = appConfig.getProperty("url.selection") + "?dataset=" + datasetId;
		Selection[] arr = secureRestTemplate.getForObject(url, Selection[].class);
		List<Selection> list = Arrays.asList(arr);
		return list;
	}
	
	public List<Selection> findForTask(String taskId) {
		String url = appConfig.getProperty("url.selection") + "?task=" + taskId;
		Selection[] arr = secureRestTemplate.getForObject(url, Selection[].class);
		List<Selection> list = Arrays.asList(arr);
		return list;
	}

}
