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
 * This class implements the model for the "add dataset form" (used to add datasets). 
 * Does validation using Hibernate validator constraints.
 * 
 * */

public class DatasetAddForm {

	@Valid
	Dataset dataset;

	// either file or experimentId is required.
	// TODO: Would be elegant to check this in a Hibernate validator if both
	// options should be available. At the moment the check is done in the DatasetController

	// @FileSelectedConstraint // this validator is not used anymore, because also experiment can
	// be selected in stead of file.
	CommonsMultipartFile featureFile;

	String experimentId;

	public DatasetAddForm() {
	}

	public DatasetAddForm(Dataset dataset) {
		this.dataset = dataset;
	}

	public CommonsMultipartFile getFeatureFile() {
		return featureFile;
	}

	public void setFeatureFile(CommonsMultipartFile featureFile) {
		this.featureFile = featureFile;
	}

	public String getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

}
