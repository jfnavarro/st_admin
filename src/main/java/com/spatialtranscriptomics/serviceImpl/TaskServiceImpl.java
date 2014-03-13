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

import com.spatialtranscriptomics.model.Task;
import com.spatialtranscriptomics.service.TaskService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Task".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class TaskServiceImpl implements TaskService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(TaskServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	
	public Task find(String id) {
		String url = appConfig.getProperty("url.task");
		url += id;
		Task task = secureRestTemplate.getForObject(url, Task.class);
		return task;
	}

	
	public List<Task> list() {
		String url = appConfig.getProperty("url.task");
		Task[] eArray = secureRestTemplate.getForObject(url,
				Task[].class);
		List<Task> eList = Arrays.asList(eArray);
		return eList;
	}

	
	public Task add(Task task) {
		String url = appConfig.getProperty("url.task");
		Task eResponse = secureRestTemplate.postForObject(url, task, Task.class);
		return eResponse;
	}

	
	public void update(Task task) {
		String url = appConfig.getProperty("url.task");
		String id = task.getId();
		secureRestTemplate.put(url + id, task);
	}

	
	public void delete(String id) {
		String url = appConfig.getProperty("url.task");
		secureRestTemplate.delete(url + id);
	}

	public List<Task> findForAccount(String accountId) {
		String url = appConfig.getProperty("url.task") + "?account=" + accountId;
		Task[] arr = secureRestTemplate.getForObject(url, Task[].class);
		List<Task> list = Arrays.asList(arr);
		return list;
	}


}
