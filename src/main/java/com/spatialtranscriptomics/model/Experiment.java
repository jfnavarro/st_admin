/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * This bean class maps the Experiment data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 * 
 * */

public class Experiment implements IExperiment {

	String id;

	@NotEmpty
	String name;

	String emr_jobflow_id;

	Date created;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmr_Jobflow_id() {
		return this.emr_jobflow_id;
	}

	public void setEmr_Jobflow_id(String emrJobflowId) {
		this.emr_jobflow_id = emrJobflowId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
