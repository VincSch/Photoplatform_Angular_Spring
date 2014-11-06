/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.models.CategoryRecept;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface CategoryReceptDAO extends GenericDAO<CategoryRecept>
{

    /**
     * find a category by its unique name.
     * 
     * @param name
     *            unique name
     * 
     * @return the category entity
     */
    CategoryRecept findByName(String name);
}
