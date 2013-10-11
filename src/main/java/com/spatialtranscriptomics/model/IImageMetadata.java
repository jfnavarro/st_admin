/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import java.util.Date;

/**
 * This interface defines the ImageMetadata model. Must to the same as the ImageMetadata model in ST API
 */

public interface IImageMetadata {

	public String getFilename();

	public Date getLastModified();

}
