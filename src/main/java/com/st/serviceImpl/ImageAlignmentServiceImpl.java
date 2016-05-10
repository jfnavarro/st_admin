/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.st.model.ImageAlignment;
import com.st.service.ImageAlignmentService;

/**
 * This class implements the store/retrieve logic to the ST API for the data
 * model class "ImageAlignment". The connection to the ST API is handled in a
 * RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */
@Service
public class ImageAlignmentServiceImpl implements ImageAlignmentService {

    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ImageAlignmentServiceImpl.class);

    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;

    @Override
    public ImageAlignment find(String id) {
        if (id == null || id.equals("")) { return null; }
        String url = appConfig.getProperty("url.imagealignment");
        url += id;
        ImageAlignment imal = secureRestTemplate.getForObject(url, ImageAlignment.class);
        return imal;
    }

    @Override
    public List<ImageAlignment> list() {
        String url = appConfig.getProperty("url.imagealignment");
        ImageAlignment[] imalArray = secureRestTemplate.getForObject(url, ImageAlignment[].class);
        List<ImageAlignment> imalList = Arrays.asList(imalArray);
        return imalList;
    }

    @Override
    public ImageAlignment create(ImageAlignment imal) {
        String url = appConfig.getProperty("url.imagealignment");
        ImageAlignment imalResponse = secureRestTemplate.postForObject(url, imal, ImageAlignment.class);
        return imalResponse;
    }

    @Override
    public void update(ImageAlignment imal) {
        String url = appConfig.getProperty("url.imagealignment");
        String id = imal.getId();
        secureRestTemplate.put(url + id, imal);
    }

    @Override
    public void delete(String id) {
        String url = appConfig.getProperty("url.imagealignment");
        secureRestTemplate.delete(url + id + "?cascade=true");
    }

    @Override
    public List<ImageAlignment> findForChip(String chipId) {
        String url = appConfig.getProperty("url.imagealignment");
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        url += "?chip=" + chipId;
        ImageAlignment[] imalArray = secureRestTemplate.getForObject(url, ImageAlignment[].class);
        if (imalArray == null) {
            return null;
        }
        List<ImageAlignment> imalList = Arrays.asList(imalArray);
        return imalList;
    }

}
