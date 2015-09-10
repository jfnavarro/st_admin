/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.controller;

import com.amazonaws.util.json.JSONObject;
import com.spatialtranscriptomics.component.StaticContextAccessor;
import com.spatialtranscriptomics.form.DatasetAddForm;
import com.spatialtranscriptomics.form.DatasetEditForm;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Dataset;
import com.spatialtranscriptomics.model.FeaturesMetadata;
import com.spatialtranscriptomics.model.ImageAlignment;
import com.spatialtranscriptomics.model.PipelineExperiment;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.DatasetServiceImpl;
import com.spatialtranscriptomics.serviceImpl.FeaturesServiceImpl;
import com.spatialtranscriptomics.serviceImpl.ImageAlignmentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.PipelineExperimentServiceImpl;
import com.spatialtranscriptomics.serviceImpl.S3ServiceImpl;
import com.spatialtranscriptomics.util.ByteOperations;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/dataset". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models .
 */

//TODO the file processing in the onEdit and onAdd callbacks should be
//factored out
@Controller
@RequestMapping("/dataset")
public class DatasetController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(DatasetController.class);

    @Autowired
    DatasetServiceImpl datasetService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    FeaturesServiceImpl featuresService;

    @Autowired
    ImageAlignmentServiceImpl imageAlignmentService;

    @Autowired
    PipelineExperimentServiceImpl experimentService;

    @Autowired
    S3ServiceImpl s3Service;

    /**
     * Returns the list view for all the datasets.
     *
     * @return list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ModelAndView list() {
        logger.info("Entering list view of datasets");
        return new ModelAndView("datasetlist", "datasetList", datasetService.list());
    }

    /**
     * Returns the show view for a single dataset.
     *
     * @param id dataset ID.
     * @return the view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView get(@PathVariable String id) {
        
        logger.info("Entering show view of dataset" + id);
        Dataset dataset = datasetService.find(id);
        ModelAndView success = new ModelAndView("datasetshow", "dataset", dataset);
        
        // get granted accounts for dataset
        List<Account> accounts = accountService.findForDataset(id);
        success.addObject("accounts", accounts);
        
        // get image alignment (cannot be null)
        ImageAlignment imal = imageAlignmentService.find(dataset.getImage_alignment_id());
        success.addObject("imagealignment", imal);
        
        // get dataset creator
        Account creator = accountService.find(dataset.getCreated_by_account_id());
        success.addObject("accountcreator", creator == null ?
                "Unknown" : creator.getUsername());
        
        return success;
    }

    /**
     * Returns the add form.
     *
     * @return the form.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        logger.info("Entering add form of dataset");
        return new ModelAndView("datasetadd", "datasetform", new DatasetAddForm());
    }

    /**
     * Invoked on add form submit.
     *
     * @param datasetAddForm add form.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAdd(
            @ModelAttribute("datasetform") @Valid DatasetAddForm datasetAddForm, 
            BindingResult result) {
        
        // form validation
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }

        byte[] features_file = null;
        if (!datasetAddForm.getFeatureFile().isEmpty()) {
            try {
                features_file = ByteOperations.gunzip(datasetAddForm.getFeatureFile().getBytes());
            } catch (IOException ex) {
                logger.error("Failed to convert S3 feature stream to byte array "
                        + "when adding dataset from experiment " + datasetAddForm.getExperimentId(), ex);
                ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
                model.addObject("featureerror", 
                        "Error trying to access features from experiment " 
                                + datasetAddForm.getExperimentId());
                return model;
            }
        } else if (datasetAddForm.getExperimentId() != null) {
            try {
                // TODO: This needs to be made zipped already at an earlier stage
                // when running the pipeline
                features_file = s3Service.getFeaturesAsJson(datasetAddForm.getExperimentId());
                features_file = ByteOperations.gzip(features_file);
            } catch (IOException ex) {
                logger.error("Failed to convert S3 feature stream to byte array "
                        + "when adding dataset from experiment " + datasetAddForm.getExperimentId(), ex);
                ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
                model.addObject("featureerror", 
                        "Error trying to access features from experiment " 
                                + datasetAddForm.getExperimentId());
                return model;
            }
        }

        // is the features file OK?
        if (features_file == null) {
            logger.error("There was an error processing the features file");
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("There was an error processing the features file");
            return model;
        }
        
        Dataset beingCreated = datasetAddForm.getDataset();
        
        //get QA parameters as byte array
        byte[] qa_file = datasetAddForm.getQaFile().getBytes();
        if (qa_file == null) {
            logger.error("There was an error processing the qa stats file, empty or not valid");
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("QA stats file is empty or not valid JSON");
            return model; 
        }
        //parse QA parameters to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> qa_parameters;
        try {
            qa_parameters = objectMapper.readValue(qa_file, HashMap.class);
        } catch (Exception ex) {
            logger.error("There was an error parsing the qa stats file", ex);
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("There was an error parsing the qa stats file");
            return model; 
        }
        
        //update the object
        beingCreated.setQa_parameters(qa_parameters);
        
        // add dataset's creator account
        beingCreated.setCreated_by_account_id(StaticContextAccessor.getCurrentUser().getId());
        //TODO surround try catch
        Dataset dsResult = datasetService.add(beingCreated);

        // add features file, now that we know the ID.
        try { 
            featuresService.addUpdate(dsResult.getId(), features_file);
        } catch(Exception e) {
            logger.info("Removing dataset because there was an error adding the features file");
            datasetService.delete(dsResult.getId());
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("There was an uploading the features file");
            return model; 
        }
        
        // Return list view.
        ModelAndView success = list();
        success.addObject("msg", "Dataset created.");
        logger.info("Successfully added dataset " + dsResult.getId());
        return success;
    }

    /**
     * Returns the edit form.
     *
     * @param id dataset ID.
     * @return the form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit form for dataset " + id);
        return new ModelAndView("datasetedit", "datasetform", 
                new DatasetEditForm(datasetService.find(id)));
    }

    /**
     * Invoked on submit of the edit form. Updates the dataset and possibly the
     * features file.
     *
     * @param datasetEditForm edit form.
     * @param result binding.
     * @return list form.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(
            @ModelAttribute("datasetform") @Valid DatasetEditForm datasetEditForm, 
            BindingResult result) {
        
        // form validation
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetedit", "datasetform", datasetEditForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }

        // Read features, if specified.
        byte[] bytes = null;
        if (!datasetEditForm.getFeatureFile().isEmpty()) {
            CommonsMultipartFile ffile = datasetEditForm.getFeatureFile();
            bytes = ffile.getBytes();
        } else if (!datasetEditForm.getExperimentId().isEmpty()) {
            try {
                // TODO: This needs to be made zipped already at an earlier stage.
                bytes = s3Service.getFeaturesAsJson(datasetEditForm.getExperimentId());
                bytes = ByteOperations.gzip(bytes);
            } catch (IOException ex) {
                logger.error("Failed to convert S3 feature stream to byte array "
                        + "when editing dataset " + datasetEditForm.getDataset().getId(), ex);
                ModelAndView model = new ModelAndView("datasetedit", "datasetform", datasetEditForm);
                model.addObject("featureerror", "Error creating features from selected experiment.");
                return model;
            }
        }

        Dataset beingUpdated = datasetEditForm.getDataset();

        // Add features file to S3 if only if the user changed the file
        if (bytes != null) {
            featuresService.addUpdate(beingUpdated.getId(), bytes);      
        }

        //get QA parameters as byte array, could be empty
        byte[] qa_file = datasetEditForm.getFeatureFile().getBytes();
        if (qa_file == null) {
            //parse QA parameters to JSON if file was given
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> qa_parameters;
            try {
                qa_parameters = objectMapper.readValue(qa_file, HashMap.class);
            } catch (IOException ex) {
                logger.error("There was an error parsing the qa stats file", ex);
                ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetEditForm);
                model.addObject("There was an error parsing the qa stats file");
                return model; 
            }
            //update the object
            beingUpdated.setQa_parameters(qa_parameters);
        }
        
        // Update dataset.
        datasetService.update(beingUpdated);

        // Return list form.
        ModelAndView success = list();
        success.addObject("msg", "Dataset saved.");
        logger.info("Successfully editied dataset " + datasetEditForm.getDataset().getId());
        return success;
    }

    /**
     * Deletes a dataset.
     *
     * @param id dataset ID.
     * @return list form.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        datasetService.delete(id);
        ModelAndView success = list();
        success.addObject("msg", "Dataset deleted.");
        logger.info("Deleted dataset " + id);
        return success;
    }

    /**
     * Returns the zipped features file.
     *
     * @param id dataset ID.
     * @param response HTTP response containing the file.
     */
    @RequestMapping(value = "/features/{id}", method = RequestMethod.GET)
    public void getFeatures(
            @PathVariable String id, 
            HttpServletResponse response) {
        
        try {
            logger.info("About to download features file for dataset " + id);
            Dataset dataset = datasetService.find(id);
            String features_string = featuresService.find(id);
            response.setContentType("application/json");
            response.setHeader("Content-Encoding", "gzip");
            response.setHeader("Content-Disposition","inline; filename=" + dataset.getName() + "_features.json");
            InputStream is = new ByteArrayInputStream(features_string.getBytes()); //UTF-8 can be passed as parameter
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.error("Error getting or parsing features file for dataset " + id + " from API.");
            throw new RuntimeException("IOError writing features file to HTTP response", ex);
        }
    }
    
    /**
     * Returns the zipped qa stats file.
     *
     * @param id dataset ID.
     * @param response HTTP response containing the file.
     */
    @RequestMapping(value = "/qafile/{id}", method = RequestMethod.GET)
    public void getQAstats(
            @PathVariable String id, 
            HttpServletResponse response) {
        
        try {
            logger.info("About to download QA stats file for dataset " + id);
            Dataset dataset = datasetService.find(id);
            Map<String,String> qa_parameters = dataset.getQa_parameters();
            String qa_parameters_string = new JSONObject(qa_parameters).toString();
            response.setContentType("application/json");
            response.setHeader("Content-Disposition","inline; filename=" + dataset.getName() + "_qa_parameters.json");
            InputStream is = new ByteArrayInputStream(qa_parameters_string.getBytes()); //UTF-8 can be passed as parameter
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.error("Error getting or parsing qa file for dataset " + id + " from API.");
            throw new RuntimeException("IOError writing qa file to HTTP response", ex);
        }
    }

    /**
     * Helper. Metadata.
     * Used to get information to show in the Download feature file option
     * @return metadata.
     */
    @ModelAttribute("featuresMetadata")
    public Map<String, FeaturesMetadata> populateFeaturesMetadata() {
        List<FeaturesMetadata> metadatas = featuresService.listMetadata();
        Map<String, FeaturesMetadata> metadatasMap = 
                new LinkedHashMap<String, FeaturesMetadata>(metadatas.size());
        for (FeaturesMetadata metadata : metadatas) {
            metadatasMap.put(metadata.getDatasetId(), metadata);
        }
        
        return metadatasMap;
    }

    /**
     * Helper. Populates image alignments.
     * Used to populate the combo-box of image alignments in the view.
     * TODO: replace combo-box for multi-select list widget
     * @return alignments.
     */
    @ModelAttribute("imageAlignmentChoices")
    public Map<String, String> populateImageAlignmentChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        choices.put(null, "None");
        List<ImageAlignment> imageAlignments = imageAlignmentService.list();
        for (ImageAlignment imageAlignment : imageAlignments) {
            choices.put(imageAlignment.getId(), imageAlignment.getName());
        }
        
        return choices;
    }

    /**
     * Helper. Populates experiments.
     * Used in the view to populate the file selection for importing features
     * from pipeline experiments
     * @return experiments.
     */
    @ModelAttribute("experimentChoices")
    public Map<String, String> populateExperimentChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<PipelineExperiment> experiments = experimentService.list();
        for (PipelineExperiment experiment : experiments) {
            if (experiment.getEmr_state() != null 
                    && experiment.getEmr_state().equals("COMPLETED")) {
                choices.put(experiment.getId(), experiment.getName());
            }
        }
        
        return choices;
    }

    /**
     * Helper populates accounts.
     * Used in the View to populate the granted accounts combo-box
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

    /**
     * Helper.
     *
     * @return static access of dataset service.
     */
    public static DatasetServiceImpl getStaticDatasetService() {
        return StaticContextAccessor.getBean(DatasetController.class).getDatasetService();
    }

    /**
     * Helper.
     *
     * @return access to dataset service.
     */
    public DatasetServiceImpl getDatasetService() {
        return this.datasetService;
    }

}
