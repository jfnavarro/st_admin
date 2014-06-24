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
 * This class implements the model for the "import image form" (used to import image files). 
 * Does validation using Hibernate validator constraints.
 * 
 */

public class ImageForm {


	CommonsMultipartFile imageFile;
	
	@NotEmpty
	String fileName;

        /**
         * Returns the image file.
         * @return the image file.
         */
	public CommonsMultipartFile getImageFile() {
		return imageFile;
	}

        /**
         * Sets the image file.
         * @param imageFile image file.
         */
	public void setImageFile(CommonsMultipartFile imageFile) {
		this.imageFile = imageFile;
		this.fileName = imageFile.getOriginalFilename();
	}

        /**
         * Returns the file name.
         * @return file name.
         */
	public String getFileName() {
		return fileName;
	}
	
        /**
         * Sets the filename.
         * @param fileName filename.
         */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
}
