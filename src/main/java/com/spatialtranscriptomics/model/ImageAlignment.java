package com.spatialtranscriptomics.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
 * This bean class maps the ImageAlignment data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 */
public class ImageAlignment implements IImageAlignment {

	String id;
	
	@NotBlank(message = "Name must not be blank.")
	String name;
	
	String chip_id;
	
	String figure_red;
	
	String figure_blue;
	
	double[] alignment_matrix;
	
	Date last_modified;
	
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

	public String getChip_id() {
		return chip_id;
	}

	public void setChip_id(String id) {
		this.chip_id = id;
	}

	public String getFigure_red() {
		return figure_red;
	}

	public void setFigure_red(String fig) {
		this.figure_red = fig;
	}

	public String getFigure_blue() {
		return figure_blue;
	}

	public void setFigure_blue(String fig) {
		this.figure_blue = fig;
	}

	public double[] getAlignment_matrix() {
		return alignment_matrix;
	}

	public void setAlignment_matrix(double[] arr) {
		this.alignment_matrix = arr;
	}

	public Date getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(Date d) {
		this.last_modified = d;
	}

}
