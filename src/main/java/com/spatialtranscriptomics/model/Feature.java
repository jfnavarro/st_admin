/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.model;

import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import static com.spatialtranscriptomics.util.ByteOperations.gunzip;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This bean class maps a subset of the Feature post within a feature file to
 * properties needed for statistics computations, etc. It has no correspondnce with the API.
 * Only use fields are kept,
 * in order to keep heap size consumption down while parsing.
 */
public class Feature implements IFeature {

    int x;
    
    int y;
    
    int hits;

    String barcode;
    
    String gene;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Default Constructor is required by Jackson.
     */
    public Feature() {}

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
    
    @Override
    public String getBarcode() {
        return barcode;
    }

    @Override
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String getGene() {
        return this.gene;
    }

    @Override
    public void setGene(String gene) {
        this.gene = gene;
    }
    
    /**
     * Helper. Computes feature stats.
     *
     * @param features
     * @param overall_hit_quartiles
     * @param gene_pooled_hit_quartiles
     * @return [overall_feature_count, overall_hit_count, unique_gene_count,
     * unique_barcode_count]
     */
    public static int[] computeStats(Feature[] features, double[] overall_hit_quartiles, double[] gene_pooled_hit_quartiles) {
        int n = features.length;
        int sum = 0;
        List<Integer> hits = new ArrayList<Integer>(n);
        HashMap<String, Integer> pooledHits = new HashMap<String, Integer>(n);
        HashSet<String> pooledBarcodes = new HashSet<String>(n);
        for (Feature f : features) {
            String gene = f.getGene().toUpperCase();
            String barcode = f.getBarcode().toUpperCase();
            int h = f.getHits();
            sum += h;
            hits.add(h);
            if (pooledHits.containsKey(gene)) {
                pooledHits.put(gene, pooledHits.get(gene) + h);
            } else {
                pooledHits.put(gene, h);
            }
            pooledBarcodes.add(barcode);
        }
        Collections.sort(hits);
        ArrayList<Integer> poolHits = new ArrayList<Integer>(pooledHits.values());
        Collections.sort(poolHits);

        computeQuartiles(hits, overall_hit_quartiles);
        computeQuartiles(poolHits, gene_pooled_hit_quartiles);

        // overall_feature_count, overall_hit_count, unique_gene_count, unique_barcode_count
        return new int[]{n, sum, poolHits.size(), pooledBarcodes.size()};
    }

    /**
     * Helper. Computes quartiles of a sorted list.
     *
     * @param hits the sorted hit counts.
     * @param q the quartiles to be computed.
     */
    private static void computeQuartiles(List<Integer> hits, double[] q) {
        int n = hits.size();
        if (n == 1) {
            double val = hits.get(0);
            q[0] = val;
            q[1] = val;
            q[2] = val;
            q[3] = val;
            q[4] = val;
        }
        // Linear interpolation for intermediate values, exact at endpoints.
        q[0] = hits.get(0);
        q[4] = hits.get(n - 1);
        double[] idx = new double[]{0.25 * n - 0.25, 0.5 * n - 0.5, 0.75 * n - 0.75};
        for (int i = 0; i < 3; ++i) {
            int floor = (int) (Math.floor(idx[i]));
            int ceil = (int) (Math.ceil(idx[i]));
            double delta = idx[i] - floor;
            q[i + 1] = hits.get(floor) * (1.0 - delta) + hits.get(ceil) * delta;  // No prob if ceil==floor...
        }
    }
    
    /**
     * Parses features.
     * @param bytes raw file.
     * @param isGZipped true if raw file is gzipped.
     * @return the features.
     */
    public static Feature[] parse(byte[] bytes, boolean isGZipped) {
        Feature[] features = null;

        try {
            if (isGZipped) {
                bytes = gunzip(bytes);
            }
            
            // Specify that unknown parameters should not be mapped.
            ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            features = (Feature[]) mapper.readValue(bytes, Feature[].class);
            //logger.debug(" features in file: " + features.length);
            return features;
        } catch (IOException e) {
            e.printStackTrace();
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse feature file. Wrong format?" + e.getStackTrace().toString());
            throw new GenericException(resp);
        }
    }
    
}
