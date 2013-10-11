/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Dataset;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;

import javax.validation.Valid;

/**
 * This class is Spring MVC controller class for the URL "/account". It implements the methods available at this URL and returns views (.jsp pages) with models .
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	private static final Logger logger = Logger
			.getLogger(AccountController.class);

	@Autowired
	AccountServiceImpl accountService;

	@Autowired
	DatasetServiceImpl datasetService;

	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable String id) {

		Account acc = accountService.find(id);

		ModelAndView success = new ModelAndView("accountshow", "account", acc);

		// this is a bit ugly, maybe implement a
		// datasetService.list(list<String> datasetIds) in serviceImpl and ST
		// API
		List<Dataset> datasets = datasetService.list();
		List<Dataset> grantedDatasets = new ArrayList<Dataset>();
		if (acc.getGrantedDatasets() != null) {
			for (Dataset ds : datasets) {
				if (acc.getGrantedDatasets().contains(ds.getId())) {
					grantedDatasets.add(ds);
				}
			}
		}

		success.addObject("datasets", grantedDatasets);
		return success;
	}

	// list
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {

		return new ModelAndView("accountlist", "accountList",
				accountService.list());
	}

	// add
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {

		return new ModelAndView("accountadd", "account", new Account());
	}

	// add submit
	@RequestMapping(value = "/submitadd", method = RequestMethod.POST)
	public ModelAndView submitAdd(
			@ModelAttribute("account") @Valid Account acc, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("accountadd", "account", acc);
			model.addObject("errors", result.getAllErrors());
			return model;
		}

		accountService.add(acc);
		ModelAndView success = new ModelAndView("accountlist", "accountList",
				accountService.list());
		success.addObject("msg", "Account created.");
		return success;

	}

	// edit
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {

		return new ModelAndView("accountedit", "account",
				accountService.find(id));
	}

	// edit submit
	@RequestMapping(value = "/submitedit", method = RequestMethod.POST)
	public ModelAndView submitEdit(
			@ModelAttribute("account") @Valid Account acc, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("accountedit", "account", acc);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		accountService.update(acc);
		ModelAndView success = new ModelAndView("accountlist", "accountList",
				accountService.list());
		success.addObject("msg", "Account saved.");
		return success;
	}

	// delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		accountService.delete(id);

		ModelAndView success = new ModelAndView("accountlist", "accountList",
				accountService.list());
		success.addObject("msg", "Account deleted.");
		return success;
	}

	// populate Choice fields for form

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
