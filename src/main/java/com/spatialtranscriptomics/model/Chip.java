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
 * This bean class maps the Chip data retrieved from the ST API to the
 * application data model. This data model has to be aligned with the ST API
 * data model. Does data validation using Hibernate validator constraints.
 */
public class Chip implements IChip {

    String id;

    int barcodes;

    @NotBlank(message = "Name must not be blank.")
    String name;

    int x1;
    int x1_border;
    int x1_total;
    int x2;
    int x2_border;
    int x2_total;
    int y1;
    int y1_border;
    int y1_total;
    int y2;
    int y2_border;
    int y2_total;

    DateTime created_at;

    DateTime last_modified;

    /**
     * Default Constructor is required by Jackson.
     */
    public Chip() {}
    
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
    public int getBarcodes() {
        return barcodes;
    }

    @Override
    public void setBarcodes(int barcodes) {
        this.barcodes = barcodes;
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
    public int getX1() {
        return x1;
    }

    @Override
    public void setX1(int x1) {
        this.x1 = x1;
    }

    @Override
    public int getX1_border() {
        return x1_border;
    }

    @Override
    public void setX1_border(int x1_border) {
        this.x1_border = x1_border;
    }

    @Override
    public int getX1_total() {
        return x1_total;
    }

    @Override
    public void setX1_total(int x1_total) {
        this.x1_total = x1_total;
    }

    @Override
    public int getX2() {
        return x2;
    }

    @Override
    public void setX2(int x2) {
        this.x2 = x2;
    }

    @Override
    public int getX2_border() {
        return x2_border;
    }

    @Override
    public void setX2_border(int x2_border) {
        this.x2_border = x2_border;
    }

    @Override
    public int getX2_total() {
        return x2_total;
    }

    @Override
    public void setX2_total(int x2_total) {
        this.x2_total = x2_total;
    }

    @Override
    public int getY1() {
        return y1;
    }

    @Override
    public void setY1(int y1) {
        this.y1 = y1;
    }

    @Override
    public int getY1_border() {
        return y1_border;
    }

    @Override
    public void setY1_border(int y1_border) {
        this.y1_border = y1_border;
    }

    @Override
    public int getY1_total() {
        return y1_total;
    }

    @Override
    public void setY1_total(int y1_total) {
        this.y1_total = y1_total;
    }

    @Override
    public int getY2() {
        return y2;
    }

    @Override
    public void setY2(int y2) {
        this.y2 = y2;
    }

    @Override
    public int getY2_border() {
        return y2_border;
    }

    @Override
    public void setY2_border(int y2_border) {
        this.y2_border = y2_border;
    }

    @Override
    public int getY2_total() {
        return y2_total;
    }

    @Override
    public void setY2_total(int y2_total) {
        this.y2_total = y2_total;
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

    @Override
    public void setLast_modified(DateTime last_modified) {
        this.last_modified = last_modified;
    }
}
