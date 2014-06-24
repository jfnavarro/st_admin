/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.Selection;

/**
 * Interface for the selection service.
 */
public interface SelectionService {

	public Selection find(String id);

	public List<Selection> list();
	
	public List<Selection> findForAccount(String accountId);
	
	public List<Selection> findForDataset(String datasetId);
	
	public List<Selection> findForTask(String taskId);

	public Selection add(Selection sel);

	public void update(Selection sel);

	public void delete(String id);
	
	public void deleteForDataset(String datasetId);
}
