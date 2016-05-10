/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.service;

import java.util.List;

import com.st.model.Dataset;

/**
 * Interface for the dataset service.
 */
public interface DatasetService {

    public Dataset find(String id);

    public List<Dataset> list();

    public List<Dataset> listForAccount(String accountId);

    public Dataset add(Dataset dataset);

    public void update(Dataset dataset);

    public void delete(String id);
}
