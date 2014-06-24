/*
*Copyright © 2012 Spatial Transcriptomics AB
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

import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Dataset;
import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.model.Selection;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.FeatureServiceImpl;
import com.spatialtranscriptomics.serviceImpl.SelectionServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;

/**
 * This class is Spring MVC controller class for the URL "/selection". It implements the methods available at this URL and returns views (.jsp pages) with models .
 */

@Controller
@RequestMapping("/selection")
public class SelectionController {

	@Autowired
	SelectionServiceImpl selectionService;
	
	@Autowired
	AccountServiceImpl accountService;
	
	@Autowired
	DatasetServiceImpl datasetService;

	@Autowired
	FeatureServiceImpl featureService;
	
	
	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable String id) {
		Selection sel = selectionService.find(id);
		ModelAndView success = new ModelAndView("selectionshow", "selection", sel);
		success.addObject("account", accountService.find(sel.getAccount_id()));
		success.addObject("dataset", datasetService.find(sel.getDataset_id()));
		return success;
	}

		
	// list
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
            //System.out.println("Listing selections");
            List<Selection> selections = selectionService.list();
            //System.out.println("Got selections: " + (selections == null ? "null" : selections.size()));
            ModelAndView success = new ModelAndView("selectionlist", "selectionList", selections);
            return success;
	}
	
	
	// get feature list for selection
	@RequestMapping(value = "/{id}/features", method = RequestMethod.GET)
	public ModelAndView getFeatures(@PathVariable String id) {
		List<Feature> features = featureService.findForSelection(id);
		ModelAndView success = new ModelAndView("selectionfeaturelist", "featureList", features);
		success.addObject("selection", selectionService.find(id));
		return success;
	}

	
	// add
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("selectionadd", "selection", new Selection());
	}

	
	// add submit
	@RequestMapping(value = "/submitadd", method = RequestMethod.POST)
	public ModelAndView submitAdd(@ModelAttribute("selection") @Valid Selection sel, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("selectionadd", "selection", sel);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		selectionService.add(sel);
		ModelAndView success = new ModelAndView("selectionlist", "selectionList", selectionService.list());
		success.addObject("msg", "Selection created.");
		return success;

	}

	
	// edit
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("selectionedit", "selection", selectionService.find(id));
	}

	
	// edit submit
	@RequestMapping(value = "/submitedit", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute("selection") @Valid Selection sel, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("selectionedit", "selection", sel);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		selectionService.update(sel);
		ModelAndView success = new ModelAndView("selectionlist", "selectionList", selectionService.list());
		success.addObject("msg", "Selection saved.");
		return success;
	}

	
	// delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		selectionService.delete(id);
		ModelAndView success = new ModelAndView("selectionlist", "selectionList", selectionService.list());
		success.addObject("msg", "Selection deleted.");
		return success;
	}
	
	
	// populate account choice fields for form
	@ModelAttribute("accountChoices")
	public Map<String, String> populateAccountChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<Account> l = accountService.list();
		for (Account t : l) {
			choices.put(t.getId(), t.getUsername());
		}
		return choices;
	}
	
	
	// populate dataset choice fields for form
	@ModelAttribute("datasetChoices")
	public Map<String, String> populateDatasetChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<Dataset> l = datasetService.list();
		for (Dataset t : l) {
			choices.put(t.getId(), t.getName());
		}
		return choices;
	}
	
}
