/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.models.Category;
import de.htw.sdf.photoplatform.repository.CategoryDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * Interface defining repository methods for image collection.
 *
 * @author Sergej Meister.
 */
public class CategoryDAOImpl extends GenericDAOImpl<Category> implements CategoryDAO
{
    /**
     * Category DAO constructor.
     */
    public CategoryDAOImpl()
    {
        super();
        setClazz(Category.class);
    }

}
