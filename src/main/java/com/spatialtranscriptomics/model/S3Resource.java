/*
 * Copyright (C) 2012 Spatial Transcriptomics AB
 * Read LICENSE for more information about licensing terms
 * Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 */

package com.spatialtranscriptomics.model;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * This class wraps an arbitrary file resource from S3 into JSON.
 * The file is stored in a  byte array, and the size of the byte array and
 * the content type is passed along. A content encoding can be specified
 * if desired. 
 * <p/>
 * Note: Sending contents of arbitrary types may yield difficulties, whereas JSON often works elegantly.
 */
public class S3Resource implements IS3Resource {
    
    String contentType;
    
    String contentEncoding;
    
    String filename;
    
    byte[] file;
    
    long size;
    
    /**
     * Default constructor is a must for Jackson parsing.
     */
    public S3Resource() {}
    
    /**
     * Constructor. Size is set automatically.
     * @param contentType content type, e.g. "application/xml".
     * @param filename filename.
     * @param file file contents.
     */
    public S3Resource(String contentType, String filename, byte[] file) {
        this(contentType, "", filename, file);
    }
    
    /**
     * Constructor. Size is set automatically.
     * @param contentType content type, e.g. "application/xml".
     * @param contentEncoding content encoding, e.g. "gzip".
     * @param filename filename.
     * @param file file contents.
     */
    public S3Resource(String contentType, String contentEncoding , String filename, byte[] file) {
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.filename = filename;
        this.file = file;
        this.size = file.length;
    }
    
    /**
     * Static constructor. Size is set automatically, as is content encoding.
     * Gzips the contents.
     * @param contentType content type, e.g. "application/xml"
     * @param filename filename.
     * @param file file contents.
     * @return the resource.
     */
    public static S3Resource createGZipS3Resource(String contentType, String filename, byte[] file) throws IOException {
        ByteArrayOutputStream zipbytesbos = new ByteArrayOutputStream(file.length / 20);
        BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(zipbytesbos), file.length / 20);
        bos.write(file);
        bos.flush();
        bos.close();
        zipbytesbos.close();
        byte[] zipbytes = zipbytesbos.toByteArray();
        //System.out.println("Suceeded zipping: " + zipbytes.length + " bytes (compression factor " + (bytes.length / (double) zipbytes.length));
        S3Resource wrap = new S3Resource(contentType, "gzip", filename, zipbytes);
        return wrap;
    }
    
    @Override
    public String getContentType() {
        return this.contentType;
    }
    
    @Override
    public void setContentType(String type) {
        this.contentType = type;
    }
    
    @Override
    public String getContentEncoding() {
        return this.contentEncoding;
    }
    
    @Override
    public void setContentEncoding(String encoding) {
        this.contentEncoding = encoding;
    }
    
    @Override
    public String getFilename() {
        return filename;
    }
    
    @Override
    public void setFilename(String id) {
        this.filename = id;
    }
    
    @Override
    public byte[] getFile() {
        return this.file;
    }
    
    @Override
    public void setFile(byte[] file) {
        this.file = file;
    }
    
    @Override
    public long getSize() {
        return this.size;
    }
    
    @Override
    public void setSize(long size) {
        this.size = size;
    }
}
