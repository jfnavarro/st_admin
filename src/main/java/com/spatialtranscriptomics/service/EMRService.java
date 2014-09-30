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

    /**
     * Returns the jobflow details for a certain job.
     * NOTE: Amazon will not return details for jobs older
     * than a certain time (currently a few months).
     * @param jobFlowId the job ID.
     * @return the details.
     */
    public JobFlowDetail findJobFlow(String jobFlowId);

    /**
     * Starts a job on Amazon EMR.
     * @param form the experiment form with settings.
     * @param experimentId the experiment ID.
     * @return a status String from Amazon.
     */
    public String startJobFlow(PipelineExperimentForm form, String experimentId);

    /**
     * Stops a job.
     * @param jobFlowId the job ID.
     */
    public void stopJobFlow(String jobFlowId);

}
