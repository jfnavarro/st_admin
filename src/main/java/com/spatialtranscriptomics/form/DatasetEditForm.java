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
import javax.validation.constraints.AssertTrue;

/**
 * This class implements the model for the edit dataset form 
 * .Does validation using Hibernate validator constraints.
 */
public class DatasetEditForm {

    // Dataset being created. 
    @Valid
    Dataset dataset;

    // User uploaded features file. 
    // Empty if experiment output is fetched instead. 
    CommonsMultipartFile featureFile;   // not required when editing a dataset

    // User uploaded qa stats file. 
    CommonsMultipartFile qaFile;  // not required when editing a dataset 
    
    // Features file is fetched from experiment output.
    // Empty if user uploaded file is used instead. 
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

    @AssertTrue(message="You must select one feature file and it must be GZIP")
    private boolean isValid() {
        // validate that only one feature input is selected and it is gzip
        return (!featureFile.isEmpty() 
                && !featureFile.getOriginalFilename().endsWith("gz")
                && !featureFile.getOriginalFilename().endsWith("gzip"))
                || !(!featureFile.isEmpty() && !experimentId.isEmpty());
    }
  
    public CommonsMultipartFile getFeatureFile() {
        return featureFile;
    }

    public void setFeatureFile(CommonsMultipartFile featureFile) {
        this.featureFile = featureFile;
    }

    public CommonsMultipartFile getQaFile() {
        return qaFile;
    }

    public void setQaFile(CommonsMultipartFile qaFile) {
        this.qaFile = qaFile;
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
