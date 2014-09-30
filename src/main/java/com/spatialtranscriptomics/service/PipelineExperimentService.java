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

    /**
     * Retrieves an experiment.
     * @param id the experiment ID.
     * @return the experiment.
     */
    public PipelineExperiment find(String id);

    /**
     * Lists all experiments.
     * @return the experiments.
     */
    public List<PipelineExperiment> list();

    /**
     * Retrieves the experiments for an account.
     * @param accountId the account ID.
     * @return the experiments.
     */
    public List<PipelineExperiment> findForAccount(String accountId);

    /**
     * Adds an experiment.
     * @param experiment the experiment.
     * @return the experiment with ID assigned.
     */
    public PipelineExperiment add(PipelineExperiment experiment);

    /**
     * Updates an experiment.
     * @param experiment experiment.
     */
    public void update(PipelineExperiment experiment);

    /**
     * Deletes an experiment.
     * @param id the ID of the experiment.
     */
    public void delete(String id);
}
