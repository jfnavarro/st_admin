/*
*Copyright © 2014 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spatialtranscriptomics.model.PipelineExperiment;
import com.spatialtranscriptomics.model.PipelineStats;
import com.spatialtranscriptomics.serviceImpl.PipelineExperimentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineStatsServiceImpl;

/**
 * This class is Spring MVC controller class for the URL "/pipelinestats". It implements the methods available at this URL and returns views (.jsp pages) with models.
 * TODO: Need to define how these buggers are to be created! Best would be in conjunction with the parent PipelineExperiment.
 */

@Controller
@RequestMapping("/pipelinestats")
public class PipelineStatsController {

	@Autowired
	PipelineStatsServiceImpl pipelinestatsService;
	
	@Autowired
	PipelineExperimentServiceImpl pipelineexperimentService;
	
	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable String id) {
		PipelineStats stats = pipelinestatsService.find(id);
		PipelineExperiment exp = pipelineexperimentService.find(stats.getExperiment_id());
		ModelAndView success = new ModelAndView("pipelinestatsshow", "pipelinestats", stats);
		success.addObject("pipelineexperiment", exp);
		return success;
	}

		
	// list
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("pipelinestatslist", "pipelinestatsList", pipelinestatsService.list());
	}

	
	// edit
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("pipelinestatsedit", "pipelinestats", pipelinestatsService.find(id));
	}

	
	// edit submit
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
		return success;
	}

	
	// delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		pipelinestatsService.delete(id);
		ModelAndView success = new ModelAndView("pipelinestatslist", "pipelinestatsList", pipelinestatsService.list());
		success.addObject("msg", "PipelineStats deleted.");
		return success;
	}
	
}
