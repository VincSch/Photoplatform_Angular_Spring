/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice;

import de.htw.sdf.photoplatform.common.Messages;
import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.NotFoundException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ResponseError;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.controller.AuthorizationController;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;

/**
 * Base API Controller with exception hadling.
 */
@Controller
@RequestMapping(Endpoints.API_PREFIX)
public class BaseAPIController {

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    @Resource
    private AuthorizationController authorizationController;
    
    @Resource
    protected Messages messages;


    /**
     * Return the logged in user or null
     *
     * @return the user
     */
    public User getAuthenticatedUser(){
        return authorizationController.getAuthenticatedUser();
    }

    /**
     * Handle RuntimeException exception.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
    public void handleBaseException(RuntimeException ex) {
        log.error(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleBadRequestException(BadRequestException ex,
                                                   WebRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseError handleNotFoundException(NotFoundException ex,
                                                 WebRequest request) {
        return handleException(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseError handleException(HttpStatus status,
                                          AbstractBaseException ex, WebRequest request) {
        return new ResponseError(status.value(), ex.getCode(), ex.getMessage(),
                ex.getErrors());
    }
}
