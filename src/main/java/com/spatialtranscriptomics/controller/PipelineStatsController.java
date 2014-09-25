/*
 *Copyright © 2014 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.controller;

import com.spatialtranscriptomics.model.PipelineExperiment;
import com.spatialtranscriptomics.model.PipelineStats;
import com.spatialtranscriptomics.serviceImpl.PipelineExperimentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineStatsServiceImpl;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/pipelinestats". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models.
 *
 * TODO: Need to define how these buggers are to be created! Best would be in
 * conjunction with the parent PipelineExperiment. The stats class need to be
 * merged with pipeline experiment at some point, in which case this controller
 * will vanish.
 */
@Controller
@RequestMapping("/pipelinestats")
public class PipelineStatsController {

    private static final Logger logger = Logger
            .getLogger(PipelineStatsController.class);

    @Autowired
    PipelineStatsServiceImpl pipelinestatsService;

    @Autowired
    PipelineExperimentServiceImpl pipelineexperimentService;

    /**
     * Returns the show view.
     *
     * @param id the stats ID.
     * @return the view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view for pipeline stats " + id);
        PipelineStats stats = pipelinestatsService.find(id);
        PipelineExperiment exp = pipelineexperimentService.find(stats.getExperiment_id());
        ModelAndView success = new ModelAndView("pipelinestatsshow", "pipelinestats", stats);
        success.addObject("pipelineexperiment", exp);
        return success;
    }

    /**
     * Returns the list view.
     * @return the list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("Entering list view for pipeline stats");
        return new ModelAndView("pipelinestatslist", "pipelinestatsList", pipelinestatsService.list());
    }

    /**
     * Returns the edit form.
     * @param id stats id.
     * @return the form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit form for pipeline stats " + id);
        return new ModelAndView("pipelinestatsedit", "pipelinestats", pipelinestatsService.find(id));
    }

    /**
     * Invoked on edit form submit.
     * @param ds stats
     * @param result binding
     * @return the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@ModelAttribute("pipelinestats") @Valid PipelineStats ds, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("pipelinestatsedit", "pipelinestats", ds);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        pipelinestatsService.update(ds);
        ModelAndView success = new ModelAndView("pipelinestatslist", "pipelinestatsList", pipelinestatsService.list());
        success.addObject("msg", "PipelineStats saved.");
        logger.info("Successfullt edited pipeline stats " + ds.getId());
        return success;
    }

    /**
     * Deletes stats.
     * @param id the stats ID.
     * @return the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        pipelinestatsService.delete(id);
        ModelAndView success = new ModelAndView("pipelinestatslist", "pipelinestatsList", pipelinestatsService.list());
        success.addObject("msg", "PipelineStats deleted.");
        logger.info("Deleted pipeline stats " + id);
        return success;
    }

}
