/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.model;

import com.st.util.StringOperations;
import java.beans.Transient;
import org.joda.time.DateTime;

/**
 * This bean class maps the ImageMetadata data retrieved from the ST API to the
 * application data model. This data model has to be aligned with the ST API
 * data model. Does validation using Hibernate validator constraints.
 */
public class ImageMetadata implements IImageMetadata {

    String imageType;
    String filename;
    DateTime created;
    DateTime lastModified;
    long size;  // file size in no of bytes.

    /**
     * Default constructor is required by Jackson.
     */
    public ImageMetadata() {}
    
    @Override
    public String getImageType() {
        return this.imageType;
    }

    @Override
    public void setImageType(String imageType) {
        this.imageType = imageType;
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
    public DateTime getCreated() {
        return this.created;
    }

    @Override
    public void setCreated(DateTime created) {
        this.created = created;
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
