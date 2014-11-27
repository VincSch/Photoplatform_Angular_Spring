/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.Category;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for collection category.
 *
 * @author Sergej Meister.
 */
public interface CategoryDAO extends GenericDAO<Category> {
    /**
     * Returns category by name.
     *
     * @param name category name.
     * @return category.
     */
    Category findByName(String name);
}
