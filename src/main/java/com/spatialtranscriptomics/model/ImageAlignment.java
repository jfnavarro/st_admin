/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * This bean class maps the ImageAlignment data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 */
public class ImageAlignment implements IImageAlignment {

	String id;

	@NotBlank(message = "Name must not be blank.")
	String name;

	@NotBlank(message = "Chip must not be blank.")
	String chip_id;

	@NotBlank(message = "Figure red must not be blank.")
	String figure_red;

	@NotBlank(message = "Figure blue must not be blank.")
	String figure_blue;

	@NotEmpty(message = "Alignment matrix must not be empty.")
	double[] alignment_matrix;
	
	DateTime created_at;
	
        DateTime last_modified;
        
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
