/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * This bean class maps the Dataset data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 * 
 * */

public class Dataset implements IDataset {

	String id;

	@NotBlank
	String name;

	@NotBlank
	String chipid;

	@NotBlank
	String figure_blue;
	
	@NotBlank
	String figure_red;
	
	int figure_status;
	double[] alignment_matrix;

	@NotNull
	int stat_barcodes;
	
	@NotNull
	int stat_genes;
	
	@NotNull
	int stat_unique_barcodes;
	
	@NotNull
	int stat_unique_genes;
	
	String stat_tissue;
	String stat_specie;
	String stat_comments;
	Date stat_created;
	
	@JsonIgnore
	Double alignment_field1;
	
	@JsonIgnore
	Double alignment_field2;
	
	@JsonIgnore 
	Double alignment_field3;
	
	@JsonIgnore 
	Double alignment_field4;
	
	@JsonIgnore
	Double alignment_field5;
	
	@JsonIgnore 
	Double alignment_field6;
	
	@JsonIgnore
	Double alignment_field7;
	
	@JsonIgnore 
	Double alignment_field8;
	
	@JsonIgnore 
	Double alignment_field9;
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChipid() {
		return chipid;
	}

	public void setChipid(String chipid) {
		this.chipid = chipid;
	}

	public String getFigure_blue() {
		return figure_blue;
	}

	public void setFigure_blue(String figure_blue) {
		this.figure_blue = figure_blue;
	}

	public String getFigure_red() {
		return figure_red;
	}

	public void setFigure_red(String figure_red) {
		this.figure_red = figure_red;
	}

	public int getFigure_status() {
//		return figure_status;
		return 1; // this is a temp workaround. Field is deprecated and should be removed in data model.
	}

	public void setFigure_status(int figure_status) {
//		this.figure_status = figure_status;
		this.figure_status = 1; // this is a temp workaround. Field is deprecated and should be removed in data model.
	}

	public double[] getAlignment_matrix() {
		return alignment_matrix;
	}

	public void setAlignment_matrix(double[] alignment_matrix) {
		this.alignment_matrix = alignment_matrix;
	}

	public int getStat_barcodes() {
		return this.stat_barcodes;
	}

	public void setStat_barcodes(int stat_barcodes) {
		this.stat_barcodes = stat_barcodes;
	}

	public int getStat_genes() {
		return this.stat_genes;
	}

	public void setStat_genes(int stat_genes) {
		this.stat_genes = stat_genes;
	}

	public int getStat_unique_barcodes() {
		return this.stat_unique_barcodes;
	}

	public void setStat_unique_barcodes(int stat_unique_barcodes) {
		this.stat_unique_barcodes = stat_unique_barcodes;
	}

	public int getStat_unique_genes() {
		return this.stat_unique_genes;
	}

	public void setStat_unique_genes(int stat_unique_genes) {
		this.stat_unique_genes = stat_unique_genes;
	}

	public String getStat_tissue() {
		return this.stat_tissue;
	}

	public void setStat_tissue(String stat_tissue) {
		this.stat_tissue = stat_tissue;
	}

	public String getStat_specie() {
		return this.stat_specie;
	}

	public void setStat_specie(String stat_specie) {
		this.stat_specie = stat_specie;
	}

	public Date getStat_created() {
		return this.stat_created;
	}

	public void setStat_created(Date stat_created) {
		this.stat_created = stat_created;
	}

	public String getStat_comments() {
		return this.stat_comments;
	}

	public void setStat_comments(String stat_comments) {
		this.stat_comments = stat_comments;
	}
	

	// individual matrix fields, used by the web form. 
	// These fields are not being serialized to JSON.
	
	public double getAlignment_field1() {

		if(this.alignment_matrix == null){
			return 1.0d;
		}
		return this.alignment_matrix[0];
	}

	public void setAlignment_field1(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[0] = d;
	}

	
	public double getAlignment_field2() {

		if(this.alignment_matrix == null){
			return 0.0d;
		}
		return this.alignment_matrix[1];
	}

	public void setAlignment_field2(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[1] = d;
	}
	
	public double getAlignment_field3() {

		if(this.alignment_matrix == null){
			return 0.0d;
		}
		return this.alignment_matrix[2];
	}

	public void setAlignment_field3(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[2] = d;
	}
	
	public double getAlignment_field4() {

		if(this.alignment_matrix == null){
			return 0.0d;
		}
		return this.alignment_matrix[3];
	}

	public void setAlignment_field4(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[3] = d;
	}
	
	public double getAlignment_field5() {

		if(this.alignment_matrix == null){
			return 1.0d;
		}
		return this.alignment_matrix[4];
	}

	public void setAlignment_field5(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[4] = d;
	}
	
	public double getAlignment_field6() {

		if(this.alignment_matrix == null){
			return 0.0d;
		}
		return this.alignment_matrix[5];
	}

	public void setAlignment_field6(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[5] = d;
	}
	
	public double getAlignment_field7() {

		if(this.alignment_matrix == null){
			return 0.0d;
		}
		return this.alignment_matrix[6];
	}

	public void setAlignment_field7(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[6] = d;
	}
	
	public double getAlignment_field8() {

		if(this.alignment_matrix == null){
			return 0.0d;
		}
		return this.alignment_matrix[7];
	}

	public void setAlignment_field8(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[7] = d;
	}
	
	public double getAlignment_field9() {

		if(this.alignment_matrix == null){
			return 1.0d;
		}
		return this.alignment_matrix[8];
	}

	public void setAlignment_field9(double d) {

		if(this.alignment_matrix == null){
			this.alignment_matrix = new double[9];
		}
		this.alignment_matrix[8] = d;
	}

}
