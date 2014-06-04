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

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.elasticmapreduce.model.JobFlowDetail;
import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import com.spatialtranscriptomics.form.PipelineExperimentForm;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.PipelineExperiment;
import com.spatialtranscriptomics.model.PipelineStats;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.EMRServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineExperimentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineStatsServiceImpl;
import com.spatialtranscriptomics.serviceImpl.S3ServiceImpl;

/**
 * This class is Spring MVC controller class for the URL "/pipelineexperiment". It implements the methods available at this URL and returns views (.jsp pages) with models.
 */

@Controller
@RequestMapping("/pipelineexperiment")
public class PipelineExperimentController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(PipelineExperimentController.class);

	@Autowired
	PipelineExperimentServiceImpl pipelineexperimentService;
	
	@Autowired
	PipelineStatsServiceImpl pipelinestatsService;

	@Autowired
	EMRServiceImpl emrService;

	@Autowired
	S3ServiceImpl s3Service;
	
	@Autowired
	AccountServiceImpl accountService;

	
	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable String id) {
		PipelineExperiment exp = pipelineexperimentService.find(id);
		ModelAndView success = new ModelAndView("pipelineexperimentshow", "pipelineexperiment", exp);
		//System.out.println("Trtying to get " + exp.getEmr_jobflow_id());
		PipelineStats stats = pipelinestatsService.findForPipelineExperiment(id);
		success.addObject("stats", stats);
		JobFlowDetail jobFlow = emrService.findJobFlow(exp.getEmr_jobflow_id());
		
		success.addObject("jobflow", jobFlow);
		success.addObject("account", accountService.find(exp.getAccount_id()));
		return success;
	}

	
	// list
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView list() {
		ModelAndView success = new ModelAndView("pipelineexperimentlist", "pipelineexperimentList", pipelineexperimentService.list());
		return success;
	}

	// edit
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("pipelineexperimentedit", "pipelineexperiment", pipelineexperimentService.find(id));
	}
	
	// edit submit
	@RequestMapping(value = "/submitedit", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute("pipelineexperiment") @Valid PipelineExperiment pipelineexperiment, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("pipelineexperimentedit", "pipelineexperiment", pipelineexperiment);
			model.addObject("errors", result.getAllErrors());
			return model;
		}
		pipelineexperimentService.update(pipelineexperiment);
		ModelAndView success = new ModelAndView("pipelineexperimentlist", "pipelineexperimentList", pipelineexperimentService.list());
		success.addObject("msg", "PipelineExperiment saved.");
		return success;
	}
	
	// create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", new PipelineExperimentForm());
	}

	
	// create submit
	@RequestMapping(value = "/submitcreate", method = RequestMethod.POST)
	public ModelAndView submitCreate(@ModelAttribute("pipelineexperimentform") @Valid PipelineExperimentForm form, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", form);
			model.addObject("errors", result.getAllErrors());
			return model;
		}

		// create pipelineexperiment
		PipelineExperiment pipelineexperiment = new PipelineExperiment();
		pipelineexperiment.setName(form.getExperimentName());
		pipelineexperiment.setAccount_id(form.getAccountId());
		pipelineexperiment.setEmr_jobflow_id("Not yet received");
		//pipelineexperiment.setCreated(new Date());
		pipelineexperiment = pipelineexperimentService.add(pipelineexperiment);

		// create EMR jobflow
		String emrJobFlowId = emrService.startJobFlow(form, pipelineexperiment.getId());

		// Delete pipelineexperiment and return error if EMR jobflow could not be
		// started
		if (emrJobFlowId == null) {
			ModelAndView awsFail = new ModelAndView("pipelineexperimentcreate",	"pipelineexperimentform", form);
			pipelineexperimentService.delete(pipelineexperiment.getId());
			awsFail.addObject("errors", "Could not start EMR Job. Try again.");
			return awsFail;
		}

		// update pipelineexperiment with Jobflow ID
		pipelineexperiment.setEmr_jobflow_id(emrJobFlowId);
		pipelineexperimentService.update(pipelineexperiment);

		ModelAndView success = new ModelAndView("pipelineexperimentlist", "pipelineexperimentList", pipelineexperimentService.list());
		success.addObject("msg", "Experiment started.");
		return success;
	}

	
	// stop and delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		PipelineExperiment exp = pipelineexperimentService.find(id);
		if (exp.getEmr_jobflow_id() != null) {
			emrService.stopJobFlow(exp.getEmr_jobflow_id());
		}
		s3Service.deleteExperimentData(id);
		pipelineexperimentService.delete(id);
		pipelinestatsService.deleteForExperiment(id);
		ModelAndView success = new ModelAndView("pipelineexperimentlist", "pipelineexperimentList", pipelineexperimentService.list());
		success.addObject("msg", "Experiment deleted.");
		return success;
	}

	
	// download output
	@RequestMapping(value = "/{id}/output", method = RequestMethod.GET, produces = "text/json")
	public @ResponseBody
	HttpEntity<byte[]> getOutput(@RequestParam(value = "format", required = true) String format, @PathVariable String id) {
		try {
			HttpHeaders header = new HttpHeaders();
			byte[] wb;

			if (format.equals("json")) {
				wb = IOUtils.toByteArray(s3Service.getFeaturesAsJson(id));
				header.setContentType(new MediaType("text", "json"));
				header.set("Content-Disposition", "attachment; filename=output_" + id + ".json");
			} else {
				wb = IOUtils.toByteArray(s3Service.getFeaturesAsCSV(id));
				header.setContentType(new MediaType("text", "csv"));
				header.set("Content-Disposition", "attachment; filename=output_" + id + ".csv");
			}
			header.setContentLength(wb.length);
			return new HttpEntity<byte[]>(wb, header);

		} catch (Exception e) {
			GenericExceptionResponse resp = new GenericExceptionResponse();
			resp.setError("Parse error");
			resp.setError_description("Could not parse pipeline experiment output. Does the output exist?");
			throw new GenericException(resp);
		}
//		return null;
	}

	
	// Populate Choice fields here
	@ModelAttribute("numNodesChoices")
	public Map<String, String> populateNumNodesChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		for (int i = 1; i <= 30; i++) {
			choices.put(String.valueOf(i), String.valueOf(i));
		}
		return choices;
	}

	
	@ModelAttribute("nodeTypeChoices")
	public Map<String, String> populateNodeTypeChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		InstanceType[] instanceTypes = com.amazonaws.services.ec2.model.InstanceType.values();
		for (InstanceType t : instanceTypes) {
			choices.put(t.toString(), t.name());
		}
		return choices;
	}

	
	@ModelAttribute("folderChoices")
	public Map<String, String> populateFolderChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<String> l = s3Service.getInputFolders();
		for (String t : l) {
			choices.put(t, t);
		}
		return choices;
	}

	
	@ModelAttribute("idFileChoices")
	public Map<String, String> populateIdFileChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<String> l = s3Service.getIDFiles();
		for (String t : l) {
			choices.put(t, t);
		}
		return choices;
	}

	
	@ModelAttribute("refAnnotationChoices")
	public Map<String, String> populateRefAnnotationChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<String> l = s3Service.getReferenceAnnotation();
		for (String t : l) {
			choices.put(t, t);
		}
		return choices;
	}

	
	@ModelAttribute("refGenomeChoices")
	public Map<String, String> populateRefGenomeChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<String> l = s3Service.getReferenceGenome();
		for (String t : l) {
			choices.put(t, t);
		}
		return choices;
	}

	
	@ModelAttribute("bowtieFileChoices")
	public Map<String, String> populateBowtieFileChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		List<String> l = s3Service.getBowtieFiles();
		for (String t : l) {
			choices.put(t, t);
		}
		return choices;
	}

	
	@ModelAttribute("htseqAnnotationChoices")
	public Map<String, String> populateHtseqAnnotationChoices() {
		Map<String, String> choices = new LinkedHashMap<String, String>();
		choices.put("union", "union"); 
		//choices.put("intersection-nonempty", "intersection-nonempty"); //hardcoded in jsp view, for pre-selection
		choices.put("intersection-strict", "intersection-strict");
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

}
