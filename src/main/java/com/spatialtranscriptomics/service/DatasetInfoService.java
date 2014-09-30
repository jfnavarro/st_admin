/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.DatasetInfo;

/**
 * Interface for the datasetinfo service.
 */
public interface DatasetInfoService {

    /**
     * Returns a dataset info.
     * @param id the ID.
     * @return the dataset info.
     */
    public DatasetInfo find(String id);

    /**
     * Returns a list of all dataset infos.
     * @return the dataset infos.
     */
    public List<DatasetInfo> list();

    /**
     * Returns all dataset infos for an account.
     * @param accountId the account ID.
     * @return the dataset infos.
     */
    public List<DatasetInfo> listForAccount(String accountId);

    /**
     * Returns all datasets for a dataset.
     * @param datasetId the dataset ID.
     * @return the dataset infos.
     */
    public List<DatasetInfo> listForDataset(String datasetId);

    /**
     * Adds a dataset info.
     * @param datasetinfo the dataset info.
     * @return the dataset info with ID assigned.
     */
    public DatasetInfo add(DatasetInfo datasetinfo);

    /**
     * Updates a dataset info.
     * @param datasetinfo the dataset info.
     */
    public void update(DatasetInfo datasetinfo);

    /**
     * Deletes a dataset info.
     * @param id the dataset info ID.
     */
    public void delete(String id);

}
