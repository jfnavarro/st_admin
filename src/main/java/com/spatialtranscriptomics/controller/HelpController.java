/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/help". It just
 * returns a view (help.jsp page).
 */
@Controller
@RequestMapping("/help")
public class HelpController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(HelpController.class);

    /**
     * Returns the help page.
     * @return the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView show() {
        return new ModelAndView("help");
    }

}
