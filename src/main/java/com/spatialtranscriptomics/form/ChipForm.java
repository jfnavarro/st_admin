/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * This class implements the model for the "import chip form" (used to import chip files). 
 * Does data validation using Hibernate validator constraints.
 * 
 * */
public class ChipForm {

	@NotEmpty
	String name;
	
	@NotEmpty
	String fileName;
	
	CommonsMultipartFile chipFile;

        /**
         * Returns the chip file.
         * @return the chip file.
         */
	public CommonsMultipartFile getChipFile() {
		return chipFile;
	}

        /**
         * Sets the chip file.
         * @param chipFile the file.
         */
	public void setChipFile(CommonsMultipartFile chipFile) {
		this.chipFile = chipFile;
		this.fileName = chipFile.getOriginalFilename();
	}

        /**
         * Returns the name.
         * @return the name.
         */
	public String getName() {
		return name;
	}

        /**
         * Sets the name.
         * @param name the name.
         */
	public void setName(String name) {
		this.name = name;
	}
	
        /**
         * Returns the filename.
         * @return the filename.
         */
	public String getFileName() {
		return this.fileName;
	}

}
