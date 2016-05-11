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

import com.st.model.Account;
import com.st.model.Dataset;
import com.st.model.Selection;
import com.st.serviceImpl.AccountServiceImpl;
import com.st.serviceImpl.SelectionServiceImpl;
import com.st.serviceImpl.DatasetServiceImpl;
import org.apache.log4j.Logger;

/**
 * This class is Spring MVC controller class for the URL "/selection". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models .
 */
@Controller
@RequestMapping("/selection")
public class SelectionController {

    private static final Logger logger = Logger
            .getLogger(SelectionController.class);
    
    @Autowired
    SelectionServiceImpl selectionService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    DatasetServiceImpl datasetService;

    /**
     * Returns the show view.
     * @param id tselection ID.
     * @return the show view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view for selection " + id);
        Selection sel = selectionService.find(id);
        ModelAndView success = new ModelAndView("selectionshow", "selection", sel);
        success.addObject("account", accountService.find(sel.getAccount_id()));
        success.addObject("dataset", datasetService.find(sel.getDataset_id()));
        return success;
    }

    /**
     * Returns the list view.
     * @return the list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("Entering list view for selections");
        List<Selection> selections = selectionService.list();
        ModelAndView success = new ModelAndView("selectionlist", "selectionList", selections);
        return success;
    }


    /**
     * Returns the add form.
     * @return the form.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        logger.info("Entering add form for selection");
        return new ModelAndView("selectionadd", "selection", new Selection());
    }

    /**
     * Invoked on add form submit.
     * @param sel selection.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAdd(@ModelAttribute("selection") 
    @Valid Selection sel, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("selectionadd", "selection", sel);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        selectionService.add(sel);
        ModelAndView success = new ModelAndView("selectionlist", 
                "selectionList", selectionService.list());
        success.addObject("msg", "Selection created.");
        logger.info("Successfully added selection");
        return success;

    }

    /**
     * Returns the edit form.
     * @param id selection.
     * @return form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit form for selection " + id);
        return new ModelAndView("selectionedit", "selection", 
                selectionService.find(id));
    }

    /**
     * Invoked on edit form submit.
     * @param sel selection.
     * @param result binding.
     * @return  the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@ModelAttribute("selection") 
    @Valid Selection sel, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("selectionedit", 
                    "selection", sel);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        selectionService.update(sel);
        ModelAndView success = new ModelAndView("selectionlist", 
                "selectionList", selectionService.list());
        success.addObject("msg", "Selection saved.");
        logger.info("Successfully edited selection " + sel.getId());
        return success;
    }

    /**
     * Deletes a selection.
     * @param id selection ID.
     * @return the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        selectionService.delete(id);
        ModelAndView success = new ModelAndView("selectionlist", 
                "selectionList", selectionService.list());
        success.addObject("msg", "Selection deleted.");
        logger.info("Deleted selection " + id);
        return success;
    }

    // populate account choice fields for form
    @ModelAttribute("accountChoices")
    public Map<String, String> populateAccountChoices() {
        Map<String, String> choices = new LinkedHashMap<>();
        List<Account> l = accountService.list();
        choices.put("", "Unknown");
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
        choices.put("", "Unknown");
        for (Dataset t : l) {
            choices.put(t.getId(), t.getName());
        }
        return choices;
    }

}
