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
import com.spatialtranscriptomics.model.Task;
import com.spatialtranscriptomics.serviceImpl.AccountServiceImpl;
import com.spatialtranscriptomics.serviceImpl.SelectionServiceImpl;
import com.spatialtranscriptomics.serviceImpl.TaskServiceImpl;

/**
 * This class is Spring MVC controller class for the URL "/task". It implements the methods available at this URL and returns views (.jsp pages) with models .
 */

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskServiceImpl taskService;
	
	@Autowired
	AccountServiceImpl accountService;
	
	@Autowired
	SelectionServiceImpl selectionService;
	
	// get
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable String id) {
		Task task = taskService.find(id);
		ModelAndView success = new ModelAndView("taskshow", "task", task);
		success.addObject("account", accountService.find(task.getAccount_id()));
		success.addObject("selections",selectionService.findForTask(id));
		return success;
	}

		
	// list
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView success = new ModelAndView("tasklist", "taskList", taskService.list());
		success.addObject("accounts", populateAccountChoices());
		return success;
	}

	
	// add
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("taskadd", "task", new Task());
	}

	
	// add submit
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
		return success;

	}

	
	// edit
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("taskedit", "task", taskService.find(id));
	}

	
	// edit submit
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
		return success;
	}

	
	// delete
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id) {
		taskService.delete(id);
		ModelAndView success = new ModelAndView("tasklist", "taskList", taskService.list());
		success.addObject("msg", "Task deleted.");
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
	
	
	
}
