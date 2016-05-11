package com.st.service;

import com.st.model.ImageMetadata;
import com.st.model.S3Resource;
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
     * Use findCompressedAsJSON() is recommended instead.
     * @param id the image name.
     * @return the image.
     */
    public BufferedImage find(String id);

    /**
     * Returns the image payload as an S3Resource wrapping the JPEG stream.
     * @param id the image name.
     * @return the image.
     */
    public S3Resource findCompressedAsJSON(String id);

    /**
     * Adds an image payload as a BufferedImage.
     * Use addFromFileCompressedAsJSON() is recommended instead.
     * @param imageFile the image file.
     * @throws IOException
     */
    public void addFromFile(CommonsMultipartFile imageFile) throws IOException;

    /**
     * Deletes an image.
     * @param id the image name.
     */
    public void delete(String id);

    /**
     * Adds an image payload as a JPEG stream.
     * @param imageFile the image file.
     * @throws IOException
     */
    public void addFromFileCompressedAsJSON(CommonsMultipartFile imageFile) throws IOException;

}
