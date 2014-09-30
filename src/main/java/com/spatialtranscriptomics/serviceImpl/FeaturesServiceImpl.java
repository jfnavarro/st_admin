/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spatialtranscriptomics.serviceImpl;

import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.model.FeaturesMetadata;
import com.spatialtranscriptomics.model.S3Resource;
import com.spatialtranscriptomics.service.FeaturesService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
 
    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
    private static final Logger logger = Logger
            .getLogger(FeaturesServiceImpl.class);

    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;
    
    
    @Override
    public void addUpdate(String id, S3Resource resource) {
        String url = appConfig.getProperty("url.features");
        secureRestTemplate.put(url + id, resource);
    }
    
    
    @Override
    public List<FeaturesMetadata> listMetadata() {
        String url = appConfig.getProperty("url.features");
        FeaturesMetadata[] feats = secureRestTemplate.getForObject(url, FeaturesMetadata[].class);
        return Arrays.asList(feats);
    }
    
    
    @Override
    public S3Resource find(String id) {
        String url = appConfig.getProperty("url.features") + id;
        S3Resource fw = secureRestTemplate.getForObject(url, S3Resource.class);
        return fw;
    }
    
    
    @Override
    public Feature[] parse(byte[] bytes) {
        Feature[] features = null;

        try {
            // Specify that unknown parameters should not be mapped.
            ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            features = (Feature[]) mapper.readValue(bytes, Feature[].class);
            //logger.debug(" features in file: " + features.length);
            return features;
        } catch (IOException e) {
            logger.error("Error parsing features file.");
            e.printStackTrace();
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse feature file. Wrong format?" + e.getStackTrace().toString());
            throw new GenericException(resp);
        }
    }
}
