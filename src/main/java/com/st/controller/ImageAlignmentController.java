package com.st.controller;

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
import com.st.model.ImageAlignment;
import com.st.model.Chip;
import com.st.model.ImageMetadata;
import com.st.serviceImpl.ChipServiceImpl;
import com.st.serviceImpl.DatasetServiceImpl;
import com.st.serviceImpl.ImageAlignmentServiceImpl;
import com.st.serviceImpl.ImageServiceImpl;
import org.apache.log4j.Logger;

/**
 * This class is Spring MVC controller class for the URL "/imagealignment". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models .
 */
@Controller
@RequestMapping("/imagealignment")
public class ImageAlignmentController {

    private static final Logger logger = Logger
            .getLogger(ImageAlignmentController.class);
    
    @Autowired
    ImageAlignmentServiceImpl imagealignmentService;

    @Autowired
    DatasetServiceImpl datasetService;

    @Autowired
    ChipServiceImpl chipService;

    @Autowired
    ImageServiceImpl imageService;

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
     * Returns the list view.
     * @return the list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("Entering list view of image alignments");
        ModelAndView success;
        success = new ModelAndView("imagealignmentlist", 
                "imagealignmentList", imagealignmentService.list());
        return success;
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
    public ModelAndView submitAdd(@ModelAttribute("imagealignment") 
    @Valid ImageAlignment imal, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("imagealignmentadd", 
                    "imagealignment", imal);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        imagealignmentService.create(imal);
        ModelAndView success = new ModelAndView("imagealignmentlist", 
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
            @ModelAttribute("imagealignment") 
            @Valid ImageAlignment imal, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("imagealignmentedit", 
                    "imagealignment", imal);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        imagealignmentService.update(imal);
        ModelAndView success = new ModelAndView("imagealignmentlist", 
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
            imagealignmentService.delete(id);
        }
        ModelAndView success = new ModelAndView("imagealignmentlist", 
                "imagealignmentList", imagealignmentService.list());
        success.addObject("msg", "ImageAlignment deleted.");
        logger.info("Deleted image alignment " + id);
        return success;
    }

    // populate chip choice fields for form
    @ModelAttribute("chipChoices")
    public Map<String, String> populateChipChoices() {
        Map<String, String> choices = new LinkedHashMap<>();
        List<Chip> l = chipService.list();
        for (Chip t : l) {
            choices.put(t.getId(), t.getName());
        }
        return choices;
    }

    // populate image choice fields for form
    @ModelAttribute("imageChoices")
    public Map<String, String> populateImageChoices() {
        Map<String, String> choices = new LinkedHashMap<>();
        List<ImageMetadata> l = imageService.list();
        for (ImageMetadata t : l) {
            choices.put(t.getFilename(), t.getFilename());
        }
        return choices;
    }

}
