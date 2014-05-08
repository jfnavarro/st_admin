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

import com.spatialtranscriptomics.component.StaticContextAccessor;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Dataset;
import com.spatialtranscriptomics.model.DatasetInfo;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetInfoServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;

/**
 * This class is Spring MVC controller class for the URL "/datasetinfo". It implements the methods available at this URL and returns views (.jsp pages) with models .
 */

@Controller
@RequestMapping("/datasetinfo")
public class DatasetInfoController {

	@Autowired
	DatasetInfoServiceImpl datasetinfoService;
	
	@Autowired
	AccountServiceImpl accountService;
	
	@Autowired
	DatasetServiceImpl datasetService;

	
	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable String id) {
		DatasetInfo ds = datasetinfoService.find(id);
		ModelAndView success = new ModelAndView("datasetinfoshow", "datasetinfo", ds);
		return success;
	}

		
	// list
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView success = new ModelAndView("datasetinfolist", "datasetinfoList", datasetinfoService.list());
		success.addObject("datasets", populateDatasetChoices());
		success.addObject("accounts", populateAccountChoices());
		return success;
	}

	
	// add
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("datasetinfoadd", "datasetinfo", new DatasetInfo());
	}

	
	// add submit
	@RequestMapping(value = "/submitadd", method = RequestMethod.POST)
	public ModelAndView submitAdd(@ModelAttribute("datasetinfo") @Valid DatasetInfo ds, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("datasetinfoadd", "datasetinfo", ds);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		datasetinfoService.add(ds);
		ModelAndView success = new ModelAndView("datasetinfolist", "datasetinfoList", datasetinfoService.list());
		success.addObject("msg", "DatasetInfo created.");
		return success;

	}

	
	// edit
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("datasetinfoedit", "datasetinfo", datasetinfoService.find(id));
	}

	
	// edit submit
	@RequestMapping(value = "/submitedit", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute("datasetinfo") @Valid DatasetInfo ds, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("datasetinfoedit", "datasetinfo", ds);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		datasetinfoService.update(ds);
		ModelAndView success = new ModelAndView("datasetinfolist", "datasetinfoList", datasetinfoService.list());
		success.addObject("msg", "DatasetInfo saved.");
		return success;
	}

	
	// delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		datasetinfoService.delete(id);
		ModelAndView success = new ModelAndView("datasetinfolist", "datasetinfoList", datasetinfoService.list());
		success.addObject("msg", "DatasetInfo deleted.");
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
	
	public static DatasetInfoServiceImpl getStaticDatasetInfoService() {
		return StaticContextAccessor.getBean(DatasetInfoController.class).getDatasetInfoService();
	}
	
	
	public DatasetInfoServiceImpl getDatasetInfoService() {
		return this.datasetinfoService;
	}
	
}
