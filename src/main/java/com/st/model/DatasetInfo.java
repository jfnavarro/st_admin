package com.st.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * This bean class maps the DatasetInfo data retrieved from the ST API to the
 * application data model. 
 * 
 * This data model has to be aligned with the ST API
 * data model. Does data validation using Hibernate validator constraints.
 */
public class DatasetInfo implements IDatasetInfo {

    String id;

    @NotBlank(message = "account_id must not be blank.")
    String account_id;

    @NotBlank(message = "dataset_id must not be blank.")
    String dataset_id;

    String comment;

    /**
     * Default Constructor is required by Jackson.
     */
    public DatasetInfo() {
    }

    // Auto-set by Mongo.
    @Override
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAccount_id() {
        return account_id;
    }

    @Override
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    @Override
    public String getDataset_id() {
        return dataset_id;
    }

    @Override
    public void setDataset_id(String dataset_id) {
        this.dataset_id = dataset_id;
    }

    @Override
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     */
    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

}
