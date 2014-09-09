/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.PipelineExperiment;

/**
 * Interface for the pipeline experiment service.
 */
public interface PipelineExperimentService {

	public PipelineExperiment find(String id);

	public List<PipelineExperiment> list();
	
	public List<PipelineExperiment> findForAccount(String accountId);

	public PipelineExperiment add(PipelineExperiment experiment);

	public void update(PipelineExperiment experiment);

	public void delete(String id);
}
