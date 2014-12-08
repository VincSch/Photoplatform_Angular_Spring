/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.exception.common;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public abstract class AbstractBaseException extends Exception {

    /**
     * Default BAD_REQUEST.
     */
    public static final int BAD_REQUEST = 10000;

    /**
     * Default NOT_FOUND.
     */
    public static final int NOT_FOUND = 10001;

    /**
     * exception id for bad authorization.
     */
    public static final int AUTHORIZATION_NOT_VALID = 10002;

    /**
     * Email exists.
     */
    public static final int USER_EMAIL_EXISTS = 11002;

    /**
     * Collection Id is not valid!
     */
    public static final int COLLECTION_ID_NOT_VALID = 11003;

    /**
     * Param is not valid, is null or empty or doesn't belong to the request user.
     */
    public static final int PARAM_IS_NOT_VALID = 11004;

    private static final long serialVersionUID = 1L;

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    private final int code;

    private final Errors errors;

    /**
     * BaseException constructor.
     *
     * @param code the code
     */
    public AbstractBaseException(int code) {
        super("Abstract base exception with code = " + code);
        this.code = code;

        this.errors = null; // Empty
    }

    /**
     * BaseException constructor.
     *
     * @param msg the message
     */
    public AbstractBaseException(int code, String msg) {
        super(msg);
        this.code = code;

        this.errors = null; // Empty
    }

    /**
     * BaseException constructor.
     *
     * @param msg    the message
     * @param errors the errors
     */
    public AbstractBaseException(int code, String msg, Errors errors) {
        super(msg);
        this.code = code;
        this.errors = errors;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return return the errors
     */
    public Errors getErrors() {
        return this.errors;
    }
}
