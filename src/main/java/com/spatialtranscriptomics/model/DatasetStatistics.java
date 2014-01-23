package com.spatialtranscriptomics.model;

/**
 * This bean class maps the DatasetStatistics data retrieved from the ST API to the application data model. 
 * This data model has to be aligned with the ST API data model.
 * Does data validation using Hibernate validator constraints.
 */
public class DatasetStatistics implements IDatasetStatistics {

	public DatasetStatistics() {
	}
	
	String datasetId;
	
	int[] hits;
	
	int[] pooledHits;
		
	double[] hitsQuartiles;
	
	double[] pooledHitsQuartiles;

	int hitsSum;
	
	public String getDatasetId() {
		return datasetId;
	}
	
	public double[] getHitsQuartiles() {
		return this.hitsQuartiles;
	}
	
	public double[] getPooledHitsQuartiles() {
		return this.pooledHitsQuartiles;
	}

	public int getHitsSum() {
		return this.hitsSum;
	}

	
	public int[] getHits() {
		return this.hits;
	}

	public int[] getPooledHits() {
		return this.pooledHits;
	}

	public void setDatasetId(String id) {
		this.datasetId = id;
	}
	
	public void setHits(int[] ia) {
		this.hits = ia;
	}
		
	public void setPooledHits(int[] ia) {
		this.pooledHits = ia;
	}

	public void setHitsQuartiles(double[] da) {
		this.hitsQuartiles = da;
	}

	public void setPooledHitsQuartiles(double[] da) {
		this.pooledHitsQuartiles = da;
	}

	public void setHitsSum(int i) {
		this.hitsSum = i;
	}
	
	
	
	public int getHitsMin() {
		return this.hits[0];
	}

	public int getHitsMax() {
		return this.hits[this.hits.length-1];
	}

	public int getPooledHitsMin() {
		return this.pooledHits[0];
	}

	public int getPooledHitsMax() {
		return this.pooledHits[this.pooledHits.length-1];
	}
	
	public String getHitsQuartilesAsString() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("[ ");
		for (double d : this.hitsQuartiles) {
	        sb.append((Math.abs(d-Math.round(d)) < 1e-6 ? ((int) Math.round(d)) : d) + ",  ");
	    }
		return new String(sb.deleteCharAt(sb.length() - 3).append("]"));
	}
	
	public String getPooledHitsQuartilesAsString() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("[ ");
		for (double d : this.pooledHitsQuartiles) {
			sb.append((Math.abs(d-Math.round(d)) < 1e-6 ? ((int) Math.round(d)) : d) + ",  ");
	    }
		return new String(sb.deleteCharAt(sb.length() - 3).append("]"));
	}
	
	public String getHitsInliersRangeAsString() {
		double iqr = hitsQuartiles[3] - hitsQuartiles[1];
		double a = Math.max(hitsQuartiles[0], hitsQuartiles[1] - 1.5 * iqr);
		double b = Math.min(hitsQuartiles[4], hitsQuartiles[3] + 1.5 * iqr);
		return "[ " + a + ", " + b + " ]";
	}
	
	public String getPooledHitsInliersRangeAsString() {
		double iqr = pooledHitsQuartiles[3] - pooledHitsQuartiles[1];
		double a = Math.max(pooledHitsQuartiles[0], pooledHitsQuartiles[1] - 1.5 * iqr);
		double b = Math.min(pooledHitsQuartiles[4], pooledHitsQuartiles[3] + 1.5 * iqr);
		return "[ " + a + ", " + b + " ]";
	}
	
}
