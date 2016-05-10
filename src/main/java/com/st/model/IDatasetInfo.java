/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.st.model;

/**
 * This interface defines the DatasetInfo model. Should reflect the corresponding model in ST API.
 */
public interface IDatasetInfo {

	public String getId();
	
	public void setId(String id);
	
	public String getAccount_id();
	
	public void setAccount_id(String id);
	
	public String getDataset_id();
	
	public void setDataset_id(String id);
	
	public String getComment();
	
	public void setComment(String id);
	
}
