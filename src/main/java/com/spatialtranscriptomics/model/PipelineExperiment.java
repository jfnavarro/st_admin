/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.model;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

/**
 * This bean class maps the PipelineExperiment data retrieved from the ST API to
 * the application data model. This data model has to be aligned with the ST API
 * data model. Does data validation using Hibernate validator constraints.
 *
 * Note that many fields pertaining to the Amazon EMR job settings are as of yet not
 * stored. Some fields regarding EMR status, however, are stored here.
 */
public class PipelineExperiment implements IPipelineExperiment {

    String id;

    @NotBlank(message = "Name must not be blank.")
    String name;

    @NotBlank(message = "Account must not be blank.")
    String account_id;

    @NotBlank(message = "EMR job ID must not be blank.")
    String emr_jobflow_id;

    @NotBlank(message = "Chip ID must not be blank.")
    String chip_id;
    
    String emr_state;
    DateTime emr_creation_date_time;
    DateTime emr_end_date_time;
    String emr_last_state_change_reason;

    String input_files;
    String mapper_tool;
    String mapper_genome;
    String annotation_tool;
    String annotation_genome;
    
    DateTime created_at;

    DateTime last_modified;

    /**
     * Default constructor is required by Jackson.
     */
    public PipelineExperiment() {}
    
    // id is set automatically by MongoDB
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getEmr_jobflow_id() {
        return this.emr_jobflow_id;
    }

    @Override
    public void setEmr_jobflow_id(String emrJobflowId) {
        this.emr_jobflow_id = emrJobflowId;
    }

    @Override
    public String getChip_id() {
        return this.chip_id;
    }

    @Override
    public void setChip_id(String chip_id) {
        this.chip_id = chip_id;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAccount_id() {
        return account_id;
    }

    @Override
    public void setAccount_id(String id) {
        this.account_id = id;
    }

    @Override
    public String getEmr_state() {
        return this.emr_state;
    }

    @Override
    public void setEmr_state(String state) {
        this.emr_state = state;
    }

    @Override
    public DateTime getEmr_creation_date_time() {
        return this.emr_creation_date_time;
    }

    @Override
    public void setEmr_creation_date_time(DateTime creationDateTime) {
        this.emr_creation_date_time = creationDateTime;
    }

    @Override
    public DateTime getEmr_end_date_time() {
        return this.emr_end_date_time;
    }

    @Override
    public void setEmr_end_date_time(DateTime endDateTime) {
        this.emr_end_date_time = endDateTime;
    }

    @Override
    public String getEmr_last_state_change_reason() {
        return emr_last_state_change_reason;
    }

    @Override
    public void setEmr_last_state_change_reason(String lastStateChangeReason) {
        this.emr_last_state_change_reason = lastStateChangeReason;
    }

    @Override
    public String getInput_files() {
        return input_files;
    }

    @Override
    public void setInput_files(String input_files) {
        this.input_files = input_files;
    }

    @Override
    public String getMapper_tool() {
        return mapper_tool;
    }

    @Override
    public void setMapper_tool(String mapper_tool) {
        this.mapper_tool = mapper_tool;
    }

    @Override
    public String getMapper_genome() {
        return mapper_genome;
    }

    @Override
    public void setMapper_genome(String mapper_genome) {
        this.mapper_genome = mapper_genome;
    }

    @Override
    public String getAnnotation_tool() {
        return annotation_tool;
    }

    @Override
    public void setAnnotation_tool(String annotation_tool) {
        this.annotation_tool = annotation_tool;
    }

    @Override
    public String getAnnotation_genome() {
        return annotation_genome;
    }

    @Override
    public void setAnnotation_genome(String annotation_genome) {
        this.annotation_genome = annotation_genome;
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
