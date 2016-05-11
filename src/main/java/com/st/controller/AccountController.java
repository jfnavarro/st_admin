package com.st.controller;

import com.st.component.StaticContextAccessor;
import com.st.model.Account;
import com.st.model.Dataset;
import com.st.serviceImpl.AccountServiceImpl;
import com.st.serviceImpl.DatasetInfoServiceImpl;
import com.st.serviceImpl.DatasetServiceImpl;
import com.st.serviceImpl.SelectionServiceImpl;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/account". It
 * implements the methods available at this URL and returns views (.jsp pages)
 * with models.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger
            .getLogger(AccountController.class);

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    DatasetServiceImpl datasetService;

    @Autowired
    DatasetInfoServiceImpl datasetinfoService;

    @Autowired
    SelectionServiceImpl selectionService;

    /**
     * Returns the show view of a specified account.
     * @param id the account id
     * @return the show view.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable String id) {
        logger.info("Entering show view of account " + id);
        Account acc = accountService.find(id);
        ModelAndView success = new ModelAndView("accountshow", "account", acc);
        List<Dataset> datasets = datasetService.listForAccount(id);
        success.addObject("datasets", datasets);
        return success;
    }

    /**
     * Returns the list view of all accounts.
     * @return the list view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("Entering list view of accounts");
        return new ModelAndView("accountlist", "accountList", accountService.list());
    }

    /**
     * Returns the add form for adding an account.
     * @return the add form.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        logger.info("Entering add view of account.");
        return new ModelAndView("accountadd", "account", new Account());
    }

    /**
     * Invoked on submit of the add form.
     * @param acc the account to add.
     * @param result the binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitadd", method = RequestMethod.POST)
    public ModelAndView submitAdd(@ModelAttribute("account") @Valid Account acc, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("accountadd", "account", acc);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        if (!acc.getPassword().equals(acc.getPasswordRepeat())) {
            ModelAndView model = new ModelAndView("accountadd", "account", acc);
            model.addObject("specerror", "Password repeat mismatch. Please enter again.");
            return model;
        }
        accountService.add(acc);
        ModelAndView success = new ModelAndView("accountlist", "accountList", accountService.list());
        success.addObject("msg", "Account created.");
        logger.info("Added account " + acc.getId());
        return success;

    }

    /**
     * Returns the edit form for editing an existing account.
     * @param id the account id.
     * @return the edit form.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {
        logger.info("Entering edit view of account " + id);
        Account acc = accountService.find(id);
        acc.setPasswordRepeat(acc.getPassword());
        return new ModelAndView("accountedit", "account", acc);
    }

    /**
     * Invoked on submit of the edit form.
     * @param acc account.
     * @param result binding.
     * @return the list view.
     */
    @RequestMapping(value = "/submitedit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@ModelAttribute("account") @Valid Account acc, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("accountedit", "account", acc);
            model.addObject("errors", result.getAllErrors());
            return model;
        }
        if (!acc.getPassword().equals(acc.getPasswordRepeat())) {
            ModelAndView model = new ModelAndView("accountedit", "account", acc);
            model.addObject("errors", "Password repeat mismatch. Please enter again.");
            return model;
        }
        accountService.update(acc);
        ModelAndView success = new ModelAndView("accountlist", "accountList", accountService.list());
        success.addObject("msg", "Account saved.");
        logger.info("Edited account " + acc.getId());
        return success;
    }

    /**
     * Deletes an account.
     * @param id the account id.
     * @return the list view.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable String id) {
        accountService.delete(id);
        ModelAndView success = new ModelAndView("accountlist", "accountList", accountService.list());
        success.addObject("msg", "Account deleted.");
        logger.info("Deleted account " + id);
        return success;
    }

    /**
     * Populates dataset choice fields for views.
     * @return 
     */
    @ModelAttribute("datasetChoices")
    public Map<String, String> populateDatasetChoices() {
        Map<String, String> choices = new LinkedHashMap<>();
        List<Dataset> l = datasetService.list();
        for (Dataset t : l) {
            choices.put(t.getId(), t.getName());
        }
        return choices;
    }

    /**
     * Static access to the account service.
     * @return 
     */
    public static AccountServiceImpl getStaticAccountService() {
        return StaticContextAccessor.getBean(AccountController.class).getAccountService();
    }

    /**
     * Access to the account service.
     * @return 
     */
    public AccountServiceImpl getAccountService() {
        return this.accountService;
    }
}
