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

        // NOT USED RIGHT NOW.
        //String chipId; // not required when editing a dataset
        
        /**
         * Constructor.
         */
	public DatasetEditForm() {
	}

        /**
         * Constructor.
         * @param dataset dataset.
         */
	public DatasetEditForm(Dataset dataset) {
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
         * @return experiment ID.
         */
	public String getExperimentId(){
		return experimentId;
	}
	
        /**
         * Sets the experiment ID.
         * @param experimentId experiment ID.
         */
	public void setExperimentId(String experimentId){
		this.experimentId = experimentId;
	}
        
//        /**
//         * Returns the chip ID.
//         * @return the chip ID.
//         */
//	public String getChipId() {
//            if (chipId != null && (chipId.equals("") || chipId.equals("None"))) {
//                chipId = null;
//            }
//            return chipId;
//	}
//
//        /**
//         * Sets the chip ID.
//         * @param chipId the chip ID.
//         */
//	public void setChipId(String chipId) {
//            if (chipId != null && (chipId.equals("") || chipId.equals("None"))) {
//                this.chipId = null;
//            } else {
//                this.chipId = chipId;
//            }
//	}

        /**
         * Returns the dataset.
         * @return the dataset.
         */
	public Dataset getDataset() {
		return dataset;
	}

        /**
         * Sets the dataset
         * @param dataset the dataset.
         */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

}
