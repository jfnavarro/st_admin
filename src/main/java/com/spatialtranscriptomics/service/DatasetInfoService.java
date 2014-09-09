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

	public DatasetInfo find(String id);

	public List<DatasetInfo> list();
	
	public List<DatasetInfo> listForAccount(String accountId);
	
	public List<DatasetInfo> listForDataset(String datasetId);

	public DatasetInfo add(DatasetInfo datasetinfo);

	public void update(DatasetInfo datasetinfo);

	public void delete(String id);

}
