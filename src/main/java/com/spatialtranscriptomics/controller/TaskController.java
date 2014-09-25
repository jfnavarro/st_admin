/*
 *Copyright © 2014 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.controller;

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

import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.model.Selection;
import com.spatialtranscriptomics.model.Task;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.SelectionServiceImpl;
import com.spatialtranscriptomics.serviceImpl.TaskServiceImpl;
import org.apache.log4j.Logger;

/**
 * This class is Spring MVC controller class for the URL "/task". It implements
 * the methods available at this URL and returns views (.jsp pages) with models
 * .
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    private static final Logger logger = Logger
            .getLogger(TaskController.class);
    
    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    SelectionServiceImpl selectionService;

    /**
     * Returns the show view.
     * @param id the task.
     * @return the view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view of task " + id);
        Task task = taskService.find(id);
        ModelAndView success = new ModelAndView("taskshow", "task", task);
        success.addObject("account", accountService.find(task.getAccount_id()));
        success.addObject("selections", selectionService.findForTask(id));
        return success;
    }

    /**
     * Returns the list view.
     * @return  the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("Entering list view of tasks ");
        ModelAndView success = new ModelAndView("tasklist", "taskList", taskService.list());
        success.addObject("accounts", populateAccountChoices());
        return success;
    }

    /**
     * Returns the add form.
     * @return  the form.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        logger.info("Entering add form of task ");
        return new ModelAndView("taskadd", "task", new Task());
    }

    /**
     * Invoked on add form submit.
     * @param task the task.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAdd(@ModelAttribute("task") @Valid Task task, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("taskadd", "task", task);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        taskService.add(task);
        ModelAndView success = new ModelAndView("tasklist", "taskList", taskService.list());
        success.addObject("msg", "Task created.");
        logger.info("Successfully added task");
        return success;

    }

    /**
     * Returns the edit form.
     * @param id the task.
     * @return the view.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit form of task " + id);
        return new ModelAndView("taskedit", "task", taskService.find(id));
    }

    /**
     * Invoked on submit if edit form.
     * @param task the task.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@ModelAttribute("task") @Valid Task task, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("taskedit", "task", task);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        taskService.update(task);
        ModelAndView success = new ModelAndView("tasklist", "taskList", taskService.list());
        success.addObject("msg", "Task saved.");
        logger.info("Successfully edited task " + task.getId());
        return success;
    }

    /**
     * Deletes a task.
     * @param id the task id.
     * @return the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        taskService.delete(id);
        ModelAndView success = new ModelAndView("tasklist", "taskList", taskService.list());
        success.addObject("msg", "Task deleted.");
        logger.info("Deleted task " + id);
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

    // populate account choice fields for form
    @ModelAttribute("selectionChoices")
    public Map<String, String> populateSelectionChoices() {
        Map<String, String> choices = new LinkedHashMap<String, String>();
        List<Selection> l = selectionService.list();
        for (Selection t : l) {
            choices.put(t.getId(), t.getName());
        }
        return choices;
    }

}
