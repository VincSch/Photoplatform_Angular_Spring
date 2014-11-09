/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data Transfer Object.
 *
 * @author <a href="s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class UserRegister extends UserCredential
{

    @NotEmpty
    @Email
    private String email;

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

}
