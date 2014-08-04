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
	
	//@NotBlank(message = "Account must not be blank.")
	String account_id;
    
        @NotBlank(message = "EMR job ID must not be blank.")
	String emr_jobflow_id;
        
        String emr_state;
        
        DateTime emr_creation_date_time;
        
        DateTime emr_end_date_time;
        
        String emr_last_state_change_reason;
        
        DateTime created_at;
	
        DateTime last_modified;

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

        public String getEmr_state() {
            return this.emr_state;
        }
        
        public void setEmr_state(String state) {
            this.emr_state = state;
        }

        public DateTime getEmr_creation_date_time() {
            return this.emr_creation_date_time;
        }
        
        public void setEmr_creation_date_time(DateTime creationDateTime) {
            this.emr_creation_date_time = creationDateTime;
        }
        
        public DateTime getEmr_end_date_time() {
            return this.emr_end_date_time;
        }

        public void setEmr_end_date_time(DateTime endDateTime) {
            this.emr_end_date_time = endDateTime;
        }
        
        public String getEmr_last_state_change_reason() {
            return emr_last_state_change_reason;
        }

        public void setEmr_last_state_change_reason(String lastStateChangeReason) {
            this.emr_last_state_change_reason = lastStateChangeReason;
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
