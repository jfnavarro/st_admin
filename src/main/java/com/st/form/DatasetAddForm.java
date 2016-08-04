package com.st.form;

import javax.validation.Valid;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.st.model.Dataset;

/**
 * This class implements the model for the "add dataset form" (used to add
 * datasets). Does validation using Hibernate validator constraints.
 * 
 * TODO: Would be elegant to check this in a Hibernate validator if both
 * options should be available. At the moment the check is done in the DatasetController.
 *
 */
public class DatasetAddForm {

    // Dataset being created. 
    @Valid
    Dataset dataset;

    // User uploaded features file. 
    CommonsMultipartFile featureFile;

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

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

}
