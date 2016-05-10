/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.form;

import javax.validation.Valid;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.st.model.Dataset;

/**
 * This class implements the model for the "edit dataset form" (used to edit
 * datasets). Different from the DatasetAddForm, this form does not *require* a
 * feature file (from user or from experiment output). Does validation using
 * Hibernate validator constraints.
 *
 */
public class DatasetEditForm {

    /** Dataset being created. */
    @Valid
    Dataset dataset;

    /** User uploaded features file. Empty if experiment output is fetched instead. */
    CommonsMultipartFile featureFile;   // not required when editing a dataset

    /** Features file is fetched from experiment output. Empty if user uploaded file is used instead. */
    String experimentId; // not required when editing a dataset

    /**
     * Constructor.
     */
    public DatasetEditForm() {
    }

    /**
     * Constructor.
     *
     * @param dataset dataset.
     */
    public DatasetEditForm(Dataset dataset) {
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
