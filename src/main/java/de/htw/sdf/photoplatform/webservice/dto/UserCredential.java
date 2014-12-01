/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Data Transfer Object.
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class UserCredential {

    @NotEmpty
    @Email
    protected String email;

    @NotEmpty
    @Size(min = 3, max = 60)
    protected String password;

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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
