/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.PipelineStats;

/**
 * Interface for the pipelinestats service.
 */
public interface PipelineStatsService {

    /**
     * Retrieves a specified stats object.
     * @param id the stats ID.
     * @return the stats.
     */
    public PipelineStats find(String id);

    /**
     * Retrieves all stats.
     * @return the stats.
     */
    public List<PipelineStats> list();

    /**
     * Retrieves the stats of a pipeline experiment.
     * @param expId the experiment ID.
     * @return the stats.
     */
    public PipelineStats findForPipelineExperiment(String expId);

    /**
     * Adds a stats object.
     * @param stats the stats.
     * @return the stats object with ID assigned.
     */
    public PipelineStats add(PipelineStats stats);

    /**
     * Updates a stats object.
     * @param stats the stats.
     */
    public void update(PipelineStats stats);

    /**
     * Deletes a stats object.
     * @param id the stats ID.
     */
    public void delete(String id);
}
