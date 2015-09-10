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
 * This class implements the the import chip form (used to allow the user to
 * import chip files). Does data validation using Hibernate validator
 * constraints.
 */
public class ChipForm {

    // Chip name, not filename. 
    @NotEmpty
    String name;

    // Filename. 
    @NotEmpty
    String fileName;

    // Uploaded file. 
    CommonsMultipartFile chipFile;

    public CommonsMultipartFile getChipFile() {
        return chipFile;
    }

    public void setChipFile(CommonsMultipartFile chipFile) {
        this.chipFile = chipFile;
        this.fileName = chipFile.getOriginalFilename();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return this.fileName;
    }

}
