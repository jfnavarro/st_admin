/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.service;

import com.spatialtranscriptomics.model.Chip;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Interface for the chip service.
 */
public interface ChipService {

    /**
     * Returns a chip.
     * @param id chip ID.
     * @return the chip.
     */
    public Chip find(String id);

    /**
     * Returns all chips.
     * @return the chips.
     */
    public List<Chip> list();

    /**
     * Adds a chip.
     * @param chip the chip.
     * @return the chip with ID assigned.
     */
    public Chip create(Chip chip);

    /**
     * Adds a chip from a chip file. The chip parameters will be extracted from
     * the file and the original file will be stored in S3
     * @param chipFile the file.
     * @param name the name of the chip.
     * 
     * @throws IOException on read error.
     */
    public void addFromFile(CommonsMultipartFile chipFile, String name) throws IOException;

    /**
     * Updates a chip.
     * @param chip the chip.
     */
    public void update(Chip chip);

    /**
     * Deletes a chip.
     * @param id chip ID.
     */
    public void delete(String id);

}
