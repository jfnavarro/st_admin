/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.serviceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.spatialtranscriptomics.model.Chip;
import com.spatialtranscriptomics.service.ChipService;
import com.spatialtranscriptomics.util.NDFParser;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Chip".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class ChipServiceImpl implements ChipService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(ChipServiceImpl.class);

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

	
	public Chip find(String id) {

		String url = appConfig.getProperty("url.chip");
		url += id;
		Chip chip = secureRestTemplate.getForObject(url, Chip.class);
		return chip;
	}

	
	public List<Chip> list() {
		String url = appConfig.getProperty("url.chip");
		Chip[] chipArray = secureRestTemplate.getForObject(url, Chip[].class);
		List<Chip> chipList = Arrays.asList(chipArray);
		return chipList;
	}

	
	public Chip create(Chip chip) {
		String url = appConfig.getProperty("url.chip");
		Chip chipResponse = secureRestTemplate.postForObject(url, chip,
				Chip.class);
		return chipResponse;
	}

	
	public Chip addFromFile(CommonsMultipartFile chipFile, String name) {
		try {
			// parse ndf file to get Values
			NDFParser parser = new NDFParser(chipFile.getInputStream());
			Chip chip = parser.readChip();
			chip.setName(name);

			String url = appConfig.getProperty("url.chip");
			Chip chipResponse = secureRestTemplate.postForObject(url, chip,
					Chip.class);
			return chipResponse;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public void update(Chip chip) {
		String url = appConfig.getProperty("url.chip");
		String id = chip.getId();
		secureRestTemplate.put(url + id, chip);
	}

	
	public void delete(String id) {
		String url = appConfig.getProperty("url.chip");
		secureRestTemplate.delete(url + id);
	}

}
