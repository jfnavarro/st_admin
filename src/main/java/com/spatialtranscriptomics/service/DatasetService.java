/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.Dataset;

/**
 * Interface for the dataset service.
 */
public interface DatasetService {

    /**
     * Finds a dataset by ID
     * @param id of the dataset
     * @return the Dataset
     */
    public Dataset find(String id);

    /**
     * Retrieves a list of present datasets filtered by user role
     * @return a list of Datasets
     */
    public List<Dataset> list();

    /**
     * Retrieves a list of present datasets by user ID
     * @param accountId of the user
     * @return a list of Datasets
     */
    public List<Dataset> listForAccount(String accountId);

    /**
     * Insert a Dataset in the DB
     * @param dataset to be inserted
     * @return the inserted Dataset with updated ID
     */
    public Dataset add(Dataset dataset);

    /**
     * Update a Dataset in the DB
     * @param dataset to be updated
     */
    public void update(Dataset dataset);

    /**
     * Deletes a Dataset in the DB by ID
     * @param id of the Dataset
     */
    public void delete(String id);
}
