/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.User;

/**
 * Interface defining business methods for recipe ingredient mapping.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface UserManager
{

    /**
     * persist a User mapping.
     * 
     * @param entity
     *            the user to create
     */
    void create(final User entity);

    /**
     * update a User.
     * 
     * @param entity
     *            User you want to update
     * 
     * @return the updated User
     */
    User update(final User entity);

    /**
     * delete a User.
     * 
     * @param entity
     *            user to be deleted
     */
    void delete(final User entity);

    /**
     * find User its id.
     * 
     * @param id
     *            User id
     * @return User class
     */
    User findById(final long id);

    /**
     * find all Users.
     * 
     * @return a list of all Users
     */
    List<User> findAll();

    /**
     * delete all Users.
     * 
     */
    void deleteAll();

    /**
     * find a user by its unique name.
     * 
     * @param name
     *            unique name
     * @return the user entity
     */
    User findByName(String name);

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
