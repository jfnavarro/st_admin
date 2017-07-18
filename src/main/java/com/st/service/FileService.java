package com.st.service;

import com.st.model.FileMetadata;
import java.util.List;

/**
 * Interface for the features service.
 */
public interface FileService {
    
    /**
     * Adds or updates a file.
     * @param id the file ID.
     * @param gzipfile the file, gzipped in BASE64-encoding.
     */
    public void addUpdate(String id, byte[] gzipfile);
    
    /**
     * Lists metadata for all files.
     * @return the metadata.
     */
    public List<FileMetadata> listMetadata();
    
    /**
     * Finds the file of a dataset.
     * @param id the dataset ID.
     * @return the features file.
     */
    public byte[] find(String id);
    
}
