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
    
}
