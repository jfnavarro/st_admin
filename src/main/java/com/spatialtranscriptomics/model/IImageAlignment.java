/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import org.joda.time.DateTime;

/**
 * This interface defines the ImageAlignment model. Should reflect the corresponding model in ST API.
 */
public interface IImageAlignment {

    public String getId();

    public void setId(String id);
	
    public String getName();
	
    public void setName(String name);
	
    public String getChip_id();
	
    public void setChip_id(String id);
	
    public String getFigure_red();
	
    public void setFigure_red(String fig);
	
    public String getFigure_blue();
	
    public void setFigure_blue(String fig);
	
    public double[] getAlignment_matrix();
	
    public void setAlignment_matrix(double[] arr);
        
    public DateTime getCreated_at();

    public void setCreated_at(DateTime created);
	
    public DateTime getLast_modified();
}