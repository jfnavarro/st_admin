/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.controller;

import com.amazonaws.services.elasticmapreduce.model.JobFlowDetail;
import com.spatialtranscriptomics.component.StaticContextAccessor;
import com.spatialtranscriptomics.form.DatasetAddForm;
import com.spatialtranscriptomics.form.DatasetEditForm;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Chip;
import com.spatialtranscriptomics.model.Dataset;
import com.spatialtranscriptomics.model.Feature;
import com.spatialtranscriptomics.model.ImageAlignment;
import com.spatialtranscriptomics.model.PipelineExperiment;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ChipServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetInfoServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;
import com.spatialtranscriptomics.serviceImpl.EMRServiceImpl;
import com.spatialtranscriptomics.serviceImpl.FeatureServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ImageAlignmentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineExperimentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.S3ServiceImpl;
import com.spatialtranscriptomics.serviceImpl.SelectionServiceImpl;
import com.spatialtranscriptomics.util.ComputeFeatureImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
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
	EMRServiceImpl emrService;
        
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
                Account creator = accountService.find(dataset.getCreated_by_account_id());
                success.addObject("accountcreator", creator == null ? "Unknown" : creator.getUsername());
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

		// parse
		if (!datasetAddForm.getFeatureFile().isEmpty()) {
			features = featureService.parse(datasetAddForm.getFeatureFile());
		}
		// or: parse features from experiment output bucket
		else if (datasetAddForm.getExperimentId() != null) {
			features = s3Service.getFeaturesAsList(datasetAddForm.getExperimentId());
		}
                
                // Stub for auto image alignment creation.
//                // Attempt to create image alignment dynamically.
//                if (datasetAddForm.getChipId() != null
//				&& datasetAddForm.getDataset().getImage_alignment_id() != null
//                                && !datasetAddForm.getDataset().getImage_alignment_id().equals("")) {
//			ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
//			model.addObject("featureerror", "Select either a chip (for dynamically creating an alignment) or a manual image alignment.");
//			return model;
//		}
//                if (datasetAddForm.getChipId() != null) {
//                    try {
//                        Chip chip = chipService.find(datasetAddForm.getChipId());
//                        ComputeFeatureImage.computeImage(chip, features);
//                    } catch (IOException ex) {
//                        ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
//			datasetAddForm.setChipId(null);
//                        model.addObject("featureerror", "Could not create an alignment dynamically. Try with a manual alignment");
//			return model;
//                    }
//                }
                
                // add features and add dataset
                datasetAddForm.getDataset().setCreated_by_account_id(StaticContextAccessor.getCurrentUser().getId());
                dsResult = datasetService.add(datasetAddForm.getDataset());
                featureService.add(features, dsResult.getId());

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

		// parse
                List<Feature> features = null;
		if (!datasetEditForm.getFeatureFile().isEmpty()) {
			features = featureService.parse(datasetEditForm.getFeatureFile());
		}
		// or: parse features from experiment output bucket
		else if (!datasetEditForm.getExperimentId().isEmpty()) {
			features = s3Service.getFeaturesAsList(datasetEditForm.getExperimentId());
			datasetService.update(datasetEditForm.getDataset());
			featureService.update(features, datasetEditForm.getDataset().getId());
		}
                
                // Stub for auto image alignment creation.
//                // Attempt to create image alignment dynamically.
//                if (datasetEditForm.getChipId() != null
//				&& datasetEditForm.getDataset().getImage_alignment_id() != null
//                                && !datasetEditForm.getDataset().getImage_alignment_id().equals("")) {
//			ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetEditForm);
//			model.addObject("featureerror", "Select either a chip (for dynamically creating an alignment) or a manual image alignment.");
//			return model;
//		}
//                if (datasetEditForm.getChipId() != null) {
//                    try {
//                        Chip chip = chipService.find(datasetEditForm.getChipId());
//                        List<Feature> fs = (features != null ? features : featureService.findForDataset(datasetEditForm.getDataset().getId()));
//                        ComputeFeatureImage.computeImage(chip, fs);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetEditForm);
//			datasetEditForm.setChipId(null);
//                        model.addObject("featureerror", "Could not create an alignment dynamically. Try with a manual alignment");
//			return model;
//                    }
//                }
                
                // update dataset and features.
                datasetService.update(datasetEditForm.getDataset());
                if (features != null) {
                    featureService.update(features, datasetEditForm.getDataset().getId());
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

	// populate image alignment choices
	@ModelAttribute("imageAlignmentChoices")
	public Map<String, String> populateImageAlignmentChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
                choices.put(null, "None");
		List<ImageAlignment> l = imageAlignmentService.list();
		for (ImageAlignment t : l) {
			choices.put(t.getId(), t.getName());
		}
		return choices;
	}
        
        // populate chip choices
	@ModelAttribute("chipChoices")
	public Map<String, String> populateChipChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
                choices.put(null, "None");
		List<Chip> l = chipService.list();
		for (Chip t : l) {
			choices.put(t.getId(), t.getName());
		}
		return choices;
	}
	
        // populate experiment choices.
	@ModelAttribute("experimentChoices")
	public Map<String, String> populateExperimentChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<PipelineExperiment> l = experimentService.list();
		for (PipelineExperiment pe : l) {
                    if (pe.getEmr_state() != null && pe.getEmr_state().equals("COMPLETED")) {    
                        choices.put(pe.getId(), pe.getName());
                    }
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
	
	// static access for dataset service
	public static DatasetServiceImpl getStaticDatasetService() {
		return StaticContextAccessor.getBean(DatasetController.class).getDatasetService();
	}
	
	// access for dataset service.
	public DatasetServiceImpl getDatasetService() {
		return this.datasetService;
	}
	
}
