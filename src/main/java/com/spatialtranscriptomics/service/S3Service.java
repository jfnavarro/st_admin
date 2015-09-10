/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.service;

import java.util.List;
import java.io.IOException;

/**
 * Interface for the Amazon S3 service mainly used for pipeline experiments.
 */
public interface S3Service {

    /**
     * Returns a list of experiment data input folders.
     * @return the folders.
     */
    public List<String> getInputFolders();

    /**
     * Returns a list of chips files.
     * @return the chips.
     */
    public List<String> getIDFiles();
    
    /**
     * Returns a list of available reference annotations.
     * @return the annotations.
     */
    public List<String> getReferenceAnnotation();

    /**
     * Returns a list of available reference genomes.
     * @return the genomes.
     */
    public List<String> getReferenceGenome();

    /**
     * Returns a list of available RNA contaminant genomes.
     * @return the genomes.
     */
    public List<String> getContaminantGenome();

    /**
     * Parsing helper. Returns a standard JSON representation
     * of the features of an experiment.
     * @param experimentId the experiment ID.
     * @return the JSON contents.
     * @throws java.io.IOException
     */
    public byte[] getFeaturesAsJson(String experimentId) throws IOException;

    /**
     * Parsing helper. Returns a standard CSV representation
     * of the features of an experiment.
     * @param experimentId the experiment ID.
     * @return the CSV contents.
     * @throws java.io.IOException
     */
    public byte[] getFeaturesAsCSV(String experimentId) throws IOException;  
    
     /**
     * Parsing helper. Returns a standard JSON representation of the QA stats
     * of an experiment.
     * @param experimentId the experiment ID.
     * @return the JSON contents.
     * @throws java.io.IOException
     */
    public byte[] getExperimentQA(String experimentId) throws IOException;  
}
