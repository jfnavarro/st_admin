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
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class FeatureWrapper that encapsulates
 * a features file with compressed contents.
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class FeaturesServiceImpl implements FeaturesService {
 
    private static final Logger logger = Logger
            .getLogger(FeaturesServiceImpl.class);

    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;
    
    /**
     * Adds or, in the case of an existing object, updates the features file.
     * @param id dataset id.
     * @param bytes the raw decompressed JSON file.
     */
    @Override
    public void addUpdate(String id, byte[] bytes) {
        try {
            System.out.println("Attempting to zip features file with " + bytes.length + " bytes");
            ByteArrayOutputStream zipbytesbos = new ByteArrayOutputStream(bytes.length / 20);
            BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(zipbytesbos), bytes.length / 20);
            bos.write(bytes);
            bos.flush();
            bos.close();
            zipbytesbos.close();
            byte[] zipbytes = zipbytesbos.toByteArray();
            System.out.println("Suceeded zipping: " + zipbytes.length + " bytes (compression factor " + (bytes.length / (double) zipbytes.length));
                        
            String url = appConfig.getProperty("url.features");
            S3Resource wrap = new S3Resource("application/json", "gzip", id, zipbytes);
            System.out.println("Trying put on " + url + id);
            secureRestTemplate.put(url + id, wrap);
            System.out.println("Made put");
            
        } catch (IOException ex) {
            logger.info("Failed to gzip feature file.");
        }
    }
    
    /**
     * Lists metadata for feature files.
     * @return metadata.
     */
    @Override
    public List<FeaturesMetadata> listMetadata() {
        String url = appConfig.getProperty("url.features");
        FeaturesMetadata[] feats = secureRestTemplate.getForObject(url, FeaturesMetadata[].class);
        return Arrays.asList(feats);
    }
    
    /**
     * Returns the features file of a dataset.
     * @param id the dataset ID.
     * @return the file with compressed contents.
     */
    @Override
    public S3Resource find(String id) {
        String url = appConfig.getProperty("url.features") + id;
        S3Resource fw = secureRestTemplate.getForObject(url, S3Resource.class);
        return fw;
    }
    
    /**
     * Parses an features file into a list of model objects.
     * @param bytes the raw decompressed file.
     * @return the model objects.
     */
    @Override
    public Feature[] parse(byte[] bytes) {
        Feature[] features = null;

        try {
            ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //byte[] bytes = featureFile.getBytes();
            features = (Feature[]) mapper.readValue(bytes, Feature[].class);
            logger.debug(" features in file: " + features.length);
            return features;
        } catch (IOException e) {
            logger.debug("error parsing dataset: ");
            e.printStackTrace();
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse feature file. Wrong format?" + e.getStackTrace().toString());
            throw new GenericException(resp);
        }
    }
}
