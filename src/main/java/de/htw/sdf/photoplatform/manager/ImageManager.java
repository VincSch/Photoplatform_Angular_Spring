/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.persistence.model.Category;
import de.htw.sdf.photoplatform.persistence.model.Image;

import java.util.List;

/**
 * Interface defining business methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface ImageManager {

    /**
     * persist a category.
     *
     * @param entity the category to create
     */
    void create(final Image entity);

    /**
     * update a category.
     *
     * @param entity category you want to update
     * @return the updated category
     */
    Image update(final Image entity);

    /**
     * delete a category.
     *
     * @param entity category to be deleted
     */
    void delete(final Image entity);

    /**
     * find category by its id.
     *
     * @param id category id
     * @return category class
     */
    Image findById(final long id);

    /**
     * find all categories.
     *
     * @return a list of all categories
     */
    List<Image> findAll();

    /**
     * delete all categories.
     */
    void deleteAll();
}
