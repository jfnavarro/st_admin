package com.st.service;

/**
 * Interface for the features service.
 */
public interface FileService {
    
    /**
     * Adds or updates a file.
     * @param filename the name of the file
     * @param id the dataset ID.
     * @param gzipfile the file, gzipped in BASE64-encoding.
     */
    public void addUpdate(String filename, String id, byte[] gzipfile);
    
    /**
     * Removes a file
     * @param filename the name of the file
     * @param id the dataset ID
     */
    public void remove(String filename, String id);
    
    /**
     * Finds the file of a dataset.
     * @param filename the name of the file
     * @param id the dataset ID.
     * @return the features file.
     */
    public byte[] find(String filename, String id);
    
}
