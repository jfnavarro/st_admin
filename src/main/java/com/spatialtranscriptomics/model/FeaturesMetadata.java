/*
 * Copyright (C) 2012 Spatial Transcriptomics AB
 * Read LICENSE for more information about licensing terms
 * Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 */

package com.spatialtranscriptomics.model;

import com.spatialtranscriptomics.util.StringOperations;
import java.beans.Transient;
import org.joda.time.DateTime;

/**
 * This class implements the FeaturesMetadata object. This has no correspondence with the API.
 */
public class FeaturesMetadata implements IFeaturesMetadata {

    String datasetId;
    String filename;
    DateTime lastModified;
    DateTime created;
    long size;  // file size as no of bytes.

    /**
     * Default Constructor is required by Jackson.
     */
    public FeaturesMetadata() {}
    
    @Override
    public String getDatasetId() {
        return this.datasetId;
    }
    
    @Override
    public void setDatasetId(String id) {
        this.datasetId = id;
    }
    
    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public DateTime getLastModified() {
        return this.lastModified;
    }

    @Override
    public void setLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public DateTime getCreated() {
        return this.created;
    }

    @Override
    public void setCreated(DateTime d) {
        this.created = d;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public void setSize(long size) {
        this.size = size;
    }

    @Transient
    @Override
    public String getReadableSize() {
        return StringOperations.humanReadableByteCount(this.size);
    }

}
