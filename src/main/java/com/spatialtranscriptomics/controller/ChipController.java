/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.controller;

import com.spatialtranscriptomics.form.ChipForm;
import com.spatialtranscriptomics.model.Chip;
import com.spatialtranscriptomics.serviceImpl.ChipServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ImageAlignmentServiceImpl;
import java.io.IOException;
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
 * This class is Spring MVC controller class for the URL "/chip". It implements
 * the methods available at this URL and returns views (.jsp pages) with models
 * .
 */
@Controller
@RequestMapping("/chip")
public class ChipController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ChipController.class);

    @Autowired
    ChipServiceImpl chipService;

    @Autowired
    ImageAlignmentServiceImpl imagealignmentService;

    @Autowired
    DatasetServiceImpl datasetService;

    /**
     * Returns the list view.
     * @return the list.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView list() {
        logger.info("Entering list view of chips");
        return new ModelAndView("chiplist", "chipList", chipService.list());
    }

    /**
     * Returns the show view of a chip.
     * @param id the chip ID.
     * @return the show view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view of chip " + id);
        return new ModelAndView("chipshow", "chip", chipService.find(id));
    }

    /**
     * Returns the chip import form, where a user uploads a file.
     * @return the import form.
     */
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView add() {
        logger.info("Entering import form of chip");
        return new ModelAndView("chipimport", "chipform", new ChipForm());
    }

    /**
     * Invoked on chip import submit.
     * @param chipForm the import form.
     * @param result the binding.
     * @return the list form.
     */
    @RequestMapping(value = "/submitimport", method = RequestMethod.POST)
    public ModelAndView submitImport(@ModelAttribute("chipform") @Valid ChipForm chipForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("chipimport", "chipform", chipForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        try {
            chipService.addFromFile(chipForm.getChipFile(), chipForm.getName());
            ModelAndView success = new ModelAndView("chiplist", "chipList", chipService.list());
            success.addObject("msg", "Chip created.");
            logger.info("Succesfully imported chip " + chipForm.getFileName());
            return success;
        } catch (IOException e) {
            ModelAndView model = new ModelAndView("chipimport", "chipform", chipForm);
            model.addObject("errors", "Invalid chip file");
            logger.error("Failed to import chip " + chipForm.getChipFile());
            return model;
        }
    }

    /**
     * Returns the edit form.
     * @param id the chip id.
     * @return the edit form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit form of chip " + id);
        return new ModelAndView("chipedit", "chip", chipService.find(id));
    }

    /**
     * Invoked on submit of the edit form.
     * @param chip the edited chip.
     * @param result the binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView submitEdit(@ModelAttribute("chip") @Valid Chip chip, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("chipedit", "chip", chip);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        chipService.update(chip);
        ModelAndView success = new ModelAndView("chiplist", "chipList", chipService.list());
        success.addObject("msg", "Chip saved.");
        logger.info("Successfully edited chip " + chip.getId());
        return success;
    }

    /**
     * Deletes a chip.
     * @param id the chip ID.
     * @return the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        chipService.delete(id);
        ModelAndView success = new ModelAndView("chiplist", "chipList", chipService.list());
        success.addObject("msg", "Chip deleted.");
        logger.info("Deleted chip " + id);
        return success;
    }

}
