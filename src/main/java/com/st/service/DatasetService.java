package com.st.service;

import java.util.List;

import com.st.model.Dataset;

/**
 * Interface for the dataset service.
 */
public interface DatasetService {

    public Dataset find(String id);

    /**
     * Gives a list of all datasets for ADMIN users and granted datasets
     * for CM users
     * @return 
     */
    public List<Dataset> list();

    /**
     * Gives a list of all granted datasets for ADMIN and CM users
     * @param accountId the account to get the granted datasets from
     * @return a list of Datasets
     */
    public List<Dataset> listForAccount(String accountId);

    public Dataset add(Dataset dataset);

    public void update(Dataset dataset);

    public void delete(String id);
}
