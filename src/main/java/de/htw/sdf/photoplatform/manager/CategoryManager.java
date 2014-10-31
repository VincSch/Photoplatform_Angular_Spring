/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.Category;

/**
 * Interface defining business methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface CategoryManager
{

    /**
     * persist a category.
     * 
     * @param entity
     *            ingredient
     */
    void create(final Category entity);

    /**
     * update a category.
     * 
     * @param entity
     *            the category you want to update
     * @return the updated category
     */
    Category update(final Category entity);

    /**
     * delete an category.
     * 
     * @param entity
     *            to be deleted
     */
    void delete(final Category entity);

    /**
     * find a category by its id.
     * 
     * @param id
     *            category id
     * 
     * @return category class
     */
    Category findById(final long id);

    /**
     * find all categories.
     * 
     * @return a list of all categories
     */
    List<Category> findAll();

    /**
     * find a category by its unique name.
     * 
     * @param name
     *            unique name
     * @return the category entity
     */
    Category findByName(String name);

    /**
     * delete all categories .
     */
    void deleteAll();
}
