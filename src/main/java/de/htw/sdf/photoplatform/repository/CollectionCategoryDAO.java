/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.Category;
import de.htw.sdf.photoplatform.persistence.model.CollectionCategory;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 * Interface defining relation between collection and category.
 *
 * @author Sergej Meister
 */
public interface CollectionCategoryDAO extends GenericDAO<CollectionCategory> {
    /**
     * Returns all collections by given category.
     *
     * @param category categoty.
     * @return list of collectionCategory.
     */
    List<CollectionCategory> getCollectionCategoryBy(Category category);
}
