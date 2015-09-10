/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.model;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

/**
 * This bean class maps the Dataset data retrieved from the ST API to the
 * application data model. This data model has to be aligned with the ST API
 * data model. Does data validation using Hibernate validator constraints.
 */
public class Dataset implements IDataset {

    String id;

    @NotBlank(message = "Name must not be blank.")
    String name;

    boolean enabled;

    @NotBlank(message = "Tissue must not be blank.")
    String tissue;

    @NotBlank(message = "Species must not be blank.")
    String species;
    
    @NotBlank(message = "Image Alignment must not be blank.")
    String image_alignment_id;
    
    //dynamic parameters
    Map<String,String> qa_parameters;
    
    String comment;

    // Transient in API.
    List<String> granted_accounts;

    String created_by_account_id;

    DateTime created_at;

    DateTime last_modified;

    /**
     * Default Constructor is required by Jackson.
     */
    public Dataset() {}
    
    @Override
    public String getId() {
        return id;
    }

    // id is set automatically by MongoDB
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getImage_alignment_id() {
        return this.image_alignment_id;
    }

    @Override
    public void setImage_alignment_id(String imal) {
        this.image_alignment_id = imal;
    }
    
    @Override
    public Map<String,String> getQa_parameters() {
        return this.qa_parameters;
    }
    
    @Override
    public void setQa_parameters(Map<String,String> qa_parameters) {
        this.qa_parameters = qa_parameters;
    }
    
    @Override
    public String getTissue() {
        return this.tissue;
    }

    @Override
    public void setTissue(String tissue) {
        this.tissue = tissue;
    }

    @Override
    public String getSpecies() {
        return this.species;
    }

    @Override
    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public void setComment(String comm) {
        this.comment = comm;
    }

    @Override
    public boolean getEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean b) {
        this.enabled = b;
    }

    // Transient in API.
    @Override
    public List<String> getGranted_accounts() {
        return this.granted_accounts;
    }

    // Transient in API.
    @Override
    public void setGranted_accounts(List<String> grantedAccounts) {
        this.granted_accounts = grantedAccounts;
    }

    @Override
    public String getCreated_by_account_id() {
        return this.created_by_account_id;
    }

    @Override
    public void setCreated_by_account_id(String id) {
        this.created_by_account_id = id;
    }

    @Override
    public DateTime getCreated_at() {
        return created_at;
    }

    @Override
    public void setCreated_at(DateTime created) {
        this.created_at = created;
    }

    @Override
    public DateTime getLast_modified() {
        return last_modified;
    }

}
