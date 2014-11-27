package de.htw.sdf.photoplatform.exception.common;

/**
 * The exception for not authorized action.
 * <p/>
 * you are caught in a trying to hack our system :(, police is on the way :)!
 *
 * @author Sergej Meister
 */
public class AuthorizationException extends AbstractBaseException {

    private static final long serialVersionUID = 423565295525712970L;

    /**
     * Web Form validation exceptions.
     *
     * @param code the code
     */
    public AuthorizationException(int code) {
        super(code, "Authorization Exception");
    }
}
