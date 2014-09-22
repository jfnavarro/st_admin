///*
//*Copyright © 2012 Spatial Transcriptomics AB
//*Read LICENSE for more information about licensing terms
//*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
//* 
//*/
//
//package com.spatialtranscriptomics.service;
//
//import java.util.List;
//
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import com.spatialtranscriptomics.model.Feature;
//
///**
// * Interface for the old DB-based feature service.
// */
//public interface FeatureService {
//	
//	public List<Feature> findForDataset(String datasetId);
//	
//	public List<Feature> findForSelection(String selectionId);
//
//	public List<Feature> add(List<Feature> features, String datasetId);
//	
//	public List<Feature> update(List<Feature> features, String datasetId);
//
//	public void deleteAll(String datasetId);
//
//}
