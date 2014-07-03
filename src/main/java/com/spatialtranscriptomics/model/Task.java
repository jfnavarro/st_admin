/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * This bean class maps the Task data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 * 
 * */
public class Task implements ITask {

	String id;
	
	@NotBlank(message = "Name must not be blank.")
	String name;
	
	@NotBlank(message = "Account must not be blank.")
	String account_id;
	
	String status;
	
	@DateTimeFormat
	Date start;
	
	@DateTimeFormat
	Date end;
	
	String[] selection_ids;
	
	String parameters;

	String result_file;
        
        DateTime created_at;
	
        DateTime last_modified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String[] getSelection_ids() {
		return selection_ids;
	}

	public void setSelection_ids(String[] selection_ids) {
		this.selection_ids = selection_ids;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public String getResult_file() {
		return result_file;
	}

	public void setResult_file(String file) {
		this.result_file = file;
	}
        
        public DateTime getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(DateTime created) {
		this.created_at = created;
	}

	public DateTime getLast_modified() {
		return last_modified;
	}
}
