package com.st.model;

import com.st.exceptions.GenericException;
import com.st.exceptions.GenericExceptionResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;

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

    /**
     * Default Constructor is required by Jackson.
     */
    public Feature() {}
    
    /**
     * Constructor.
     * @param x
     * @param y
     * @param hits
     * @param barcode
     * @param gene
     */
    public Feature(int x, int y, int hits, String barcode, String gene) {
        this.x = x;
        this.y = y;
        this.hits = hits;
        this.barcode = barcode;
        this.gene = gene;
    }
    
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

    @Override
    public int getHits() {
        return hits;
    }

    /**
     *
     * @param hits
     */
    @Override
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
    * Parses a file for the sake of stats computations.
    * @param bytes file contents
    * @param isGZipped true if gzipped file.
    * @param overall_hit_quartiles return value hit quartiles.
    * @param gene_pooled_hit_quartiles return value gene-pooled hit quartiles.
    * @return overall_feature_count, overall_hit_count, unique_gene_count, unique_barcode_count.
    */
    public static int[] parse(byte[] bytes, boolean isGZipped, 
            double[] overall_hit_quartiles, double[] gene_pooled_hit_quartiles) {
        File temp = null;
        int[] stats = null;
        
        try {
            temp = File.createTempFile("st_parsing", ".json");
            byte[] buffer = new byte[1024 * 10000];
            
            if (isGZipped) {
                               
                FileOutputStream out;
                try ( // Unzip and write to file.
                        GZIPInputStream gzis = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
                    out = new FileOutputStream(temp);
                    int len;
                    while ((len = gzis.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    //System.out.println("Finished unzipping");
                }
                out.close();
                
            } else {
                
                FileOutputStream out;
                try ( // Write to file.
                        ByteArrayInputStream bin = new ByteArrayInputStream(bytes)) {
                    out = new FileOutputStream(temp);
                    int len;
                    while ((len = bin.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                }
                out.close();
            }
            
            // Parse.
            stats = parseStreamingly(temp, overall_hit_quartiles, gene_pooled_hit_quartiles);
            
        } catch (IOException e) {
            //e.printStackTrace();
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse JSON feature file. Wrong format?" 
                    + Arrays.toString(e.getStackTrace()));
            throw new GenericException(resp);
        } finally {
            if (temp != null) {
                temp.delete();
            }
        }
        
        return stats;
    }
    
    /**
     * Helper to parse().
     */
    private static int[] parseStreamingly(File file, double[] overall_hit_quartiles, 
            double[] gene_pooled_hit_quartiles) throws IOException {
        
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createJsonParser(file);
        JsonToken current;
        current = jp.nextToken();
        if (current != JsonToken.START_ARRAY) {
          throw new IOException("Error: root should be object: quitting.");
        }
        
        int sum = 0;
        List<Integer> hits = new ArrayList<>(20000000);
        HashMap<String, Integer> pooledHits = new HashMap<>(2000000);
        HashSet<String> pooledBarcodes = new HashSet<>(100000);
        
        int n = 0;
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            // Parse.
            Feature feat = jp.readValueAs(Feature.class);
            
            // Update stats.
            n++;
            String gene = feat.getGene().toUpperCase();
            String barcode = feat.getBarcode().toUpperCase();
            int h = feat.getHits();
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
        ArrayList<Integer> poolHits = new ArrayList<>(pooledHits.values());
        Collections.sort(poolHits);

        computeQuartiles(hits, overall_hit_quartiles);
        computeQuartiles(poolHits, gene_pooled_hit_quartiles);

        return new int[]{n, sum, poolHits.size(), pooledBarcodes.size()};
    }
    
}
