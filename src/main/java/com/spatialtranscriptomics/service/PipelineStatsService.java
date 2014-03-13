/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.PipelineStats;

public interface PipelineStatsService {

	public PipelineStats find(String id);

	public List<PipelineStats> list();
	
	public PipelineStats findForPipelineExperiment(String expId);

	public PipelineStats add(PipelineStats stats);

	public void update(PipelineStats stats);

	public void delete(String id);
	

}
