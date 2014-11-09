/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.Category;
import de.htw.sdf.photoplatform.persistence.models.CollectionCategory;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining relation between collection and category.
 *
 * @author Sergej Meister
 */
public interface CollectionCategoryDAO extends GenericDAO<CollectionCategory> {
    /**
     * Returns all collections by given category.
     *
     * @param category
     *            categoty.
     * @return list of collectionCategory.
     */
    List<CollectionCategory> getCollectionCategoryBy(Category category);
}
