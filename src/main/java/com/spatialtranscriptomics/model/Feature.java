/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;



/**
 * This bean class maps the Feature data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does validation using Hibernate validator constraints.
 */

public class Feature implements IFeature {

	String id;
	
	String barcode;

	String gene_nomenclature;
	
	int hits;
	
	int x;
	
	int y;
	
	String annotation;
	
	public String getId() {
		return id;
	}

	// id is set automatically by MongoDB
	public void setId(String id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getGene_nomenclature() {
		return this.gene_nomenclature;
	}

	public void setGene_nomenclature(String gene) {
		this.gene_nomenclature = gene;
	}

	public String getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(String ann) {
		this.annotation = ann;
	}

}
