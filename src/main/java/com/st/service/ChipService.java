package com.st.service;

import com.st.model.Chip;
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
     * Adds a chip from a chip file.
     * @param chipFile the file.
     * @param name the name of the chip.
     * @return the chip with ID assigned.
     * @throws IOException on read error.
     */
    public Chip addFromFile(CommonsMultipartFile chipFile, String name) throws IOException;

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
