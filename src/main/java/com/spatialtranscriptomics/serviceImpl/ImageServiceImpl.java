/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.spatialtranscriptomics.model.ImageMetadata;
import com.spatialtranscriptomics.service.ImageService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "ImageMetadata" and image payload (BufferedImage).
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	RestTemplate secureRestTemplate;

	@Autowired
	Properties appConfig;

        @Override
	public List<ImageMetadata> list() {

		String url = appConfig.getProperty("url.image");
		ImageMetadata[] imgMetadataArray = secureRestTemplate.getForObject(url,
				ImageMetadata[].class);
		return Arrays.asList(imgMetadataArray);
	}

        @Override
	public BufferedImage find(String id) {

		String url = appConfig.getProperty("url.image");
		url += id;
		BufferedImage img = secureRestTemplate.getForObject(url,
				BufferedImage.class);
		return img;
	}

        @Override
	public void delete(String id) {

		String url = appConfig.getProperty("url.image");
		secureRestTemplate.delete(url + id);
	}

        @Override
	public void addFromFile(CommonsMultipartFile imageFile) {

		String url = appConfig.getProperty("url.image");
		url += imageFile.getOriginalFilename();

		try {
			BufferedImage bi = ImageIO.read(imageFile.getInputStream());
			secureRestTemplate.put(url, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
