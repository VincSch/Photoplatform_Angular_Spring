package de.htw.sdf.photoplatform.exception.common;

/**
 * Web Form validation exceptions.
 *
 * @author Sergej Meister
 */
public class WebFormException extends AbstractBaseException {

    private static final long serialVersionUID = -775254578731958067L;

    /**
     * Web Form validation exceptions.
     *
     * @param code the code
     */
    public WebFormException(int code) {
        super(code, "Web Form validation Exception");
    }
}
