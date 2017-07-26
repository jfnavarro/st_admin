package com.st.serviceImpl;

import com.st.service.FileService;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This class implements the store/retrieve logic to the ST API for the dataset's files.
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
    public void addUpdate(String filename, String id, byte[] gzipfile) {
        final String url = appConfig.getProperty("url.file");
        logger.info("Adding or updating file for dataset " + id);
        secureRestTemplate.put(url + id + "?filename=" + filename, gzipfile);
    }
    
    @Override
    public void remove(String filename, String id) {
        final String url = appConfig.getProperty("url.file");
        logger.info("Removing file for dataset " + id);
        secureRestTemplate.delete(url + id + "?filename=" + filename);
    }
    
    @Override
    public byte[] find(String filename, String id) {
        final String url = appConfig.getProperty("url.file");
        logger.info("Getting file for dataset " + id);
        return secureRestTemplate.getForObject(url + id + "?filename=" + filename, byte[].class);
    }
    
}
