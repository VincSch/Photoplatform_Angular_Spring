/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.NotFoundException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ResponseError;

/**
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
@ControllerAdvice
public class GlobalControllerExceptionHandling
{

    static Logger log = Logger.getLogger(GlobalControllerExceptionHandling.class.getName());

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
    public void handleBaseException(RuntimeException ex)
    {
        log.error(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleBadRequestException(BadRequestException ex, WebRequest request)
    {
        return handleException(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseError handleNotFoundException(NotFoundException ex, WebRequest request)
    {
        return handleException(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseError handleException(
            HttpStatus status,
            AbstractBaseException ex,
            WebRequest request)
    {
        return new ResponseError(status.value(), ex.getCode(), ex.getMessage(), ex.getErrors());
    }

}
