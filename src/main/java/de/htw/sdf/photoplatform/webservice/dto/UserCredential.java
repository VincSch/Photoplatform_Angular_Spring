/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Data Transfer Object.
 *
 * @author <a href="s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class UserCredential
{

    @NotEmpty
    @Size(min = 3, max = 30)
    protected String username;


    @NotEmpty
    @Size(min = 3)
    protected String password;

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
