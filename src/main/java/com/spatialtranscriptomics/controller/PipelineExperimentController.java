/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.controller;

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
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
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

/**
 * This class is Spring MVC controller class for the URL "/pipelineexperiment".
 * It implements the methods available at this URL and returns views (.jsp
 * pages) with models.
 *
 * The experiment details (this class) are stored in MongoDB, whereas the
 * experiment output is fetched from Amazon S3. This controller presents forms
 * for starting jobs on Amazon EMR.
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

    /**
     * Returns the show view. Some information may be fetched from Amazon and
     * updated in the object on state changes.
     *
     * @param id the experiment ID.
     * @return the view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view for pipeline experiment " + id);
        PipelineExperiment exp = pipelineexperimentService.find(id);
        ModelAndView success = new ModelAndView("pipelineexperimentshow", "pipelineexperiment", exp);

        PipelineStats stats = pipelinestatsService.findForPipelineExperiment(id);
        success.addObject("stats", stats);
        JobFlowDetail jobFlow = emrService.findJobFlow(exp.getEmr_jobflow_id());

        // Update DB w. jobflow status. Should be turned into an Amazon callback, if available.
        // Note that we want to avoid doing too many pulls to Amazon, as Amazon has limits on operations.
        String oldState = exp.getEmr_state() == null ? "" : exp.getEmr_state();
        if (!oldState.equals("COMPLETED") && !oldState.equals("FAILED") && !oldState.equals("TERMINATED") && jobFlow != null) {
            logger.info("Updating pipeline experiment " + id + " with state informatoin from Amazon.");
            if (jobFlow.getExecutionStatusDetail().getState() != null) {
                exp.setEmr_state(jobFlow.getExecutionStatusDetail().getState());
            }
            if (jobFlow.getExecutionStatusDetail().getCreationDateTime() != null) {
                exp.setEmr_creation_date_time(new DateTime(jobFlow.getExecutionStatusDetail().getCreationDateTime()));
            }
            if (jobFlow.getExecutionStatusDetail().getEndDateTime() != null) {
                exp.setEmr_end_date_time(new DateTime(jobFlow.getExecutionStatusDetail().getEndDateTime()));
            }
            if (jobFlow.getExecutionStatusDetail().getLastStateChangeReason() != null) {
                exp.setEmr_last_state_change_reason(jobFlow.getExecutionStatusDetail().getLastStateChangeReason());
            }
            pipelineexperimentService.update(exp);
        }
        success.addObject("jobflow", jobFlow);
        success.addObject("accountName", exp.getAccount_id() == null ? "" : accountService.find(exp.getAccount_id()).getUsername());
        return success;
    }

    /**
     * Returns the list view.
     *
     * @return the list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView list() {
        logger.info("Entering list view for pipeline experiments");
        ModelAndView success = new ModelAndView("pipelineexperimentlist", "pipelineexperimentList", pipelineexperimentService.list());
        return success;
    }

    /**
     * Returns the edit form.
     *
     * @param id the experiment id.
     * @return the form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit form for pipeline experiments");
        return new ModelAndView("pipelineexperimentedit", "pipelineexperiment", pipelineexperimentService.find(id));
    }

    /**
     * Invoked on submit of the edit form.
     *
     * @param pipelineexperiment the experiment.
     * @param result binding.
     * @return the list view.
     */
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
        logger.info("Successfully edited pipeline experiment " + pipelineexperiment.getId());
        return success;
    }

    /**
     * Returns the create form for starting pipeline experiments.
     *
     * @return the form.
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        logger.info("Entering create view for pipeline experiment");
        return new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", new PipelineExperimentForm());
    }

    /**
     * Invoked on submit form create. This will trigger a new job on Amazon EMR.
     *
     * @param form the form.
     * @param result binding.
     * @return the list view.
     */
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

        pipelineexperiment = pipelineexperimentService.add(pipelineexperiment);

        if (pipelineexperiment == null || pipelineexperiment.getId() == null) {
            ModelAndView addFail = new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", form);
            addFail.addObject("errors", "Could not create pipeline experiment. Try again.");
            return addFail;
        }

        // create EMR jobflow
        String emrJobFlowId = emrService.startJobFlow(form, pipelineexperiment.getId());

        // Delete pipelineexperiment and return error if EMR jobflow could not be
        // started
        if (emrJobFlowId == null) {
            ModelAndView awsFail = new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", form);
            pipelineexperimentService.delete(pipelineexperiment.getId());
            awsFail.addObject("errors", "Could not start EMR Job. Try again.");
            return awsFail;
        }

        // update pipelineexperiment with Jobflow ID
        pipelineexperiment.setEmr_jobflow_id(emrJobFlowId);
        pipelineexperimentService.update(pipelineexperiment);

        // TODO: Change to proper stats.
        PipelineStats stats = new PipelineStats();
        stats.setExperiment_id(pipelineexperiment.getId());
        stats.setInput_files(new String[]{null});
        stats.setOutput_files(new String[]{null});
        stats.setParameters("Unknown");
        stats.setStatus("COMPLETED");
        stats.setNo_of_reads_mapped(-1);
        stats.setNo_of_reads_annotated(-1);
        stats.setNo_of_reads_mapped_with_find_indexes(-1);
        stats.setNo_of_reads_contaminated(-1);
        stats.setNo_of_barcodes_found(-1);
        stats.setNo_of_genes_found(-1);
        stats.setNo_of_transcripts_found(-1);
        stats.setNo_of_reads_found(-1);
        stats.setMapper_tool("Unknown");
        stats.setMapper_genome("Unknown");
        stats.setAnnotation_tool("Unknown");
        stats.setAnnotation_genome("Unknown");
        stats.setQuality_plots_file("Unknown");
        stats.setLog_file("Unknown");
        stats.setDoc_id("Unknown");
        pipelinestatsService.add(stats);

        ModelAndView success = new ModelAndView("pipelineexperimentlist", "pipelineexperimentList", pipelineexperimentService.list());
        success.addObject("msg", "Experiment started.");
        logger.info("Succesfully created new pipeline experiment " + pipelineexperiment.getId());
        return success;
    }

    /**
     * Deletes an experiment. Any ongoing related EMR job will be cancelled.
     *
     * @param id the experiment.
     * @return the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        PipelineExperiment exp = pipelineexperimentService.find(id);
        if (exp.getEmr_jobflow_id() != null) {
            emrService.stopJobFlow(exp.getEmr_jobflow_id());
            logger.info("Stopped EMR job for pipeline experiment " + id);
        }
        pipelineexperimentService.delete(id);
        ModelAndView success = new ModelAndView("pipelineexperimentlist", "pipelineexperimentList", pipelineexperimentService.list());
        success.addObject("msg", "Experiment deleted.");
        logger.info("Deleted pipeline experiment " + id);
        return success;
    }

    /**
     * Downloads the output of an experiment, either as a JSON file or as a CSV
     * file.
     *
     * @param format the format, either "json" or "csv".
     * @param id the experiment id.
     * @return file as the body of an HTTP entity.
     */
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
                logger.info("Downloading pipeline experiment output for  " + id + " as JSON.");
            } else {
                wb = IOUtils.toByteArray(s3Service.getFeaturesAsCSV(id));
                header.setContentType(new MediaType("text", "csv"));
                header.set("Content-Disposition", "attachment; filename=output_" + id + ".csv");
                logger.info("Downloading pipeline experiment output for  " + id + " as CSV.");
            }
            header.setContentLength(wb.length);
            return new HttpEntity<byte[]>(wb, header);

        } catch (IOException e) {
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse pipeline experiment output. Does the output exist?");
            logger.error("Error downloading pipeline experiment output for  " + id);
            throw new GenericException(resp);
        }
    }

    // Populate number of nodes choice fields
    @ModelAttribute("numNodesChoices")
    public Map<String, String> populateNumNodesChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        for (int i = 1; i <= 30; i++) {
            choices.put(String.valueOf(i), String.valueOf(i));
        }
        return choices;
    }

    // Populate node type choices.
    @ModelAttribute("nodeTypeChoices")
    public Map<String, String> populateNodeTypeChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        InstanceType[] instanceTypes = com.amazonaws.services.ec2.model.InstanceType.values();
        for (InstanceType t : instanceTypes) {
            choices.put(t.toString(), t.name());
        }
        return choices;
    }

    // populate folder choices
    @ModelAttribute("folderChoices")
    public Map<String, String> populateFolderChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> l = s3Service.getInputFolders();
        for (String t : l) {
            choices.put(t, t);
        }
        return choices;
    }

    // populate ID file choices.
    @ModelAttribute("idFileChoices")
    public Map<String, String> populateIdFileChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> l = s3Service.getIDFiles();
        for (String t : l) {
            choices.put(t, t);
        }
        return choices;
    }

    // populate referance annotation choices
    @ModelAttribute("refAnnotationChoices")
    public Map<String, String> populateRefAnnotationChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> l = s3Service.getReferenceAnnotation();
        for (String t : l) {
            choices.put(t, t);
        }
        return choices;
    }

    // populate genome file choices
    @ModelAttribute("refGenomeChoices")
    public Map<String, String> populateRefGenomeChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> l = s3Service.getReferenceGenome();
        for (String t : l) {
            choices.put(t, t);
        }
        return choices;
    }

    // populate bowtie file choices
    @ModelAttribute("bowtieFileChoices")
    public Map<String, String> populateBowtieFileChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> l = s3Service.getBowtieFiles();
        for (String t : l) {
            choices.put(t, t);
        }
        return choices;
    }

    // populate HTSeq annotation file choices.
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
