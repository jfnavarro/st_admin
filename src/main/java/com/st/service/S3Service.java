/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.service;

import java.util.List;

import com.st.model.Feature;
import java.io.IOException;

/**
 * Interface for the Amazon S3 service.
 */
public interface S3Service {

    /**
     * Deletes the pipeline experiment data on S3 for an experiment.
     * @param experimentId the experiment ID.
     */
    public void deleteExperimentData(String experimentId);

    /**
     * Returns a list of experiment data input folders.
     * @return the folders.
     */
    public List<String> getInputFolders();

    /**
     * Returns a list of experiment ID files.
     * @return the ID files.
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
     * Returns a list of available Bowtie files.
     * @return the Bowtie files.
     */
    public List<String> getBowtieFiles();

    /**
     * Parsing helper. Returns a standard JSON representation
     * of the features of an experiment.
     * @param experimentId the experiment ID.
     * @return the JSON contents.
     */
    public byte[] getFeaturesAsJson(String experimentId) throws IOException;

    /**
     * Parsing helper. Returns a standard CSV representation
     * of the features of an experiment.
     * @param experimentId the experiment ID.
     * @return the CSV contents.
     */
    public byte[] getFeaturesAsCSV(String experimentId) throws IOException;

    /**
     * Parsing helper. Returns a Features array
     * of the features of an experiment.
     * @param experimentId the experiment ID.
     * @return the Features array contents.
     */
    public List<Feature> getFeaturesAsList(String experimentId) throws IOException;

    
}
