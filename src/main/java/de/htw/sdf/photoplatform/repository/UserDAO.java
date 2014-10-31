/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import org.springframework.security.core.userdetails.UserDetailsService;

import de.htw.sdf.photoplatform.persistence.User;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface UserDAO extends UserDetailsService, GenericDAO<User>
{

    /**
     * find a user by its unique name.
     * 
     * @param userName
     *            unique name
     * 
     * @return the user entity
     */
    User findByUserName(String userName);

    /**
     * find a number of recipes belonging to this user.
     * 
     * @param userName
     *            unique name
     * 
     * @return amount of recipes
     */
    Long getRecipeAmount(String userName);

    /**
     * find a number of recipe books belonging to this user.
     * 
     * @param userName
     *            unique name
     * 
     * @return amount of recipe books
     */
    Long getRecipeBookAmount(String userName);
}
