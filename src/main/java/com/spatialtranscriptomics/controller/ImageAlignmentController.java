/*
 *Copyright © 2014 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.spatialtranscriptomics.model.ImageAlignment;
import com.spatialtranscriptomics.model.Chip;
import com.spatialtranscriptomics.model.ImageMetadata;
import com.spatialtranscriptomics.serviceImpl.ChipServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ImageAlignmentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ImageServiceImpl;
import com.spatialtranscriptomics.serviceImpl.S3ServiceImpl;
import org.apache.log4j.Logger;

/**
 * This class is Spring MVC controller class for the URL "/imagealignment". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models .
 */
//TODO add a basic manual aligner. Two widgets, one will load the image with the frame
//and the second will generate a representation of the array, user will be able
//to move them and dock then, after that the alignment matrix can be computed and stored
//TODO convert combo-boxes for images into single selection list widget
@Controller
@RequestMapping("/imagealignment")
public class ImageAlignmentController {

    private static final Logger logger = Logger.getLogger(ImageAlignmentController.class);
    
    @Autowired
    ImageAlignmentServiceImpl imagealignmentService;

    @Autowired
    DatasetServiceImpl datasetService;

    @Autowired
    ChipServiceImpl chipService;

    @Autowired
    ImageServiceImpl imageService;

    @Autowired
    S3ServiceImpl s3service;

    /**
     * Returns the show view.
     * @param id the image alignment.
     * @return the view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view of image alignment " + id);
        ImageAlignment imal = imagealignmentService.find(id);
        Chip chip = chipService.find(imal.getChip_id());
        ModelAndView success = new ModelAndView("imagealignmentshow", "imagealignment", imal);
        success.addObject("chip", chip);
        return success;
    }

    /**
     * Returns the list view of all image alignment objects.
     * @return the list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("Entering list view of image alignments");
        return new ModelAndView("imagealignmentlist", 
                "imagealignmentList", imagealignmentService.list());
    }

    /**
     * Returns the add form.
     * @return the add form.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        logger.info("Entering add form of image alignment");
        return new ModelAndView("imagealignmentadd", "imagealignment", new ImageAlignment());
    }

    /**
     * Invoked on submit of add form.
     * @param imal the alignment.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAdd(
            @ModelAttribute("imagealignment") @Valid ImageAlignment imal, 
            BindingResult result) {
        
        //check valid form
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("imagealignmentadd", "imagealignment", imal);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        
        //create the image alignment and save it
        imagealignmentService.create(imal);
        ModelAndView success = 
                new ModelAndView("imagealignmentlist", 
                        "imagealignmentList", imagealignmentService.list());
        success.addObject("msg", "ImageAlignment created.");
        logger.info("Successfully added image alignment");
        return success;

    }

    /**
     * Returns the edit form.
     * @param id the alignment.
     * @return the edit form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit view of image alignment " + id);
        return new ModelAndView("imagealignmentedit", 
                "imagealignment", imagealignmentService.find(id));
    }

    /**
     * Invoked on submit of edit form.
     * @param imal alignment.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(
            @ModelAttribute("imagealignment") @Valid ImageAlignment imal, 
            BindingResult result) {
        
        // validate form
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("imagealignmentedit", "imagealignment", imal);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        
        // update image alignment object
        imagealignmentService.update(imal);
        ModelAndView success = 
                new ModelAndView("imagealignmentlist", 
                        "imagealignmentList", imagealignmentService.list());
        success.addObject("msg", "ImageAlignment saved.");
        logger.info("Succesfully edited image alignment " + imal.getId());
        return success;
    }

    /**
     * Deletes an image alignment.
     * @param id the alignment.
     * @return the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        
        ImageAlignment imal = imagealignmentService.find(id);
        if (imal != null) {
            logger.info("Deleted image alignment " + id);
            imagealignmentService.delete(id);
        } else {
            logger.info("Could not find image alignment object in DB with id " + id);
        }
        
        ModelAndView success = 
                new ModelAndView("imagealignmentlist", 
                        "imagealignmentList", imagealignmentService.list());
        success.addObject("msg", "ImageAlignment deleted.");
        return success;
    }

    // populate chip choice fields for form
    @ModelAttribute("chipChoices")
    public Map<String, String> populateChipChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<Chip> chips = chipService.list();
        for (Chip chip : chips) {
            choices.put(chip.getId(), chip.getName());
        }
        
        return choices;
    }

    // populate image choice fields for form
    @ModelAttribute("imageChoices")
    public Map<String, String> populateImageChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<ImageMetadata> images = imageService.list();
        for (ImageMetadata image : images) {
            choices.put(image.getFilename(), image.getFilename());
        }
        
        return choices;
    }

}
