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

import com.spatialtranscriptomics.component.StaticContextAccessor;
import com.spatialtranscriptomics.form.DatasetAddForm;
import com.spatialtranscriptomics.form.DatasetEditForm;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Dataset;
import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.model.ImageAlignment;
import com.spatialtranscriptomics.model.PipelineExperiment;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ChipServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetInfoServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineExperimentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.FeatureServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ImageAlignmentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.S3ServiceImpl;
import com.spatialtranscriptomics.serviceImpl.SelectionServiceImpl;

/**
 * This class is Spring MVC controller class for the URL "/dataset". It implements the methods available at this URL and returns views (.jsp pages) with models .
 */

@Controller
@RequestMapping("/dataset")
public class DatasetController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(DatasetController.class);

	@Autowired
	DatasetServiceImpl datasetService;
	
	@Autowired
	AccountServiceImpl accountService;

	@Autowired
	FeatureServiceImpl featureService;
	
	@Autowired
	SelectionServiceImpl selectionService;

	@Autowired
	DatasetInfoServiceImpl datasetinfoService;
	
	@Autowired
	ChipServiceImpl chipService;
	
	@Autowired
	ImageAlignmentServiceImpl imageAlignmentService;

	@Autowired
	PipelineExperimentServiceImpl experimentService;

	@Autowired
	S3ServiceImpl s3Service;
	
	// list
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView list() {
		return new ModelAndView("datasetlist", "datasetList", datasetService.list());
	}

	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView get(@PathVariable String id) {
		Dataset dataset = datasetService.find(id);
		ModelAndView success = new ModelAndView("datasetshow", "dataset", dataset);
		List<Account> accounts = accountService.findForDataset(id);
		success.addObject("accounts", accounts);
		if (dataset.getImage_alignment_id() != null && !dataset.getImage_alignment_id().equals("")) {
			ImageAlignment imal = imageAlignmentService.find(dataset.getImage_alignment_id());
			success.addObject("imagealignment", imal);
		}
		return success;

	}

	// add
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("datasetadd", "datasetform",
				new DatasetAddForm());
	}

	// add submit
	@RequestMapping(value = "/submitadd", method = RequestMethod.POST)
	public ModelAndView submitAdd(@ModelAttribute("datasetform") @Valid DatasetAddForm datasetAddForm, BindingResult result) {
		// form validation
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		List<Feature> features = null;
		Dataset dsResult = null;
		// validate if either feature file of experiment is selected (exactly one of them).
		if (datasetAddForm.getFeatureFile().isEmpty()
				&& datasetAddForm.getExperimentId().isEmpty()) {
			ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
			model.addObject("featureerror", "Select either a file or an experiment for feature import.");
			return model;
		} else if (!datasetAddForm.getFeatureFile().isEmpty()
				&& !datasetAddForm.getExperimentId().isEmpty()) {
			ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
			model.addObject("featureerror", "Select either a file or an experiment for feature import. You cannot select both.");
			return model;
		}

		// parse, add features and add dataset
		if (!datasetAddForm.getFeatureFile().isEmpty()) {
			features = featureService.parse(datasetAddForm.getFeatureFile());
			dsResult = datasetService.add(datasetAddForm.getDataset());
			featureService.add(features, dsResult.getId());
		}
		// or: parse features from experiment output bucket
		else if (datasetAddForm.getExperimentId() != null) {
			features = s3Service.getFeaturesAsList(datasetAddForm.getExperimentId());
			dsResult = datasetService.add(datasetAddForm.getDataset());
			featureService.add(features, dsResult.getId());
		}

		ModelAndView success = new ModelAndView("datasetlist", "datasetList", datasetService.list());
		success.addObject("msg", "Dataset created.");
		return success;

	}

	
	// edit
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("datasetedit", "datasetform", new DatasetEditForm(datasetService.find(id)));
	}

	
	// edit submit
	@RequestMapping(value = "/submitedit", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute("datasetform") @Valid DatasetEditForm datasetEditForm, BindingResult result) {

		// form validation
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("datasetedit", "datasetform",	datasetEditForm);
			model.addObject("errors", result.getAllErrors());
			return model;
		}

		// validate if only one feature input is selected.
		if (!datasetEditForm.getFeatureFile().isEmpty()
				&& !datasetEditForm.getExperimentId().isEmpty()) {
			ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetEditForm);
			model.addObject("featureerror", "Select either a file or an experiment for feature import. You cannot select both.");
			return model;
		}

		// parse, update features and update dataset
		if (!datasetEditForm.getFeatureFile().isEmpty()) {
			List<Feature> features = featureService.parse(datasetEditForm.getFeatureFile());
			datasetService.update(datasetEditForm.getDataset());
			featureService.update(features, datasetEditForm.getDataset().getId());

		}
		// or: parse features from experiment output bucket
		else if (!datasetEditForm.getExperimentId().isEmpty()) {
			List<Feature> features = s3Service.getFeaturesAsList(datasetEditForm.getExperimentId());
			datasetService.update(datasetEditForm.getDataset());
			featureService.update(features, datasetEditForm.getDataset().getId());
		}
		// or: update dataset without features
		else {
			datasetService.update(datasetEditForm.getDataset());
		}

		ModelAndView success = new ModelAndView("datasetlist", "datasetList", datasetService.list());
		success.addObject("msg", "Dataset saved.");
		return success;

	}

	// delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		featureService.deleteAll(id);
		datasetService.delete(id);
		selectionService.deleteForDataset(id);
		datasetinfoService.deleteForDataset(id);
		ModelAndView success = new ModelAndView("datasetlist", "datasetList", datasetService.list());
		success.addObject("msg", "Dataset deleted.");
		return success;
	}

	// get feature list for dataset
	@RequestMapping(value = "/{id}/features", method = RequestMethod.GET)
	public ModelAndView getFeatures(@PathVariable String id) {
		List<Feature> features = featureService.findForDataset(id);
		ModelAndView success = new ModelAndView("datasetfeaturelist", "featureList", features);
		success.addObject("dataset", datasetService.find(id));
		return success;
	}

	
	@ModelAttribute("imageAlignmentChoices")
	public Map<String, String> populateImageAlignmentChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<ImageAlignment> l = imageAlignmentService.list();
		for (ImageAlignment t : l) {
			choices.put(t.getId(), t.getName());
		}
		return choices;
	}
	
	@ModelAttribute("experimentChoices")
	public Map<String, String> populateExperimentChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<PipelineExperiment> l = experimentService.list();
		for (PipelineExperiment pe : l) {
			choices.put(pe.getId(), pe.getName());
		}
		return choices;
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
	
	
	public static DatasetServiceImpl getStaticDatasetService() {
		return StaticContextAccessor.getBean(DatasetController.class).getDatasetService();
	}
	
	
	public DatasetServiceImpl getDatasetService() {
		return this.datasetService;
	}
	
}
