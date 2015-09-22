/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spatialtranscriptomics.serviceImpl;

import com.spatialtranscriptomics.model.FeaturesMetadata;
import com.spatialtranscriptomics.service.FeaturesService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class
 * S3Resource that encapsulates a features file with compressed contents.
 * It also provides a parsing feature for internal handling of the feature file contents.
 * The connection to the ST API is handled in a RestTemplate object, which is
 * configured in mvc-dispather-servlet.xml.
 */

@Service
public class FeaturesServiceImpl implements FeaturesService {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(FeaturesServiceImpl.class);
        
    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;
    
    @Override
    public void addUpdate(String id, byte[] file) {
        String url = appConfig.getProperty("url.features");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<byte[]> entity = new HttpEntity(file, headers);
        // TODO: This version of spring does not seem to deal very well with
        // setting the Content-Type header to application/octet-stream.
        // Revisit this after the upgrade to Spring 4.X
        try {
            secureRestTemplate.exchange(url + id, HttpMethod.PUT, entity, String.class);
        } catch(RestClientException e) {
            logger.info("Error uploading feature file " + id + " to S3", e);
            throw e;
        }
    }
    
    @Override
    public List<FeaturesMetadata> listMetadata() {
        String url = appConfig.getProperty("url.features");
        FeaturesMetadata[] feats = 
                secureRestTemplate.getForObject(url, FeaturesMetadata[].class);
        return Arrays.asList(feats);
    }
    
    @Override
    public String find(String id) {
        String url = appConfig.getProperty("url.features") + "/" + id;
        try {
            // Add the gzip Accept-Encoding header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Content-Encoding", "gzip");
            HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
            ResponseEntity<String> response = secureRestTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            return response.toString();
        } catch (RestClientException e) {
            logger.info("Error retrieving features from API", e);
            return null;
        }
    }
    
}
