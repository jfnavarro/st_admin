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

import com.spatialtranscriptomics.model.Experiment;
import com.spatialtranscriptomics.service.ExperimentService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Experiment".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class ExperimentServiceImpl implements ExperimentService {

	private static final Logger logger = Logger
			.getLogger(ExperimentServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	public Experiment find(String id) {

		String url = appConfig.getProperty("url.experiment");
		url += id;
		Experiment experiment = secureRestTemplate.getForObject(url, Experiment.class);
		return experiment;
	}

	public List<Experiment> list() {

		String url = appConfig.getProperty("url.experiment");
		Experiment[] eArray = secureRestTemplate.getForObject(url,
				Experiment[].class);
		List<Experiment> eList = Arrays.asList(eArray);
		return eList;
	}

	public Experiment add(Experiment experiment) {

		String url = appConfig.getProperty("url.experiment");
		Experiment eResponse = secureRestTemplate.postForObject(url, experiment,
				Experiment.class);
		return eResponse;
	}

	public void update(Experiment experiment) {

		String url = appConfig.getProperty("url.experiment");
		String id = experiment.getId();
		secureRestTemplate.put(url + id, experiment);
	}

	public void delete(String id) {

		String url = appConfig.getProperty("url.experiment");
		secureRestTemplate.delete(url + id);
	}

}
