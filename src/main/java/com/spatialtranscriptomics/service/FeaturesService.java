/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.model.FeaturesMetadata;
import com.spatialtranscriptomics.model.FeaturesWrapper;
import java.util.List;

/**
 * Interface for the features service.
 */
public interface FeaturesService {
    
    public void addUpdate(String id, byte[] bytes);
    
    public List<FeaturesMetadata> listMetadata();
    
    public FeaturesWrapper find(String id);
    
    public Feature[] parse(byte[] bytes);
}
