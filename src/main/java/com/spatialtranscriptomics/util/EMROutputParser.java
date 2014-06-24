/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.apache.log4j.Logger;

import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import com.spatialtranscriptomics.model.Feature;

/**
 * This class parses the output of the Pipeline reducer and returns either Json or CSV. 
 * The methods in this class expect an UTF-8 encoded Inputstream containing a tab separated CSV. 
 * 
 * The CSVs must have the following columns:
 * 
 * X		Y		Gene		Barcode		Hits	
 * (int)	(int)	(String)	(String)	(int)
 * 
 * For example:
 * 
 * 239	257	Serinc1	GATTGTCTGTCCACTTCCGTAAACACC	1
 * 145	355	Serinc1	TTTAAGAGGTTACCCGCAACATTTGGG	34
 * 164	310	Serinc1	GCCTTCCGATAAATCCTGCCGATCCCT	2
 * 289	279	Ints12	CAAAGCTCTAAGGTGCGCCATGTTATT	1
 * 225	219	Ints12	ACTTATCCCGGAGCCAACGGCCTGTCA	1
 * 117	207	Naa30	GCTCTGTTCATACACCGCATAGAGGTT	1
 * 231	275	Naa30	TGATATTTAATGTATTTAGGCCCTCCG	3
 * 
 * Each row must contain values for all columns. Missing/null values are not allowed.
 * 
 */
public class EMROutputParser {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(EMROutputParser.class);

        /**
         * Converts CSV stream to JSON.
         * @param is CSV stream
         * @return JSON stream.
         */
	public InputStream getJSON(InputStream is) {

		try {

			Map<String, Integer> map = getMapFromInputStream(is);

			// serialize into Json and return as Inputstream

			List<EMROutputParser.JsonFeature> features = getFeaturesFromMap(map);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(features);
			InputStream result = new ByteArrayInputStream(
					json.getBytes("UTF-8"));
			return result;

		} catch (IOException e) {
			return null;
		}
	}

        /**
         * Converts JSON stream to CSV.
         * @param is JSON stream.
         * @return CSV stream.
         */
	public InputStream getCSV(InputStream is) {

		try {

			Map<String, Integer> map = getMapFromInputStream(is);

			// Serialize into CSV
			StringBuilder txt = new StringBuilder();
			for (Map.Entry<String, Integer> cursor : map.entrySet()) {

				txt.append(cursor.getKey()).append("\t")
						.append(cursor.getValue()).append("\n");

			}

			InputStream result = new ByteArrayInputStream(txt.toString()
					.getBytes("UTF-8"));

			return result;

		} catch (IOException e) {
			return null;
		}
	}

        /**
         * Converts a CSV stream to a set of features.
         * @param is CSV stream.
         * @return features.
         */
	public List<Feature> getFeatures(InputStream is) {

		Feature[] features;

		try {
			ObjectMapper mapper = new ObjectMapper();
			features = (Feature[]) mapper.readValue(this.getJSON(is),
					Feature[].class);
			return Arrays.asList(features);
		} catch (Exception e) {
			 GenericExceptionResponse resp = new GenericExceptionResponse();
			 resp.setError("Parse error");
			 resp.setError_description("Could not parse experiment output. Does the output exist?");
			 throw new GenericException(resp);
		}

	}

        /**
         * Helper that returns a map of features in a specialized map format.
         * @param is CSV stream.
         * @return map.
         */
	private Map<String, Integer> getMapFromInputStream(InputStream is) {

		try {
			// merge and store into Map
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader ir = new BufferedReader(isr);
			String line;

			Map<String, Integer> hm = new HashMap<String, Integer>();
			while ((line = ir.readLine()) != null) {
				String[] parts = line.split("\t");
				String key = parts[0] + "\t" + parts[1] + "\t" + parts[2]
						+ "\t" + parts[3];
				int value = Integer.parseInt(parts[4]);
				if (hm.containsKey(key)) {
					hm.put(key, hm.get(key) + value);
				} else {
					hm.put(key, value);
				}
			}
			return hm;

		} catch (IOException e) {
			return null;
		}
	}

        /**
         * Helper that converts a specialized map format of features into actual features.
         * @param map the map.
         * @return the features.
         */
	private List<EMROutputParser.JsonFeature> getFeaturesFromMap(
			Map<String, Integer> map) {

		List<EMROutputParser.JsonFeature> features = new ArrayList<EMROutputParser.JsonFeature>();

		for (Map.Entry<String, Integer> cursor : map.entrySet()) {

			String key = cursor.getKey();
			String[] parts = key.split("\t");

			EMROutputParser.JsonFeature feature = new EMROutputParser.JsonFeature();
			feature.setX(Integer.parseInt(parts[0]));
			feature.setY(Integer.parseInt(parts[1]));
			feature.setGene(parts[2]);
			feature.setBarcode(parts[3]);
			feature.setHits(cursor.getValue());

			features.add(feature);
		}

		return features;
	}

        /**
         * Inner class used to serialize Features to Json Objects
         */
	private class JsonFeature {

		String barcode;
		String gene;
		int hits;
		int x;
		int y;

		@SuppressWarnings("unused")
		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}

		@SuppressWarnings("unused")
		public String getGene() {
			return gene;
		}

		public void setGene(String gene) {
			this.gene = gene;
		}

		@SuppressWarnings("unused")
		public int getHits() {
			return hits;
		}

		public void setHits(int hits) {
			this.hits = hits;
		}

		@SuppressWarnings("unused")
		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		@SuppressWarnings("unused")
		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

	}

}
