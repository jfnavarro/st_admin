/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.serviceImpl;

import com.spatialtranscriptomics.model.ImageMetadata;
import com.spatialtranscriptomics.model.JPEGWrapper;
import com.spatialtranscriptomics.service.ImageService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
        public JPEGWrapper findCompressedAsJSON(String id) {
            String url = appConfig.getProperty("url.image");
            url += "/compressedjson/" + id;
            JPEGWrapper img = secureRestTemplate.getForObject(url, JPEGWrapper.class);
            return img;
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
	public void addFromFile(CommonsMultipartFile imageFile) throws IOException {
            String url = appConfig.getProperty("url.image");
            url += imageFile.getOriginalFilename();

            //try {
            //System.out.println("Reading image");
            BufferedImage bi = ImageIO.read(imageFile.getInputStream());
            if (bi == null) { throw new IOException(); }
            //System.out.println("Read image, storing it");
            secureRestTemplate.put(url, bi);
            //System.out.println("Stored image");
            //} catch (IOException e) {
            //	e.printStackTrace();
            //}
	}
        
        @Override
	public JPEGWrapper addFromFileCompressedAsJSON(CommonsMultipartFile imageFile) throws IOException {
            //System.out.println("Adding image");
            String url = appConfig.getProperty("url.image");
            url += "/compressedjson";
            
            JPEGWrapper img = new JPEGWrapper();
            img.setFilename(imageFile.getOriginalFilename());
            img.setImage(IOUtils.toByteArray(imageFile.getInputStream()));
            JPEGWrapper resp = secureRestTemplate.postForObject(url, img, JPEGWrapper.class);
            return resp;
	}

}
