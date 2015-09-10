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
 * This class implements the model for the "import image form" (used to import
 * image files). Does validation using Hibernate validator constraints.
 *
 */
public class ImageForm {

    // User uploaded image file. 
    CommonsMultipartFile imageFile;

    // Filename. 
    @NotEmpty
    String fileName;

    public CommonsMultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(CommonsMultipartFile imageFile) {
        this.imageFile = imageFile;
        this.fileName = imageFile.getOriginalFilename();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
