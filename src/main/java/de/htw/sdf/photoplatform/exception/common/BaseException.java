/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.exception.common;

import org.apache.log4j.Logger;

/**
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public class BaseException extends Exception
{

    private static final long serialVersionUID = 1L;

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * BaseException constructor.
     * 
     * @param msg
     *            the message
     */
    public BaseException(String msg)
    {
        super(msg);
    }
}
