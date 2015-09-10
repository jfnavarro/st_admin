/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.service;

import com.spatialtranscriptomics.model.ImageMetadata;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Interface for the image service.
 */
public interface ImageService {

    /**
     * Lists metadata for all images.
     * @return the metadata.
     */
    public List<ImageMetadata> list();

    /**
     * Returns the image payload as a BufferedImage.
     * 
     * @param id the image name.
     * @return the image.
     */
    public BufferedImage find(String id);

    /**
     * Adds an image payload as a BufferedImage.
     * 
     * @param imageFile the image file.
     * @throws IOException
     */
    public void addFromFile(CommonsMultipartFile imageFile) throws IOException;

    /**
     * Deletes an image.
     * @param id the image name.
     */
    public void delete(String id);

}
