/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import javax.validation.Valid;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * This class implements the model for the "edit dataset form" (used to edit datasets). 
 * Different from the DatasetAddForm, this form does not require a featureFile.
 * Does validation using Hibernate validator constraints.
 * 
 */


public class DatasetEditForm {

	
	
	@Valid
	Dataset dataset;
	
	CommonsMultipartFile featureFile; // not required when editing a dataset
	
	String experimentId; // not required when editing a dataset

	public DatasetEditForm() {
	}

	public DatasetEditForm(Dataset dataset) {
		this.dataset = dataset;
	}

	public CommonsMultipartFile getFeatureFile() {
		return featureFile;
	}

	public void setFeatureFile(CommonsMultipartFile featureFile) {
		this.featureFile = featureFile;
	}
	
	public String getExperimentId(){
		return experimentId;
	}
	
	public void setExperimentId(String experimentId){
		this.experimentId = experimentId;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

}
