/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import com.amazonaws.services.elasticmapreduce.model.JobFlowDetail;
import com.spatialtranscriptomics.form.PipelineExperimentForm;

/**
 * Interface for the Amazon EMR service.
 */
public interface EMRService {

	public JobFlowDetail findJobFlow(String jobFlowId);

	public String startJobFlow(PipelineExperimentForm j, String experimentId);

	public void stopJobFlow(String jobFlowId);

}
