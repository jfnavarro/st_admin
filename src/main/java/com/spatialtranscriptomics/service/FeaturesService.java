/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import com.spatialtranscriptomics.model.FeaturesMetadata;
import java.util.List;

/**
 * Interface for the features service.
 */
public interface FeaturesService {
    
    /**
     * Adds or (for an existing file) updates a features file.
     * @param id the dataset ID.
     * @param file the file in byte array format.
     */
    public void addUpdate(String id, byte[] file);
    
    /**
     * Lists features metadata for all datasets.
     * @return the metadata.
     */
    public List<FeaturesMetadata> listMetadata();
    
    /**
     * Finds and returns the features file of a dataset.
     * @param id the dataset ID.
     * @return the features file in byte array.
     */
    public String find(String id);
    
}
