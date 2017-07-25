package com.st.form;

import javax.validation.Valid;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.st.model.Dataset;
import java.util.List;

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

    // User uploaded data file. 
    CommonsMultipartFile dataFile;

    // User uploaded extra files
    List<CommonsMultipartFile> extraFiles;
    
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

    public CommonsMultipartFile getDataFile() {
        return dataFile;
    }
    
    public void setDataFile(CommonsMultipartFile file) {
        this.dataFile = file;
    }
    
    public List<CommonsMultipartFile> getExtraFiles() {
        return extraFiles;
    }
    
    public void setExtraFiles(List<CommonsMultipartFile> files) {
        this.extraFiles = files;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

}
