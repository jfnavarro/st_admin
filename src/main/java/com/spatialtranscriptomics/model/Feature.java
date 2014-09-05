/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import org.codehaus.jackson.annotate.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * This bean class maps the Feature data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does validation using Hibernate validator constraints.
 */

//@JsonPropertyOrder({ "x", "y", "hits", "barcode", "gene", "annotation" })
public class Feature implements IFeature {

	String id;
	
	@JsonProperty(value="barcode")
	String barcode;

	//@JsonProperty(value="gene")
	String gene;
	
	@JsonProperty(value="hits")
	int hits;
	
	@JsonProperty(value="x")
	int x;
	
	@JsonProperty(value="y")
	int y;
	
	@JsonProperty(value="annotation")
	String annotation;
	
	public String getId() {
		return id;
	}

	// id is set automatically by MongoDB
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty(value="barcode")
	public String getBarcode() {
		return barcode;
	}

	@JsonProperty(value="barcode")
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@JsonProperty(value="hits")
	public int getHits() {
		return hits;
	}

	@JsonProperty(value="hits")
	public void setHits(int hits) {
		this.hits = hits;
	}

	@JsonProperty(value="x")
	public int getX() {
		return x;
	}

	@JsonProperty(value="x")
	public void setX(int x) {
		this.x = x;
	}
	
	@JsonProperty(value="y")
	public int getY() {
		return y;
	}

	@JsonProperty(value="y")
	public void setY(int y) {
		this.y = y;
	}

	//@JsonProperty(value="gene")
	public String getGene() {
		return this.gene;
	}

	//@JsonProperty(value="gene")
	public void setGene(String gene) {
		this.gene = gene;
	}

	@JsonProperty(value="annotation")
	public String getAnnotation() {
		return this.annotation;
	}

	@JsonProperty(value="annotation")
	public void setAnnotation(String ann) {
		this.annotation = ann;
	}

}
