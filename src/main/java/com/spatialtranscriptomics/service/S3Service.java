/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.io.InputStream;
import java.util.List;

import com.spatialtranscriptomics.model.Feature;

/**
 * Interface for the Amazon S3 service.
 */
public interface S3Service {
	
	public void deleteExperimentData(String experimentId);

	public List<String> getInputFolders();
	
	public List<String> getIDFiles();
	
	public List<String> getReferenceAnnotation();
	
	public List<String> getReferenceGenome();
	
	public List<String> getBowtieFiles();
	
	public InputStream getFeaturesAsJson(String experimentId);
	
	public InputStream getFeaturesAsCSV(String experimentId);
	
	public List<Feature> getFeaturesAsList(String experimentId);
        
        public void deleteImageData(List<String> imageNames);
}
