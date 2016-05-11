package com.st.controller;

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

import com.st.component.StaticContextAccessor;
import com.st.model.Account;
import com.st.model.Dataset;
import com.st.model.DatasetInfo;
import com.st.serviceImpl.AccountServiceImpl;
import com.st.serviceImpl.DatasetInfoServiceImpl;
import com.st.serviceImpl.DatasetServiceImpl;
import org.apache.log4j.Logger;

/**
 * This class is Spring MVC controller class for the URL "/datasetinfo". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models .
 */
@Controller
@RequestMapping("/datasetinfo")
public class DatasetInfoController {

    private static final Logger logger = Logger
            .getLogger(DatasetInfoController.class);
    
    @Autowired
    DatasetInfoServiceImpl datasetinfoService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    DatasetServiceImpl datasetService;

    /**
     * Returns the show view.
     * @param id the dataset info.
     * @return  the view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view for datasetinfo");
        DatasetInfo ds = datasetinfoService.find(id);
        ModelAndView success = new ModelAndView("datasetinfoshow", "datasetinfo", ds);
        return success;
    }

    /**
     * Returns the list view.
     * @return the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("Entering list view for datasetinfo");
        ModelAndView success = new ModelAndView("datasetinfolist", "datasetinfoList", datasetinfoService.list());
        return success;
    }

    /**
     * Returns the add form for a dataset info.
     * @return the add form.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        logger.info("Entering add view for datasetinfo");
        return new ModelAndView("datasetinfoadd", "datasetinfo", new DatasetInfo());
    }

    /**
     * Invoked on add form submit.
     * @param ds the dataset info.
     * @param result the binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAdd(@ModelAttribute("datasetinfo") 
    @Valid DatasetInfo ds, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetinfoadd", "datasetinfo", ds);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        datasetinfoService.add(ds);
        ModelAndView success = new ModelAndView("datasetinfolist", 
                "datasetinfoList", datasetinfoService.list());
        success.addObject("msg", "DatasetInfo created.");
        logger.info("Successfully added datasetinfo ");
        return success;

    }

    /**
     * Returns the edit form.
     * @param id the dataset info.
     * @return the edit form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit form for datasetinfo " + id);
        return new ModelAndView("datasetinfoedit", "datasetinfo", datasetinfoService.find(id));
    }

    /**
     * Invoked on submit of the edit form.
     * @param ds the dataset info.
     * @param result the binding.
     * @return  the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@ModelAttribute("datasetinfo") 
    @Valid DatasetInfo ds, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("datasetinfoedit", "datasetinfo", ds);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        datasetinfoService.update(ds);
        ModelAndView success = new ModelAndView("datasetinfolist", 
                "datasetinfoList", datasetinfoService.list());
        success.addObject("msg", "DatasetInfo saved.");
        logger.info("Successfully edited datasetinfo");
        return success;
    }

    /**
     * Deletes a datset info.
     * @param id dataset info.
     * @return  the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        datasetinfoService.delete(id);
        ModelAndView success = new ModelAndView("datasetinfolist", 
                "datasetinfoList", datasetinfoService.list());
        success.addObject("msg", "DatasetInfo deleted.");
        logger.info("Deleted datasetinfo");
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
        Map<String, String> choices = new LinkedHashMap<>();
        List<Dataset> l = datasetService.list();
        for (Dataset t : l) {
            choices.put(t.getId(), t.getName());
        }
        return choices;
    }

    // static access for datasetinfo service
    public static DatasetInfoServiceImpl getStaticDatasetInfoService() {
        return StaticContextAccessor.getBean(DatasetInfoController.class).getDatasetInfoService();
    }

    // access for dataset service.
    public DatasetInfoServiceImpl getDatasetInfoService() {
        return this.datasetinfoService;
    }

}
