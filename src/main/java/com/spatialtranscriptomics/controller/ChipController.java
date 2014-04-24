/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.controller;


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

import com.spatialtranscriptomics.form.ChipForm;
import com.spatialtranscriptomics.model.Chip;
import com.spatialtranscriptomics.serviceImpl.ChipServiceImpl;


/**
 * This class is Spring MVC controller class for the URL "/chip". It implements the methods available at this URL and returns views (.jsp pages) with models .
 */

@Controller
@RequestMapping("/chip")
public class ChipController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ChipController.class);

	@Autowired
	ChipServiceImpl chipService;
	
	// list
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView list() {
		return new ModelAndView("chiplist", "chipList", chipService.list());
	}

	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView get(@PathVariable String id) {
		return new ModelAndView("chipshow", "chip", chipService.find(id));
	}

	// import 
	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView add() {
		return new ModelAndView("chipimport", "chipform", new ChipForm());
	}

	// import submit
	@RequestMapping(value = "/submitimport", method = RequestMethod.POST)
	public ModelAndView submitImport(@ModelAttribute("chipform") @Valid ChipForm chipForm, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("chipimport", "chipform",
					chipForm);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		chipService.addFromFile(chipForm.getChipFile(), chipForm.getName());
		ModelAndView success = new ModelAndView("chiplist", "chipList", chipService.list());
		success.addObject("msg", "Chip created.");
		return success;
	}

	// edit 
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("chipedit", "chip", chipService.find(id));
	}

	// edit submit
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
		return success;
	}

	// delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		chipService.delete(id);
		ModelAndView success = new ModelAndView("chiplist", "chipList", chipService.list());
		success.addObject("msg", "Chip deleted.");
		return success;
	}

}
