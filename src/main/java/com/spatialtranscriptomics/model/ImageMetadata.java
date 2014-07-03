/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import java.util.Date;



/**
 * This bean class maps the ImageMetadata data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does validation using Hibernate validator constraints.
 * 
 * */

public class ImageMetadata implements IImageMetadata {

	String filename;
        Date created;
	Date lastModified;

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

        public Date getCreated() {
            return this.created;
	}

	public void setCreated(Date created) {
            this.created = created;
	}
        
	public Date getLastModified() {
            return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
            this.lastModified = lastModified;
	}

}
