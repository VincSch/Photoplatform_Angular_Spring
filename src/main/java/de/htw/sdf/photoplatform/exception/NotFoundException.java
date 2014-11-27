/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.exception;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import org.springframework.validation.Errors;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public class NotFoundException extends AbstractBaseException {

    private static final long serialVersionUID = -5878839432690385704L;

    /**
     * NotFoundException constructor.
     *
     * @param msg the message
     */
    public NotFoundException(String msg) {
        super(AbstractBaseException.NOT_FOUND, msg);
    }

    /**
     * NotFoundException constructor.
     *
     * @param msg    the message
     * @param errors the errors
     */
    public NotFoundException(String msg, Errors errors) {
        super(AbstractBaseException.NOT_FOUND, msg, errors);
    }

}
