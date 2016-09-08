package com.st.service;

import com.st.model.FeaturesMetadata;
import java.util.List;

/**
 * Interface for the features service.
 */
public interface FeaturesService {
    
    /**
     * Adds or updates a features file.
     * @param id the dataset ID.
     * @param gzipfile the file, gzipped in BASE64-encoding.
     */
    public void addUpdate(String id, byte[] gzipfile);
    
    /**
     * Lists features metadata for all datasets.
     * @return the metadata.
     */
    public List<FeaturesMetadata> listMetadata();
    
    /**
     * Finds the features file of a dataset.
     * @param id the dataset ID.
     * @return the features file.
     */
    public byte[] find(String id);
    
}
