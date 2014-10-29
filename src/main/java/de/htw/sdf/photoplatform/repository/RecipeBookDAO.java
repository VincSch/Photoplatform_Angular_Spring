/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.RecipeBook;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for recipe books.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface RecipeBookDAO extends GenericDAO<RecipeBook>
{

    /**
     * find a ingredient by its unique name.
     * 
     * @param name
     *            unique name
     * 
     * @return the ingredient entity
     */
    RecipeBook findByName(String name);

    /**
     * find a recipe books created by a specified user.
     * 
     * @param name
     *            unique user name
     * 
     * @return the recipe entity
     */
    List<RecipeBook> findByUserName(String name);
}
