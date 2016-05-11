package com.st.model;

/**
 * This interface defines the Feature model.
 */
public interface IFeature {

    public String getGene();

    public void setGene(String gene);

    public int getX();

    public void setX(int x);

    public int getY();

    public void setY(int y);

    public int getHits();

    public void setHits(int hits);

    public String getBarcode();

    public void setBarcode(String barcode);
}
