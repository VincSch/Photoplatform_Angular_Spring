/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.Category;
import de.htw.sdf.photoplatform.persistence.models.CollectionCategory;
import de.htw.sdf.photoplatform.repository.CollectionCategoryDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * Repository methods for relation between collection and category.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class CollectionCategoryDAOImpl extends GenericDAOImpl<CollectionCategory> implements
        CollectionCategoryDAO
{

    public CollectionCategoryDAOImpl()
    {
        super();
        setClazz(CollectionCategory.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CollectionCategory> getCollectionCategoryBy(Category category)
    {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE category.id = ?1");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, category.getId());
        return (List<CollectionCategory>) query.getResultList();
    }

    private StringBuilder initSelectQuery()
    {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(collectionCategory) FROM CollectionCategory collectionCategory ");
        queryBuilder.append("LEFT JOIN FETCH collectionCategory.collection collection ");
        queryBuilder.append("LEFT JOIN FETCH collectionCategory.category category ");

        return queryBuilder;
    }
}
