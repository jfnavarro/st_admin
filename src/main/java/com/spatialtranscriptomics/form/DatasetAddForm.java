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
 * This class implements the model for the "add dataset form" (used to add
 * datasets). Does validation using Hibernate validator constraints.
 * 
 * TODO: Would be elegant to check this in a Hibernate validator if both
 * options should be available. At the moment the check is done in the DatasetController.
 *
 */
public class DatasetAddForm {

    /** Dataset being created. */
    @Valid
    Dataset dataset;

    /** User uploaded features file. Empty if experiment output is fetched instead. */
    CommonsMultipartFile featureFile;

    /** Features file is fetched from experiment output. Empty if user uploaded file is used instead. */
    String experimentId;

    /**
     * Constructor.
     */
    public DatasetAddForm() {
    }

    /**
     * Constructor.
     *
     * @param dataset the dataset.
     */
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
