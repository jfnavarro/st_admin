package com.st.controller;

import com.st.component.StaticContextAccessor;
import com.st.form.DatasetAddForm;
import com.st.form.DatasetEditForm;
import com.st.model.Account;
import com.st.model.AccountId;
import com.st.model.Dataset;
import com.st.serviceImpl.AccountServiceImpl;
import com.st.serviceImpl.DatasetServiceImpl;
import com.st.serviceImpl.FileServiceImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final Logger logger = Logger.getLogger(DatasetController.class);

    @Autowired
    DatasetServiceImpl datasetService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    FileServiceImpl filesService;


    /**
     * Returns the list view.
     *
     * @return list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView list() {
        logger.info("Entering list view of datasets");
        return new ModelAndView("datasetlist", "datasetList", datasetService.list());
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
        if (dataset == null) {
            logger.error("Getting dataset. Got null dataset " + id);
            throw new RuntimeException("Could not find the dataset");
        } 
        ModelAndView success = new ModelAndView("datasetshow", "dataset", dataset);
        List<AccountId> accounts_ids = accountService.findForDataset(id);
        logger.info("List of account ids for dataset " + id + " " + accounts_ids.toString());
        success.addObject("accounts", accounts_ids);
        Account creator = accountService.find(dataset.getCreated_by_account_id());
        success.addObject("accountcreator", creator == null ? "Unknown" : creator.getUsername());
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
    public ModelAndView submitAdd(@ModelAttribute("datasetform") 
    @Valid DatasetAddForm datasetAddForm, BindingResult result) {
        // form validation
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        // Validate if ST Data file is present
        if (datasetAddForm.getDataFile().isEmpty()) {
            ModelAndView model = new ModelAndView("datasetadd", "datasetform", datasetAddForm);
            model.addObject("featureerror", "Select a valid file with the st data to import.");
            return model;
        } 
   
        // get the current object
        Dataset beingCreated = datasetAddForm.getDataset();
        // get the user id
        final String user_id = StaticContextAccessor.getCurrentUser().getId();
        // add created by and the current user to granted accounts
        beingCreated.setCreated_by_account_id(user_id);
        List<String> current_users = beingCreated.getGrantedAccounts();
        if (current_users == null) {
            List<String> new_users = new ArrayList<>();
            new_users.add(user_id);
            beingCreated.setGrantedAccounts(new_users);
        } else if (!current_users.contains(user_id)) {
            current_users.add(user_id);
            beingCreated.setGrantedAccounts(current_users);
        }
        
        // add the dataset
        Dataset dsResult = datasetService.add(beingCreated);

        // parse the data file
        CommonsMultipartFile data_file = datasetAddForm.getDataFile();
        // update features file, now that we know the ID.
        filesService.addUpdate(data_file.getName(), 
                dsResult.getId(), data_file.getBytes());
        
        // parse extra files
        for (CommonsMultipartFile extra_file : datasetAddForm.getExtraFiles()) {
            filesService.addUpdate(extra_file.getName(), 
                    dsResult.getId(), extra_file.getBytes());
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
        Dataset dataset = datasetService.find(id);
        if (dataset == null) {
            logger.error("Editing dataset. Got null dataset " + id);
            throw new RuntimeException("Could not find the dataset");
        } 
        return new ModelAndView("datasetedit", "datasetform", new DatasetEditForm(dataset));
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
    public ModelAndView submitEdit(@ModelAttribute("datasetform") 
    @Valid DatasetEditForm datasetEditForm, BindingResult result) {
        
        // form validation
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetedit", "datasetform", datasetEditForm);
            model.addObject("errors", result.getAllErrors());
            return model;
        }

        Dataset beingUpdated = datasetEditForm.getDataset();
        
        // Read the st data (empty means keeping the current file)
        if (!datasetEditForm.getDataFile().isEmpty()) {
            CommonsMultipartFile data_file = datasetEditForm.getDataFile();
            filesService.addUpdate(data_file.getName(), 
                    beingUpdated.getId(), data_file.getBytes());
        }
        // parse extra files (empty means keeping the current file)
        for (CommonsMultipartFile extra_file : datasetEditForm.getExtraFiles()) {
            filesService.addUpdate(extra_file.getName(), 
                    beingUpdated.getId(), extra_file.getBytes());
        }

        // Just in case enforce to always be granted to the creator
        final String user_id = StaticContextAccessor.getCurrentUser().getId();
        List<String> granted_accounts = beingUpdated.getGrantedAccounts();
        if (granted_accounts != null && !granted_accounts.contains(user_id)) {
            granted_accounts.add(user_id);
            beingUpdated.setGrantedAccounts(granted_accounts);
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
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ModelAndView delete(@PathVariable String id) {
        datasetService.delete(id);
        ModelAndView success = list();
        success.addObject("msg", "Dataset deleted.");
        logger.info("Deleted dataset " + id);
        return success;
    }

    /**
     * Returns a file.
     *
     * @param id dataset ID.
     * @param filename the name of the file
     * @param response HTTP response containing the file.
     */
    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable String id,
            @RequestParam(value = "filename", required = true) String filename,
            HttpServletResponse response) {
        try {
            logger.info("About to download file for dataset " + id);
            byte[] fw = filesService.find(filename, id);
            response.setContentType("text/plain");
            response.setHeader("Content-Encoding", "gzip");
            InputStream is = new ByteArrayInputStream(fw);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.error("Error getting or parsing file for dataset " + id, ex);
            throw new RuntimeException("IOError writing file to HTTP response", ex);
        }
    }

    /**
     * Deletes a file.
     *
     * @param id dataset ID.
     * @param filename the name of the file
     * @param response HTTP response containing the file.
     */
    @RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
    public void removeFile(
            @PathVariable String id,
            @RequestParam(value = "filename", required = true) String filename,
            HttpServletResponse response) {
        filesService.remove(filename, id);
        //TODO return the list of files?
    }
    
    /**
     * Helper populates accounts.
     *
     * @return accounts.
     */
    @ModelAttribute("accountChoices")
    public Map<String, String> populateAccountChoices() {
        Map<String, String> choices = new LinkedHashMap<>();
        List<AccountId> accounts = accountService.listIds();
        final String user_id = StaticContextAccessor.getCurrentUser().getId();
        for (AccountId account : accounts) {
            if (!account.getId().equals(user_id)) {
                choices.put(account.getId(), account.getUsername());
            }
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
