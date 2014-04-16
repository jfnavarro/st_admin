/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import org.hibernate.validator.constraints.NotBlank;


/**
 * This bean class maps the PipelineExperiment data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 * 
 * */

public class PipelineExperiment implements IPipelineExperiment {

	String id;
	
	@NotBlank(message = "Name must not be blank.")
	String name;
	
	@NotBlank(message = "EMR job ID must not be blank.")
	String emr_jobflow_id;
	
	@NotBlank(message = "Account must not be blank.")
	String account_id;

	// id is set automatically by MongoDB
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmr_jobflow_id() {
		return this.emr_jobflow_id;
	}

	public void setEmr_jobflow_id(String emrJobflowId) {
		this.emr_jobflow_id = emrJobflowId;
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String id) {
		this.account_id = id;
	}

}
