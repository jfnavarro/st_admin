package com.st.service;

import com.st.model.FileMetadata;
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
    public List<FileMetadata> list();

    /**
     * Returns the image payload as a BufferedImage.
     * 
     * @param id the image name.
     * @return the image.
     */
    public BufferedImage find(String id);

     /**
     * Returns the image payload as a compressed byte array.
     * 
     * @param id the image name.
     * @return the image.
     */
    public byte[] findCompressed(String id);
    
    /**
     * Adds an image payload as a BufferedImage.
     * 
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
