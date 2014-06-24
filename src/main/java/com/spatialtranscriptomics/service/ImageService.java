/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.awt.image.BufferedImage;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.spatialtranscriptomics.model.ImageMetadata;

/**
 * Interface for the image service.
 */
public interface ImageService {

	public List<ImageMetadata> list();

	public BufferedImage find(String id);

	public void addFromFile(CommonsMultipartFile imageFile);

	public void delete(String id);

}
