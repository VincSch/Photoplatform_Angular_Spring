/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.models.CollectionCategory;
import de.htw.sdf.photoplatform.repository.CollectionCategoryDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * Repository methods for relation between collection and category.
 *
 * @author Sergej Meister.
 */
public class CollectionCategoryDAOImpl extends GenericDAOImpl<CollectionCategory> implements
        CollectionCategoryDAO
{

    public CollectionCategoryDAOImpl()
    {
        super();
        setClazz(CollectionCategory.class);
    }
}
