/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.controller;

import com.spatialtranscriptomics.form.ImageForm;
import com.spatialtranscriptomics.model.ImageMetadata;
import com.spatialtranscriptomics.serviceImpl.ImageServiceImpl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/image". It implements
 * the methods available at this URL and returns views (.jsp pages) with models
 * or image payload, either as a pure JPEG, or as a decompressed BufferedImage.
 * The latter is significantly more memory intense.
 */
//TODO add support to edit the file name
//TODO factor out common code in add/edit submit forms
@Controller
@RequestMapping("/image")
public class ImageController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ImageController.class);

    @Autowired
    ImageServiceImpl imageService;

    /**
     * Returns the list view of image metadata.
     * @return the view
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView listMetadata() {
        logger.info("Entering list view of image metadata ");
        List<ImageMetadata> imageMetadata = imageService.list();
        return new ModelAndView("imagelist", "imagemetadata", imageMetadata);
    }

    /**
     * Returns a compressed JPEG.
     * @param id the image name
     * @return the image.
     */
    @RequestMapping(value = "/compressed/{id:.+}", 
            method = RequestMethod.GET, produces = "image/jpeg")
    public @ResponseBody byte[] getCompressed(@PathVariable String id) {
        
        logger.info("Returning " + id + " as JPEG");
        BufferedImage imgdata = imageService.find(id);
        //conver buffered image to byte array
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imgdata, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException ex) {
            logger.info("Error converting image data to byte array", ex);
            return null;
        }
    }

    /**
     * Returns the add form for adding compressed JPEG images.
     * @return  the form.
     */
    @RequestMapping(value = "/compressed/add", method = RequestMethod.GET)
    public ModelAndView addCompressed() {
        return new ModelAndView("imageadd", "imageform", new ImageForm());
    }

    /**
     * Invoked on submit of the add compressed JPEG form.
     * @param imageForm the form.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/compressed/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAddCompressed(
            @ModelAttribute("imageform") @Valid ImageForm imageForm, 
            BindingResult result) {
        
        // validate form
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("imageadd", "imageform", imageForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }

        // check for image name
        if (checkImageNameExists(imageForm.getFileName())) {
            ModelAndView model = 
                    new ModelAndView("imagelist", "imagemetadata", imageService.list());
            model.addObject("err", 
                    "An image with this name already exists. "
                            + "Choose another name or delete existing image.");
            return model;
        } else {
            try {
                imageService.addFromFile(imageForm.getImageFile());
                ModelAndView model = 
                        new ModelAndView("imagelist", "imagemetadata", imageService.list());
                model.addObject("msg", "Image imported.");
                logger.info("Successfully imported JPEG " + imageForm.getFileName());
                return model;
            } catch (IOException ex) {
                ModelAndView model = 
                        new ModelAndView("imagelist", "imagemetadata", imageService.list());
                model.addObject("err", "Error importing image. Format seems invalid.");
                logger.error("Error importing image. Format seems invalid.", ex);
                return model;
            }
        }
    }

    /**
     * Deletes an image.
     * @param id the filename.
     * @return the list view.
     */
    @RequestMapping(value = "/{id:.+}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable String id) {
        imageService.delete(id);
        logger.info("Deleted image "+ id);

        return "redirect:/image";
    }

    /**
     * Check if image already exists
     * @param imageName the name of the image
     * @return true of the image name already exists in the DB
     */
    final boolean checkImageNameExists(String imageName) {
        List<ImageMetadata> imd = imageService.list();
        List<String> imageNames = new ArrayList<String>();
        for (ImageMetadata im : imd) {
            imageNames.add(im.getFilename());
        }
        
        return imageNames.contains(imageName);
    } 
}
