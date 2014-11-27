/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.exception;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import org.springframework.validation.Errors;

/**
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class BadRequestException extends AbstractBaseException {

    private static final long serialVersionUID = -5878839432690385704L;

    /**
     * BadRequestException constructor.
     *
     * @param msg the message
     */
    public BadRequestException(String msg) {
        super(AbstractBaseException.BAD_REQUEST, msg);
    }

    /**
     * BadRequestException constructor.
     *
     * @param msg    the message
     * @param errors the errors
     */
    public BadRequestException(String msg, Errors errors) {
        super(AbstractBaseException.BAD_REQUEST, msg, errors);
    }

}
