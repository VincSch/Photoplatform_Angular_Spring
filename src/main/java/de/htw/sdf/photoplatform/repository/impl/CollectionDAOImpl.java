/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository methods for image collection.
 *
 * @author Sergej Meister.
 *
 */
@Repository
@Transactional
public class CollectionDAOImpl extends GenericDAOImpl<Collection> implements
        CollectionDAO {

    /**
     * Collection DAO constructor.
     */
    public CollectionDAOImpl() {
        super();
        setClazz(Collection.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection findById(final Long collectionId) {
        StringBuilder queryBuilder = initFullDataCollectionSelect();
        queryBuilder.append("WHERE collection.id = ?1");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, collectionId);
        try {
            return (Collection) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Collection> findByUser(final User user) {
        StringBuilder queryBuilder = initFullDataCollectionSelect();
        queryBuilder.append("WHERE owner.id = ?1");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, user.getId());
        try {
            return (List<Collection>) query.getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<>();
        }

    }

    private StringBuilder initFullDataCollectionSelect() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(collection) FROM Collection collection ");
        queryBuilder.append("LEFT JOIN FETCH collection.user owner ");
        queryBuilder.append("LEFT JOIN FETCH collection.thumbnail thumbnail ");
        queryBuilder
                .append("LEFT JOIN FETCH collection.collectionImages collectionImages ");
        queryBuilder
                .append("LEFT JOIN FETCH collection.collectionCategories collectionCategories ");
        queryBuilder.append("LEFT JOIN FETCH collection.showCase showCase ");

        return queryBuilder;
    }
}
