/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.dto.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Data Transfer Object.
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class UserRegister extends UserCredential {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String passwordConfirm;

    /**
     * @return the email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public final void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the password confirm
     */
    public final String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * @param passwordConfirm the passwordConfirm to set
     */
    public final void setPasswordConfirm(final String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

}

