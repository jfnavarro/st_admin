/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.st.model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;


/**
 * This bean class maps the Account data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 */
public class Account implements IAccount {

	String id;
	
	@NotBlank
	@Email(message = "Username must be a valid email address.")
	String username;
	
	@Length(min = 4, message = "Password must have at least 4 characters.")
	String password;
	
        @JsonIgnore
        String passwordRepeat;
        
	@NotBlank
	String role;
        
	boolean enabled;
	
	String institution;
	
	String first_name;
	
	String last_name;
	
	String street_address;
	
	String city;
	
	String postcode;
	
	String country;
	
        // Transient in API.
	List<String> granted_datasets;
        
        DateTime created_at;
	
        DateTime last_modified;
        
        /**
         * Default Constructor is required by Jackson.
         */
        public Account() {}
        
	// id is set automatically by MongoDB
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
        
        @JsonIgnore
        public String getPasswordRepeat() {
		return passwordRepeat;
	}

        @JsonIgnore
	public void setPasswordRepeat(String password) {
            this.passwordRepeat = password;
	}
	
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

        // Transient in API.
	public List<String> getGranted_datasets() {
		return this.granted_datasets;
	}
	
        // Transient in API.
	public void setGranted_datasets(List<String> grantedDatasets) {
		this.granted_datasets = grantedDatasets;
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
