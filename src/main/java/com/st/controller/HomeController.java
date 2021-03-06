package com.st.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is Spring MVC controller class for the URL "/home". It just
 * returns a view (home.jsp page).
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(HomeController.class);

    /**
     * Returns the home page.
     * @return the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView show() {
        return new ModelAndView("home");
    }

}
