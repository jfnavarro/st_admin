/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.model;

import java.util.List;

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
    
    String image_alignment_id;

    int overall_feature_count;
    int overall_hit_count;
    int unique_gene_count;
    int unique_barcode_count;
    double[] overall_hit_quartiles;
    double[] gene_pooled_hit_quartiles;
    String[] obo_foundry_terms;
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
    
    public String getId() {
        return id;
    }

    // id is set automatically by MongoDB
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_alignment_id() {
        return this.image_alignment_id;
    }

    public void setImage_alignment_id(String imal) {
        this.image_alignment_id = imal;
    }

    public String getTissue() {
        return this.tissue;
    }

    public void setTissue(String tissue) {
        this.tissue = tissue;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getOverall_feature_count() {
        return overall_feature_count;
    }

    public void setOverall_feature_count(int count) {
        this.overall_feature_count = count;
    }

    public int getUnique_gene_count() {
        return this.unique_gene_count;
    }

    public void setUnique_gene_count(int count) {
        this.unique_gene_count = count;
    }

    public int getUnique_barcode_count() {
        return this.unique_barcode_count;
    }

    public void setUnique_barcode_count(int count) {
        this.unique_barcode_count = count;
    }

    public int getOverall_hit_count() {
        return this.overall_hit_count;
    }

    public void setOverall_hit_count(int count) {
        this.overall_hit_count = count;
    }

    public double[] getOverall_hit_quartiles() {
        return this.overall_hit_quartiles;
    }

    public void setOverall_hit_quartiles(double[] quartiles) {
        this.overall_hit_quartiles = quartiles;
    }

    public double[] getGene_pooled_hit_quartiles() {
        return this.gene_pooled_hit_quartiles;
    }

    public void setGene_pooled_hit_quartiles(double[] quartiles) {
        this.gene_pooled_hit_quartiles = quartiles;
    }

    public String[] getObo_foundry_terms() {
        return obo_foundry_terms;
    }

    public void setObo_foundry_terms(String[] obo_foundry_terms) {
        this.obo_foundry_terms = obo_foundry_terms;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comm) {
        this.comment = comm;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean b) {
        this.enabled = b;
    }

    // Transient in API.
    public List<String> getGranted_accounts() {
        return this.granted_accounts;
    }

    // Transient in API.
    public void setGranted_accounts(List<String> grantedAccounts) {
        this.granted_accounts = grantedAccounts;
    }

    public String getCreated_by_account_id() {
        return this.created_by_account_id;
    }

    public void setCreated_by_account_id(String id) {
        this.created_by_account_id = id;
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
