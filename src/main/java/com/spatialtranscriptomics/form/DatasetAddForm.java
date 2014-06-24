/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.form;

import javax.validation.Valid;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.spatialtranscriptomics.model.Dataset;

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
	// be selected instead of file.
	CommonsMultipartFile featureFile;

	String experimentId;

        /**
         * Constructor.
         */
	public DatasetAddForm() {
	}

        /**
         * Constructor.
         * @param dataset the dataset.
         */
	public DatasetAddForm(Dataset dataset) {
		this.dataset = dataset;
	}

        /**
         * Returns the feature file.
         * @return the feature file.
         */
	public CommonsMultipartFile getFeatureFile() {
		return featureFile;
	}

        /**
         * Sets the feature file.
         * @param featureFile feature file.
         */
	public void setFeatureFile(CommonsMultipartFile featureFile) {
		this.featureFile = featureFile;
	}

        /**
         * Returns the experiment ID.
         * @return the experiment ID.
         */
	public String getExperimentId() {
		return experimentId;
	}

        /**
         * Sets the experiment ID.
         * @param experimentId the experiment ID.
         */
	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}

        /**
         * Returns the dataset.
         * @return the dataset.
         */
	public Dataset getDataset() {
		return dataset;
	}

        /**
         * Sets the dataset.
         * @param dataset the dataset.
         */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

}
