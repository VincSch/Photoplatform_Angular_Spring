/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * View controller.
 *
 * @author Daniil Tomilow
 */
@Controller
public class ViewController {

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    @RequestMapping(value = {
            "/register",
            "/login",
            "/profile",
            "/profile/**",
            "/admin",
            "/admin/**",
            "/wellcomeuser",
            "/becomePhotographer",
            "/cart"}, method = RequestMethod.GET)
    public String index() {
        return "/index.html";
    }

}
