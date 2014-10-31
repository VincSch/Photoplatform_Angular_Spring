/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.htw.sdf.photoplatform.exception.common.BaseException;

/**
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested Resource was not found!")
public class NotFoundException extends BaseException
{

    private static final long serialVersionUID = -5878839432690385704L;

    /**
     * NotFoundException constructor.
     * 
     * @param msg
     *            the message
     */
    public NotFoundException(String msg)
    {
        super(msg);
        log.error(msg);
    }

}
