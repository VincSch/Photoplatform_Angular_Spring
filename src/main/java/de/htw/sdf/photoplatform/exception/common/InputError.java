/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.exception.common;

import java.io.Serializable;

/**
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class InputError implements Serializable {

    private final String fieldName;
    private final String message;

    /**
     * InputError contructor.
     *
     * @param fieldName the filed name
     * @param message   the message
     */
    public InputError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    /**
     * @return the filed name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
