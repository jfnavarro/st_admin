package com.st.form;

import javax.validation.Valid;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.st.model.Dataset;
import java.util.List;

/**
 * This class implements the model for the "edit dataset form" (used to edit
 * datasets). Different from the DatasetAddForm, this form does not *require* a
 * feature file (from user or from experiment output). Does validation using
 * Hibernate validator constraints.
 *
 */
public class DatasetEditForm {

    // Dataset being created. 
    @Valid
    Dataset dataset;

    // User uploaded st data file. 
    CommonsMultipartFile dataFile;   // not required when editing a dataset
    
    // User uploaded extra files
    List<CommonsMultipartFile> extraFiles; // not required when editing

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

    public CommonsMultipartFile getDataFile() {
        return dataFile;
    }

    public void setFeatureFile(CommonsMultipartFile featureFile) {
        this.dataFile = featureFile;
    }

    public List<CommonsMultipartFile> getExtraFiles() {
        return extraFiles;
    }

    public void setFeatureFile(List<CommonsMultipartFile> extraFiles) {
        this.extraFiles = extraFiles;
    }
    
    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

}
