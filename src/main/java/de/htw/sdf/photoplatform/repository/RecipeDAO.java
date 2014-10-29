/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.Recipe;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for receipts.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface RecipeDAO extends GenericDAO<Recipe>
{

    /**
     * find a receipt by its unique name.
     * 
     * @param name
     *            unique name
     * @return the recipe entity
     */
    Recipe findByName(String name);

    /**
     * find a recipes created by a specified user.
     * 
     * @param name
     *            unique user name
     * @return the recipe entity
     */
    List<Recipe> findByUserName(String name);

    /**
     * find a recipe which name contains a given word.
     * 
     * @param word
     *            the word we're looking for :)
     * 
     * @return a list of recipes which name contains the given word
     */
    List<Recipe> findByNameContaing(String word);
}
