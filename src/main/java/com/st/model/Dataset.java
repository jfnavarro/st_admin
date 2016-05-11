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

    @NotBlank(message = "Image alignment must not be blank.")
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

    /**
     *
     * @return
     */
    @Override
    public int getOverall_feature_count() {
        return overall_feature_count;
    }

    @Override
    public void setOverall_feature_count(int count) {
        this.overall_feature_count = count;
    }

    @Override
    public int getUnique_gene_count() {
        return this.unique_gene_count;
    }

    @Override
    public void setUnique_gene_count(int count) {
        this.unique_gene_count = count;
    }

    /**
     *
     * @return
     */
    @Override
    public int getUnique_barcode_count() {
        return this.unique_barcode_count;
    }

    @Override
    public void setUnique_barcode_count(int count) {
        this.unique_barcode_count = count;
    }

    /**
     *
     * @return
     */
    @Override
    public int getOverall_hit_count() {
        return this.overall_hit_count;
    }

    @Override
    public void setOverall_hit_count(int count) {
        this.overall_hit_count = count;
    }

    /**
     *
     * @return
     */
    @Override
    public double[] getOverall_hit_quartiles() {
        return this.overall_hit_quartiles;
    }

    @Override
    public void setOverall_hit_quartiles(double[] quartiles) {
        this.overall_hit_quartiles = quartiles;
    }

    @Override
    public double[] getGene_pooled_hit_quartiles() {
        return this.gene_pooled_hit_quartiles;
    }

    @Override
    public void setGene_pooled_hit_quartiles(double[] quartiles) {
        this.gene_pooled_hit_quartiles = quartiles;
    }

    @Override
    public String[] getObo_foundry_terms() {
        return obo_foundry_terms;
    }

    @Override
    public void setObo_foundry_terms(String[] obo_foundry_terms) {
        this.obo_foundry_terms = obo_foundry_terms;
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

    public String getCreated_by_account_id() {
        return this.created_by_account_id;
    }

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

    @Override
    public String getImage_alignment_id() {
        return image_alignment_id;
    }

    @Override
    public void setImage_alignment_id(String imal) {
        this.image_alignment_id = imal;
    }

}
