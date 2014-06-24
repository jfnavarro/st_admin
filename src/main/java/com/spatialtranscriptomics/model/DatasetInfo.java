/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * This bean class maps the DatasetInfo data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 */
public class DatasetInfo implements IDatasetInfo {

	String id;
	
	@NotBlank(message = "account_id must not be blank.")
	String account_id;
	
	@NotBlank(message = "dataset_id must not be blank.")
	String dataset_id;
	
	String comment;
	
	public DatasetInfo() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getDataset_id() {
		return dataset_id;
	}

	public void setDataset_id(String dataset_id) {
		this.dataset_id = dataset_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
