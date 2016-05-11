/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.controller;

import com.st.component.StaticContextAccessor;
import com.st.form.DatasetAddForm;
import com.st.form.DatasetEditForm;
import com.st.model.Account;
import com.st.model.Chip;
import com.st.model.Dataset;
import com.st.model.Feature;
import com.st.model.FeaturesMetadata;
import com.st.model.ImageAlignment;
import com.st.model.S3Resource;
import com.st.serviceImpl.AccountServiceImpl;
import com.st.serviceImpl.ChipServiceImpl;
import com.st.serviceImpl.DatasetInfoServiceImpl;
import com.st.serviceImpl.DatasetServiceImpl;
import com.st.serviceImpl.FeaturesServiceImpl;
import com.st.serviceImpl.ImageAlignmentServiceImpl;
import com.st.serviceImpl.S3ServiceImpl;
import com.st.serviceImpl.SelectionServiceImpl;
import com.st.util.ByteOperations;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/dataset". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models .
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
    FeaturesServiceImpl featuresService;

    @Autowired
    SelectionServiceImpl selectionService;

    @Autowired
    DatasetInfoServiceImpl datasetinfoService;

    @Autowired
    ChipServiceImpl chipService;

    @Autowired
    ImageAlignmentServiceImpl imageAlignmentService;

    @Autowired
    S3ServiceImpl s3Service;

    /**
     * Returns the list view.
     *
     * @return list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView list() {
        logger.info("Entering list view of datasets");
        Map<String, FeaturesMetadata> metadata = populateFeaturesMetadata();

        ArrayList<Dataset> l = new ArrayList<Dataset>(datasetService.list());
        Iterator<Dataset> i = l.iterator();
        while (i.hasNext()) {
            Dataset d = i.next(); // must be called before you can call i.remove()
            if (!metadata.containsKey(d.getId())) {
                i.remove();
            }
        }
        return new ModelAndView("datasetlist", "datasetList", l);
    }

    /**
     * Returns the show view.
     *
     * @param id dataset ID.
     * @return the view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view of dataset" + id);
        Dataset dataset = datasetService.find(id);
        ModelAndView success = new ModelAndView("datasetshow", "dataset", dataset);
        List<Account> accounts = accountService.findForDataset(id);
        success.addObject("accounts", accounts);
        if (dataset.getImage_alignment_id() != null && !dataset.getImage_alignment_id().equals("")) {
            ImageAlignment imal = imageAlignmentService.find(dataset.getImage_alignment_id());
            success.addObject("imagealignment", imal);
        }
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
        return new ModelAndView("datasetadd", "datasetform",
                new DatasetAddForm());
    }

    /**
     * Invoked on add form submit.
     *
     * @param datasetAddForm add form.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAdd(@ModelAttribute("datasetform") @Valid DatasetAddForm datasetAddForm, BindingResult result) {
        // form validation
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }

        // Validate if either feature file of experiment is selected (exactly one of them).
        if (datasetAddForm.getFeatureFile().isEmpty() && datasetAddForm.getExperimentId().isEmpty()) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("featureerror", "Select either a file or an experiment for feature import.");
            return model;
        } else if (!datasetAddForm.getFeatureFile().isEmpty() && !datasetAddForm.getExperimentId().isEmpty()) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("featureerror", "Select either a file or an experiment for feature import. You cannot select both.");
            return model;
        }
        
        // Validate gzip format.
        if (!datasetAddForm.getFeatureFile().isEmpty() &&
            !datasetAddForm.getFeatureFile().getOriginalFilename().endsWith("gz") &&
            !datasetAddForm.getFeatureFile().getOriginalFilename().endsWith("gzip")) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("featureerror", "The selected file must be gzipped with suffix .gz");
            return model;
        }

        byte[] bytes = null;
        if (!datasetAddForm.getFeatureFile().isEmpty()) {
            CommonsMultipartFile ffile = datasetAddForm.getFeatureFile();
            bytes = ffile.getBytes();
        } else if (datasetAddForm.getExperimentId() != null) {
            try {
                // TODO: This needs to be made zipped already at an earlier stage.
                bytes = s3Service.getFeaturesAsJson(datasetAddForm.getExperimentId());
                bytes = ByteOperations.gzip(bytes);
            } catch (IOException ex) {
                logger.error("Failed to convert S3 feature stream to byte array when adding dataset.");
                ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
                model.addObject("featureerror", "Error trying to access features from chosen experiment.");
                return model;
            }
        }

        Dataset beingCreated = datasetAddForm.getDataset();

        // Compute quartiles.
        computeStats(bytes, true, beingCreated);

        // add dataset
        beingCreated.setCreated_by_account_id(StaticContextAccessor.getCurrentUser().getId());
        Dataset dsResult = datasetService.add(beingCreated);

        // update features file, now that we know the ID.
        S3Resource s3res = new S3Resource("application/json", "gzip", dsResult.getId(), bytes);
        featuresService.addUpdate(dsResult.getId(), s3res);

        // Return list view.
        ModelAndView success = list();
        success.addObject("msg", "Dataset created.");
        logger.info("Successfully added dataset " + dsResult.getId());
        return success;

    }
    
    /**
     * Updates quartiles.
     * @param ds dataset.
     */
    private void computeStats(byte[] bytes, boolean isGzipped, Dataset ds) {
        double[] overall_hit_quartiles = new double[5];
        double[] gene_pooled_hit_quartiles = new double[5];
        // [overall_feature_count, overall_hit_count, unique_gene_count, unique_barcode_count]
        int[] stats = Feature.parse(bytes, isGzipped, overall_hit_quartiles, gene_pooled_hit_quartiles);
        ds.setOverall_feature_count(stats[0]);
        ds.setOverall_hit_count(stats[1]);
        ds.setUnique_gene_count(stats[2]);
        ds.setUnique_barcode_count(stats[3]);
        ds.setOverall_hit_quartiles(overall_hit_quartiles);;
        ds.setGene_pooled_hit_quartiles(gene_pooled_hit_quartiles);
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
        return new ModelAndView("datasetedit", "datasetform", new DatasetEditForm(datasetService.find(id)));
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
    public ModelAndView submitEdit(@ModelAttribute("datasetform") @Valid DatasetEditForm datasetEditForm, BindingResult result) {
        
        // form validation
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetedit", "datasetform", datasetEditForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }

        // validate if only one feature input is selected.
        if (!datasetEditForm.getFeatureFile().isEmpty() && !datasetEditForm.getExperimentId().isEmpty()) {
            ModelAndView model = new ModelAndView("datasetedit", "datasetform", datasetEditForm);
            model.addObject("featureerror", "Select either a file or an experiment for feature import. You cannot select both.");
            return model;
        }
        
        // Validate gzip format.
        if (!datasetEditForm.getFeatureFile().isEmpty() &&
            !datasetEditForm.getFeatureFile().getOriginalFilename().endsWith("gz") &&
            !datasetEditForm.getFeatureFile().getOriginalFilename().endsWith("gzip")) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetEditForm);
            model.addObject("featureerror", "The selected file must be gzipped with suffix .gz");
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
                logger.error("Failed to convert S3 feature stream to byte array when editing dataset " + datasetEditForm.getDataset().getId());
                ModelAndView model = new ModelAndView("datasetedit", "datasetform", datasetEditForm);
                model.addObject("featureerror", "Error creating features from selected experiment.");
                return model;
            }
        }

        Dataset beingUpdated = datasetEditForm.getDataset();

        // Add file to S3, update quartiles.
        if (bytes != null) {
            // Compute quartiles.
            computeStats(bytes, true, beingUpdated);

            // Update file.
            S3Resource s3res = new S3Resource("application/json", "gzip", beingUpdated.getId(), bytes);
            featuresService.addUpdate(beingUpdated.getId(), s3res);
                
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
    public void getFeatures(@PathVariable String id, HttpServletResponse response) {
        try {
            logger.info("About to download features file for dataset " + id);
            S3Resource fw = featuresService.find(id);
            response.setContentType("application/json");
            response.setHeader("Content-Encoding", "gzip");

            InputStream is = new ByteArrayInputStream(fw.getFile());
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.error("Error getting or parsing features file for dataset " + id + " from API.");
            throw new RuntimeException("IOError writing features file to HTTP response", ex);
        }
    }
    
//    /**
//     * Returns the features parsed into model objects.
//     *
//     * @param id dataset ID.
//     * @return the features parsed.
//     */
//    @RequestMapping(value = "/featureslist/{id}", method = RequestMethod.GET)
//    public @ResponseBody
//    Feature[] getFeaturesList(@PathVariable String id) {
//        logger.info("About to download and parse features file for dataset " + id);
//        S3Resource fw = featuresService.find(id);
//        List<Feature> features = Feature.parse(fw.getFile(), true);
//        Feature[] feats = new Feature[features.size()];
//        return feats;
//    }
    
//     /**
//     * Returns an image for inspecting the features.
//     *
//     * @param id dataset ID.
//     * @return RGB image with one pixel per feature coordinate.
//     */
//    @RequestMapping(value = "/featuresimage/{id}", method = RequestMethod.GET, produces = "image/bmp")
//    public @ResponseBody
//    BufferedImage getFeaturesImage(@PathVariable String id) {
//        logger.info("About to download and parse features file for dataset " + id + " to create inspection image");
//        Dataset d = datasetService.find(id);
//        if (d == null) return null;
//        //System.out.println("Got dataset.");
//        ImageAlignment imal = imageAlignmentService.find(d.getImage_alignment_id());
//        //System.out.println("Got imal.");
//        Chip chip = null;
//        if (imal != null) {
//            //System.out.println("Got chip.");
//            chip = chipService.find(imal.getChip_id());
//        }
//        S3Resource fw = featuresService.find(id);
//        //System.out.println("Got file.");
//        List<Feature> features = Feature.parse(fw.getFile(), true);
//        //System.out.println("Got " + features.length);
//        BufferedImage img;
//        try {
//            //System.out.println("Creating image.");
//            img = ComputeFeatureImage.computeImage(chip, features);
//            //System.out.println("size: "+ img.getWidth() +  " * " + img.getHeight());
//        } catch (IOException ex) {
//            //ex.printStackTrace();
//            logger.error("Error creating features image for dataset " + id);
//            throw new RuntimeException("Error constructing features image", ex);
//        }
//        return img;
//    }

    /**
     * Helper. Metadata.
     *
     * @return metadata.
     */
    @ModelAttribute("featuresMetadata")
    public Map<String, FeaturesMetadata> populateFeaturesMetadata() {
        List<FeaturesMetadata> fml = featuresService.listMetadata();
        Map<String, FeaturesMetadata> metadata = new LinkedHashMap<String, FeaturesMetadata>(fml.size());
        for (FeaturesMetadata t : fml) {
            metadata.put(t.getDatasetId(), t);
        }
        return metadata;
    }

    /**
     * Helper. Populates image alignments.
     *
     * @return alignments.
     */
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

    /**
     * Helper. Populates chips.
     *
     * @return chips.
     */
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

    /**
     * Helper populates accounts.
     *
     * @return accounts.
     */
    @ModelAttribute("accountChoices")
    public Map<String, String> populateAccountChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<Account> l = accountService.list();
        for (Account t : l) {
            choices.put(t.getId(), t.getUsername());
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