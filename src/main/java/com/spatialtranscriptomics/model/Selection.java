package com.spatialtranscriptomics.model;

import java.util.LinkedHashMap;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This bean class maps the Selection data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 * 
 * */
public class Selection implements ISelection {

	String id;
	
	@NotBlank(message = "Name must not be blank.")
	String name;
	
	@NotBlank(message = "Dataset must not be blank.")
	String dataset_id;
	
	@NotBlank(message = "Account must not be blank.")
	String account_id;
	
	@NotEmpty(message = "Gene nomenclatures with hit counts must not be empty.")
	LinkedHashMap<String, Integer> gene_hits;

	String type;
	
	String status;
	
	String comment;
	
	String[] obo_foundry_terms;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LinkedHashMap<String, Integer> getGene_hits() {
		return gene_hits;
	}

	public void setGene_hits(LinkedHashMap<String,Integer> gene_hits) {
		this.gene_hits = gene_hits;
	}
	
	public String getDataset_id() {
		return dataset_id;
	}

	public void setDataset_id(String dataset_id) {
		this.dataset_id = dataset_id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String[] getObo_foundry_terms() {
		return obo_foundry_terms;
	}

	public void setObo_foundry_terms(String[] obo_foundry_terms) {
		this.obo_foundry_terms = obo_foundry_terms;
	}
	
}
