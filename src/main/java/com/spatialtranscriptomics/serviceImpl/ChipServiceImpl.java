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
import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

/**
 * This class implements the store/retrieve logic to the ST API for the data
 * model class "Chip". The connection to the ST API is handled in a RestTemplate
 * object, which is configured in mvc-dispather-servlet.xml
 */
@Service
public class ChipServiceImpl implements ChipService {

    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ChipServiceImpl.class);

    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;

    @Override
    public Chip find(String id) {
        String url = appConfig.getProperty("url.chip") + id;
        return secureRestTemplate.getForObject(url, Chip.class);
    }

    @Override
    public List<Chip> list() {
        String url = appConfig.getProperty("url.chip");
        return Arrays.asList(secureRestTemplate.getForObject(url, Chip[].class));
    }

    @Override
    public Chip create(Chip chip) {
        String url = appConfig.getProperty("url.chip");
        return secureRestTemplate.postForObject(url, chip, Chip.class);
    }

    @Override
    public void addFromFile(CommonsMultipartFile chipFile, String name) throws IOException {
        // parse ndf file to get Values
        InputStream chip_file_stream = null;
        try {
            chip_file_stream = chipFile.getInputStream();
            NDFParser parser = new NDFParser(chip_file_stream);
            // get  chip object from NDF
            Chip chip = parser.readChip();
            chip.setName(name);
        
            // add the chip object
            String url_chip = appConfig.getProperty("url.chip");
            Chip created_chip = secureRestTemplate.postForObject(url_chip, chip, Chip.class);
        
            // add the file to S3
            try {
             
                final MultiValueMap<String, Object> requestParts = new LinkedMultiValueMap();
                final String tmpImageFileName = "/tmp/" + created_chip.getId();
                File file = new File(tmpImageFileName);
                chipFile.transferTo(file);
                requestParts.add("file", new FileSystemResource(tmpImageFileName));
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "multipart/form-data"); // Sending it like the client-form sends it
                String url_file = appConfig.getProperty("url.chip") + "/file/" + created_chip.getId();
                ResponseEntity<byte[]> response = 
                        secureRestTemplate.exchange(url_file, HttpMethod.POST, new HttpEntity(requestParts, headers), byte[].class);
                
                //HttpHeaders headers = new HttpHeaders();
                //headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                //HttpEntity<CommonsMultipartFile> entity = new HttpEntity(chipFile, headers);
                //String url_file = appConfig.getProperty("url.chip") + "/file/" + created_chip.getId();
                //secureRestTemplate.put(url_file, entity);
                
                /*HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<CommonsMultipartFile> requestEntity = 
                        new HttpEntity<CommonsMultipartFile>(chipFile, headers);
                String url_file = appConfig.getProperty("url.chip") + "/file/" + created_chip.getId();
                ResponseEntity<String> result = secureRestTemplate.exchange(
                        url_file, HttpMethod.POST, requestEntity, String.class);*/
            } catch (RestClientException e) {
                logger.info("Failed to import chip file to S3, deleting created chip..", e);
                //TODO well, what if this throws an exception??
                String url = appConfig.getProperty("url.chip") + created_chip.getId();
                secureRestTemplate.delete(url);
                throw e;
            }
        } catch (IOException e) {
            throw e;
        } catch (NumberFormatException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (RestClientException e) {
            throw e;
        } finally {
            if (chip_file_stream != null) {
                chip_file_stream.close();
            }
        }
    }

    @Override
    public void update(Chip chip) {
        String url = appConfig.getProperty("url.chip") + chip.getId();
        secureRestTemplate.put(url, chip);
    }

    @Override
    public void delete(String id) {
        String url = appConfig.getProperty("url.chip") + id;
        secureRestTemplate.delete(url);
    }

}
