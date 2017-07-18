/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.st.serviceImpl;

import com.st.model.FileMetadata;
import com.st.service.FileService;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
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
public class FileServiceImpl implements FileService {
 
    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
    private static final Logger logger = Logger
            .getLogger(FileServiceImpl.class);

    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;
      
    @Override
    public void addUpdate(String id, byte[] gzipfile) {
        String url = appConfig.getProperty("url.file");
        secureRestTemplate.put(url + id, gzipfile);
    }
    
    @Override
    public List<FileMetadata> listMetadata() {
        String url = appConfig.getProperty("url.file");
        FileMetadata[] feats = secureRestTemplate.getForObject(url, 
                FileMetadata[].class);
        return Arrays.asList(feats);
    }
    
    
    @Override
    public byte[] find(String id) {
        String url = appConfig.getProperty("url.file") + id;
        return secureRestTemplate.getForObject(url, byte[].class);
    }
    
}
