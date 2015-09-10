/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.model;

import org.codehaus.jackson.annotate.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * This bean class maps the ImageAlignment data retrieved from the ST API to the
 * application data model. This data model has to be aligned with the ST API
 * data model. Does data validation using Hibernate validator constraints.
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

    //Fields used for intermediately setting the matrix.
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

    /**
     * Default Constructor is required by Jackson.
     */
    public ImageAlignment() {
        this.alignment_matrix = new double[9];
        this.alignment_matrix[0] = 1.0d;
        this.alignment_matrix[1] = 0.0d;
        this.alignment_matrix[2] = 0.0d;
        this.alignment_matrix[3] = 0.0d;
        this.alignment_matrix[4] = 1.0d;
        this.alignment_matrix[5] = 0.0d;
        this.alignment_matrix[6] = 0.0d;
        this.alignment_matrix[7] = 0.0d;
        this.alignment_matrix[8] = 1.0d;
    }
    
    @Override
    public String getId() {
        return id;
    }

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
    public String getChip_id() {
        return chip_id;
    }

    @Override
    public void setChip_id(String id) {
        this.chip_id = id;
    }

    @Override
    public String getFigure_red() {
        return figure_red;
    }

    @Override
    public void setFigure_red(String fig) {
        this.figure_red = fig;
    }

    @Override
    public String getFigure_blue() {
        return figure_blue;
    }

    @Override
    public void setFigure_blue(String fig) {
        this.figure_blue = fig;
    }

    @Override
    public double[] getAlignment_matrix() {
        return alignment_matrix;
    }

    @Override
    public void setAlignment_matrix(double[] arr) {
        this.alignment_matrix = arr;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field1() {
        return this.alignment_matrix[0];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field1(double d) {
        this.alignment_matrix[0] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field2() {
        return this.alignment_matrix[1];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field2(double d) {
        this.alignment_matrix[1] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field3() {
        return this.alignment_matrix[2];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field3(double d) {
        this.alignment_matrix[2] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field4() {
        return this.alignment_matrix[3];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field4(double d) {
        this.alignment_matrix[3] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field5() {
        return this.alignment_matrix[4];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field5(double d) {
        this.alignment_matrix[4] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field6() {
        return this.alignment_matrix[5];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field6(double d) {
        this.alignment_matrix[5] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field7() {
        return this.alignment_matrix[6];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field7(double d) {
        this.alignment_matrix[6] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field8() {
        return this.alignment_matrix[7];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field8(double d) {
        this.alignment_matrix[7] = d;
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public double getAlignment_field9() {
        return this.alignment_matrix[8];
    }

    // Individual matrix field, used by the web form. 
    // This field is not being serialized to JSON.
    public void setAlignment_field9(double d) {
        this.alignment_matrix[8] = d;
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
