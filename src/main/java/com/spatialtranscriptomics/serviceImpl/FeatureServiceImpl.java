/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.service.FeatureService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Feature".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispather-servlet.xml
 */

@Service
public class FeatureServiceImpl implements FeatureService {

    private static final Logger logger = Logger
            .getLogger(FeatureServiceImpl.class);

    @Autowired
    RestTemplate secureRestTemplate;

    @Autowired
    Properties appConfig;

    @Override
    public List<Feature> findForDataset(String datasetId) {
        String url = appConfig.getProperty("url.feature");
        url += "?dataset=" + datasetId;
        logger.debug("Feature URL: " + url);
        Feature[] features = secureRestTemplate.getForObject(url, Feature[].class);
        return Arrays.asList(features);
    }

    @Override
    public List<Feature> findForSelection(String selectionId) {
        String url = appConfig.getProperty("url.feature");
        url += "?selection=" + selectionId;
        logger.debug("Feature URL: " + url);
        Feature[] features = secureRestTemplate.getForObject(url, Feature[].class);
        return (features == null ? new ArrayList<Feature>(0) : Arrays.asList(features));
    }

    public static String guessEncoding(byte[] bytes) {
        String DEFAULT_ENCODING = "UTF-8";
        org.mozilla.universalchardet.UniversalDetector detector
                = new org.mozilla.universalchardet.UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
        return encoding;
    }

    @Override
    public List<Feature> parse(CommonsMultipartFile featureFile) {
        Feature[] features = null;

        try {
//                        // Circus 70 MB seem to be the max allowed buffer size.
//                        // We split into chunks if bigger.
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = featureFile.getBytes();
//                        if (bytes.length < /*70000000*/ Integer.MAX_VALUE) {
            features = (Feature[]) mapper.readValue(featureFile.getBytes(), Feature[].class);
            logger.debug(" features in file: " + features.length);
            return Arrays.asList(features);
//                        }
//                        else {
//                            String encoding = guessEncoding(bytes);
//                            int cl = "[".getBytes().length;
//                            ArrayList<Feature> feats = new ArrayList<Feature>();
//                            int i = 0;
//                            while (i < bytes.length) {
//                                    int j = Math.min(i + 70000000, bytes.length);
//                                    int dim = j - i;
//                                    
//                                    // Expand until next }
//                                    while (j < bytes.length) {
//                                        byte[] ch = new byte[cl];
//                                        System.arraycopy(bytes, j-cl, ch, 0, cl);
//                                        if (new String(ch, encoding).equals("}")) {
//                                            break;
//                                        }
//                                        j += cl;
//                                        dim += cl;
//                                    }
//                                    byte[] part;
//                                    byte[] c = "[".getBytes(encoding);
//                                    if (i > 0) {
//                                        part = new byte[cl + dim + (j < bytes.length ? cl : 0)];
//                                        System.arraycopy(c, 0, part, 0, cl);
//                                        System.arraycopy(bytes, i, part, cl, dim);
//                                    } else {
//                                        part = new byte[dim + (j < bytes.length ? cl : 0)];
//                                        System.arraycopy(bytes, i, part, 0, dim);
//                                        
//                                    }
//                                    if (j < bytes.length) {
//                                        c = "]".getBytes();
//                                        System.arraycopy(c, 0, part, part.length - cl, cl);
//                                    }
//                                    features = (Feature[]) mapper.readValue(part, Feature[].class);
//                                    for (Feature f : features) {
//                                        feats.add(f);
//                                    }
//                                    i = j;
//                                    // Find next {
//                                    while (i < bytes.length) {
//                                        byte[] ch = new byte[cl];
//                                        System.arraycopy(bytes, i, ch, 0, cl);
//                                        if (new String(ch, encoding).equals("{")) {
//                                            break;
//                                        }
//                                        i += cl;
//                                    }
//                            }
//                            logger.debug(" features in file: " + feats.size());
//                            return feats;
//                        }
        } catch (IOException e) {
            logger.debug("error parsing dataset: ");
            e.printStackTrace();
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse feature file. Wrong format?" + e.getStackTrace().toString());
            throw new GenericException(resp);
        }
    }

    @Override
    public List<Feature> add(List<Feature> features, String datasetId) {
//        // Split into chunks for size reasons.
//        if (features.size() < /*200000*/ Integer.MAX_VALUE) {
        String url = appConfig.getProperty("url.feature") + "?dataset=" + datasetId;
        Feature[] featuresResponse = secureRestTemplate.postForObject(url, features, Feature[].class);
        return Arrays.asList(featuresResponse);
//        }
//                else {
//                    ArrayList<Feature> response = new ArrayList<Feature>(features.size());
//                    int i = 0;
//                    while (i < features.size()) {
//                        int j = Math.min(i + 200000, features.size());
//                        List<Feature> al = features.subList(i, j);
//                        String url;
//                        if (j == features.size()) {
//                            url = appConfig.getProperty("url.feature") + "?dataset=" + datasetId;
//                        } else {
//                            url = appConfig.getProperty("url.feature") + "?dataset=" + datasetId + "&nostats=true";
//                        }
//                        response.addAll(Arrays.asList(secureRestTemplate.postForObject(url, al, Feature[].class)));
//                        i = j;
//                    }
//                    return response;
//                }
    }

    @Override
    public List<Feature> update(List<Feature> features, String datasetId) {
        String url = appConfig.getProperty("url.feature");
        url += "?dataset=" + datasetId;
        // delete features for dataset
        this.deleteAll(datasetId);
        // add new features for dataset
        Feature[] featuresResponse = secureRestTemplate.postForObject(url, features, Feature[].class);
        List<Feature> fs = Arrays.asList(featuresResponse);
        return fs;
    }

    @Override
    public void deleteAll(String datasetId) {
        String url = appConfig.getProperty("url.feature");
        url += "?dataset=" + datasetId;
        secureRestTemplate.delete(url);

    }

}
