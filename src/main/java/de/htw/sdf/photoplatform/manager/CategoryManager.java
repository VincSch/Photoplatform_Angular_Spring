/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.persistence.model.Category;

import java.util.List;

/**
 * Interface defining business methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface CategoryManager {

    /**
     * persist a category.
     *
     * @param entity the category to create
     */
    void create(final Category entity);

    /**
     * update a category.
     *
     * @param entity category you want to update
     * @return the updated category
     */
    Category update(final Category entity);

    /**
     * delete a category.
     *
     * @param entity category to be deleted
     */
    void delete(final Category entity);

    /**
     * find category by its id.
     *
     * @param id category id
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
     * delete all categories.
     */
    void deleteAll();
}
