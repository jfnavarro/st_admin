/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.ImageAlignment;

public interface ImageAlignmentService {

	public ImageAlignment find(String id);

	public List<ImageAlignment> list();
	
	public List<ImageAlignment> findForChip(String chipId);

	public ImageAlignment create(ImageAlignment imal);

	public void update(ImageAlignment imal);

	public void delete(String id);

}
