/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import de.htw.sdf.photoplatform.exception.NotFoundException;

/**
 * 
 */
@Controller
@RequestMapping(Endpoints.API_PREFIX)
public class BaseAPIController
{

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * Handle exception.
     * 
     * @throws NotFoundException
     *             the not found exception
     */
    @ExceptionHandler(RuntimeException.class)
    public void handleException() throws NotFoundException
    {
        throw new NotFoundException("Ressource not found!");
    }

}
