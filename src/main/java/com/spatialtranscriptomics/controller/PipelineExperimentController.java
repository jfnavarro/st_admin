/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.controller;

import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.exceptions.GenericExceptionResponse;
import com.spatialtranscriptomics.form.PipelineExperimentForm;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Chip;
import com.spatialtranscriptomics.model.PipelineExperiment;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ChipServiceImpl;
import com.spatialtranscriptomics.serviceImpl.EMRServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineExperimentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.S3ServiceImpl;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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

/**
 * This class is Spring MVC controller class for the URL "/pipelineexperiment".
 * It implements the methods available at this URL and returns views (.jsp
 * pages) with models.
 *
 * The experiment details (this class) are stored in MongoDB, whereas the
 * experiment output is fetched from Amazon S3. This controller presents forms
 * for starting jobs on Amazon EMR.
 */

//TODO add option to create dataset from COMPLETED experiment

@Controller
@RequestMapping("/pipelineexperiment")
public class PipelineExperimentController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(PipelineExperimentController.class);

    @Autowired
    PipelineExperimentServiceImpl pipelineexperimentService;

    @Autowired
    EMRServiceImpl emrService;

    @Autowired
    ChipServiceImpl chipService;
        
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
        logger.info("Showing info for pipeline experiment " + id);
        PipelineExperiment exp = pipelineexperimentService.find(id);
        ModelAndView success = 
                new ModelAndView("pipelineexperimentshow", "pipelineexperiment", exp);
        
        success.addObject("accountName",
                exp.getAccount_id() == null ? "" : accountService.find(exp.getAccount_id()).getUsername());
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
        //TODO remove extra debugging logging
        logger.info("Entering list view for pipeline experiments");
        List<PipelineExperiment> pipelineexperiments = pipelineexperimentService.list();
        logger.info("list for pipeline experiments retrieved");
        ModelAndView success = 
                new ModelAndView("pipelineexperimentlist", 
                        "pipelineexperimentList", pipelineexperiments);
        logger.info("returning view for pipeline experiments");
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
        return new ModelAndView("pipelineexperimentedit", 
                "pipelineexperiment", pipelineexperimentService.find(id));
    }

    /**
     * Invoked on submit of the edit form.
     *
     * @param pipelineexperiment the experiment.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(
            @ModelAttribute("pipelineexperiment") @Valid PipelineExperiment pipelineexperiment, 
            BindingResult result) {
        // validate form
        if (result.hasErrors()) {
            ModelAndView model = 
                    new ModelAndView("pipelineexperimentedit", "pipelineexperiment", pipelineexperiment);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        
        // update experiment and return new list
        pipelineexperimentService.update(pipelineexperiment);
        ModelAndView success = 
                new ModelAndView("pipelineexperimentlist", 
                        "pipelineexperimentList", pipelineexperimentService.list());
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
        return new ModelAndView("pipelineexperimentcreate", 
                        "pipelineexperimentform", new PipelineExperimentForm());
    }

    /**
     * Invoked on submit form create. This will trigger a new job on Amazon EMR.
     *
     * @param form the form.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitcreate", method = RequestMethod.POST)
    public ModelAndView submitCreate(
            @ModelAttribute("pipelineexperimentform") @Valid PipelineExperimentForm form, 
            BindingResult result) {
        
        //validate form
        if (result.hasErrors()) {
            ModelAndView model = 
                    new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", form);
            model.addObject("errors", result.getAllErrors());
            return model;
        }

        // create pipelineexperiment
        PipelineExperiment pipelineexperiment = new PipelineExperiment();
        pipelineexperiment.setName(form.getExperimentName());
        pipelineexperiment.setAccount_id(form.getAccountId());
        pipelineexperiment.setEmr_jobflow_id("Not yet received");
        
        // update some parameters
        pipelineexperiment.setMapper_genome(form.getReferenceGenome());
        pipelineexperiment.setAnnotation_genome(form.getReferenceGenome());
        pipelineexperiment.setAnnotation_tool("BOWTIE2"); //TODO this should be a parameter 
        pipelineexperiment.setMapper_tool("HTSEQ"); //TODO this should be a parameter
        pipelineexperiment.setInput_files(form.getFolder());
        
        // save experiment
        pipelineexperiment = pipelineexperimentService.add(pipelineexperiment);
        
        // validate for creation
        if (pipelineexperiment == null || pipelineexperiment.getId() == null) {
            ModelAndView addFail = 
                    new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", form);
            addFail.addObject("errors", "Could not create pipeline experiment. Try again.");
            return addFail;
        }

        // create EMR jobflow
        String emrJobFlowId = emrService.startJobFlow(form, pipelineexperiment.getId());

        // Delete pipelineexperiment and return error if EMR jobflow could not be
        // started
        if (emrJobFlowId == null) {
            ModelAndView awsFail = 
                    new ModelAndView("pipelineexperimentcreate", "pipelineexperimentform", form);
            pipelineexperimentService.delete(pipelineexperiment.getId());
            awsFail.addObject("errors", "Could not start EMR Job. Try again.");
            return awsFail;
        }

        // update pipelineexperiment with Jobflow ID
        pipelineexperiment.setEmr_jobflow_id(emrJobFlowId);
        pipelineexperimentService.update(pipelineexperiment);

        ModelAndView success = 
                new ModelAndView("pipelineexperimentlist", 
                        "pipelineexperimentList", pipelineexperimentService.list());
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
        ModelAndView success = 
                new ModelAndView("pipelineexperimentlist", 
                        "pipelineexperimentList", pipelineexperimentService.list());
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
    public @ResponseBody HttpEntity<byte[]> getOutput(
            @RequestParam(value = "format", required = true) String format, 
            @PathVariable String id) {
        
        try {
            HttpHeaders header = new HttpHeaders();
            byte[] wb;

            if (format.equals("json")) {
                wb = s3Service.getFeaturesAsJson(id);
                header.setContentType(new MediaType("text", "json"));
                //TODO add GZIP compression
                header.set("Content-Disposition", "attachment; filename=output_" + id + ".json");
                logger.info("Downloading pipeline experiment output for  " + id + " as JSON.");
            } else {
                wb = s3Service.getFeaturesAsCSV(id);
                header.setContentType(new MediaType("text", "csv"));
                //TODO add GZIP compression
                header.set("Content-Disposition", "attachment; filename=output_" + id + ".csv");
                logger.info("Downloading pipeline experiment output for  " + id + " as CSV.");
            }
            
            header.setContentLength(wb.length);
            return new HttpEntity<byte[]>(wb, header);

        } catch (IOException e) {
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse pipeline experiment output. Does the output exist?");
            logger.error("Error downloading pipeline experiment output for  " + id, e);
            throw new GenericException(resp);
        }
    }
    
    /**
     * Downloads the qa stats output of an experiment in JSON format
     *
     * @param id the experiment id.
     * @return file as the body of an HTTP entity.
     */
    @RequestMapping(value = "/{id}/qaoutput", method = RequestMethod.GET, produces = "text/json")
    public @ResponseBody HttpEntity<byte[]> getQAoutput(@PathVariable String id) {
        
        try {
            HttpHeaders header = new HttpHeaders();
            byte[] wb;
            wb = s3Service.getExperimentQA(id);
            header.setContentType(new MediaType("text", "json"));
            //TODO add GZIP compression
            header.set("Content-Disposition", "attachment; filename=qastats_" + id + ".json");
            logger.info("Downloading pipeline qa stats output for  " + id + " as JSON.");
            header.setContentLength(wb.length);
            return new HttpEntity<byte[]>(wb, header);
        } catch (IOException e) {
            GenericExceptionResponse resp = new GenericExceptionResponse();
            resp.setError("Parse error");
            resp.setError_description("Could not parse pipeline experiment qt "
                    + "stats output. Does the output exist?");
            logger.error("Error downloading pipeline qt stats output for  " + id, e);
            throw new GenericException(resp);
        }
    }
    
    /**
     * Creates a dataset with the features and qa stats of the experiment ID
     *
     * @param id the experiment id.
     * @return file as the body of an HTTP entity.
     */
    @RequestMapping(value = "/{id}/createdataset", method = RequestMethod.GET, produces = "text/json")
    public @ResponseBody HttpEntity<byte[]> createDataset(@PathVariable String id) {
        //TODO finish this, simple get features file, qt stats file and redirects
        //to datasets view with the form updated
        GenericExceptionResponse resp = new GenericExceptionResponse();
        resp.setError("NOT IMPLEMENTED");
        resp.setError_description("NOT IMPLEMENTED");
        logger.error("NOT IMPLEMENTED");
        throw new GenericException(resp);
    }

    /**
     * Helper populates accounts.
     * Used in the View to populate the granted accounts combo-box
     * TODO duplicated in Datasets
     * @return accounts.
     */
    @ModelAttribute("accountChoices")
    public Map<String, String> populateAccountChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<Account> accounts = accountService.list();
        for (Account account : accounts) {
            choices.put(account.getId(), account.getUsername());
        }
        
        return choices;
    }
    
    // populate input files folder choices
    @ModelAttribute("folderChoices")
    public Map<String, String> populateFolderChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> files = s3Service.getInputFolders();
        for (String file : files) {
            choices.put(file, file);
        }
        
        return choices;
    }

    // populate ID file choices.
    //TODO duplicate in ImageAlignmentController
    @ModelAttribute("idFileChoices")
    public Map<String, String> populateIdFileChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<Chip> chips = chipService.list();
        for (Chip chip : chips) {
            choices.put(chip.getId(), chip.getName());
        }
        
        return choices;
    }

    // populate referance annotation choices
    @ModelAttribute("refAnnotationChoices")
    public Map<String, String> populateRefAnnotationChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> files = s3Service.getReferenceAnnotation();
        for (String file : files) {
            choices.put(file, file);
        }
        
        return choices;
    }

    // populate genome file choices
    @ModelAttribute("refGenomeChoices")
    public Map<String, String> populateRefGenomeChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> files = s3Service.getReferenceGenome();
        for (String file : files) {
            choices.put(file, file);
        }
        
        return choices;
    }

    // populate rna filter file choices
    @ModelAttribute("refContaminantChoices")
    public Map<String, String> populateRefContaminantChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<String> files = s3Service.getContaminantGenome();
        for (String file : files) {
            choices.put(file, file);
        }
        
        return choices;
    }

    // populate HTSeq annotation file choices.
    @ModelAttribute("htseqAnnotationChoices")
    public Map<String, String> populateHtseqAnnotationChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        choices.put("union", "union");
        //hardcoded in jsp view, for pre-selection
        //choices.put("intersection-nonempty", "intersection-nonempty"); 
        choices.put("intersection-strict", "intersection-strict");
        return choices;
    }

}
