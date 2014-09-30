/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.model.FeaturesMetadata;
import com.spatialtranscriptomics.model.S3Resource;
import java.util.List;

/**
 * Interface for the features service.
 */
public interface FeaturesService {
    
    /**
     * Adds or (for an existing file) updates a features file.
     * @param id the dataset ID.
     * @param file the file.
     */
    public void addUpdate(String id, S3Resource file);
    
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
    public S3Resource find(String id);
    
    /**
     * Parses an features file into a list of model objects.
     * @param bytes the raw decompressed file.
     * @return the model objects.
     */
    public Feature[] parse(byte[] bytes);
}
