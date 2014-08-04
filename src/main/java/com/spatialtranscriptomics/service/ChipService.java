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

	public Chip find(String id);

	public List<Chip> list();

	public Chip create(Chip chip);

	public Chip addFromFile(CommonsMultipartFile chipFile, String name) throws IOException;

	public void update(Chip chip);

	public void delete(String id);

}
