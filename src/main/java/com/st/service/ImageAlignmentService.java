/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.service;

import java.util.List;

import com.st.model.ImageAlignment;

/**
 * Interface for the image alignment service.
 */
public interface ImageAlignmentService {

    /**
     * Retrieves an image alignment.
     * @param id the ID.
     * @return the image alignment.
     */
    public ImageAlignment find(String id);

    /**
     * Lists all image alignments.
     * @return the image alignment.
     */
    public List<ImageAlignment> list();

    /**
     * Retrieves the image alignments for a given chip.
     * @param chipId the chip ID.
     * @return the image alignments.
     */
    public List<ImageAlignment> findForChip(String chipId);

    /**
     * Creates an image alignment.
     * @param imal the alignment.
     * @return the alignment with ID assigned.
     */
    public ImageAlignment create(ImageAlignment imal);

    /**
     * Updates an image alignment.
     * @param imal the alignment.
     */
    public void update(ImageAlignment imal);

    /**
     * Deletes an image alignment.
     * @param id the ID.
     */
    public void delete(String id);

}
