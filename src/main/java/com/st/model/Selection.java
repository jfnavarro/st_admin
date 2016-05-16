package com.st.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * This bean class maps the Selection data retrieved from the ST API to the
 * application data model. This data model has to be aligned with the ST API
 * data model. Does data validation using Hibernate validator constraints.
 */
public class Selection implements ISelection {

    String id;

    @NotBlank(message = "Name must not be blank.")
    String name;

    @NotBlank(message = "Dataset must not be blank.")
    String dataset_id;

    @NotBlank(message = "Account must not be blank.")
    String account_id;

    @NotEmpty(message = "Gene nomenclatures with stats must not be empty.")
    List<String[]> gene_hits = new ArrayList<>();

    boolean enabled;

    String type;

    String status;

    String comment;

    DateTime created_at;

    DateTime last_modified;

    /**
     * Default constructor is required by Jackson.
     */
    public Selection() {}
    
    @Override
    public String getId() {
        return id;
    }

    // auto-set by mongo.
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean getEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public List<String[]> getGene_hits() {
        return gene_hits;
    }

    @Override
    public void setGene_hits(List<String[]> gene_hits) {
        this.gene_hits = gene_hits;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDataset_id() {
        return dataset_id;
    }

    @Override
    public void setDataset_id(String dataset_id) {
        this.dataset_id = dataset_id;
    }

    /**
     *
     * @return
     */
    @Override
    public String getAccount_id() {
        return account_id;
    }

    @Override
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getGene(int i) {
        return (gene_hits.get(i)[0]);
    }

    @Override
    public int getHit_count(int i) {
        return Integer.parseInt(gene_hits.get(i)[1]);
    }

    @Override
    public double getNormalized_hit_count(int i) {
        return Double.parseDouble(gene_hits.get(i)[2]);
    }

    /**
     *
     * @param i
     * @return
     */
    @Override
    public double getNormalized_pixel_intensity(int i) {
        return Double.parseDouble(gene_hits.get(i)[3]);
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
