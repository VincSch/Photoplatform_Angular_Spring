/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.exception.common;

/**
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class ManagerException extends AbstractBaseException {

    /**
     * InternalException constructor.
     *
     * @param code the code
     */
    public ManagerException(int code) {
        super(code, "Internal Manager Exception");
    }
}
