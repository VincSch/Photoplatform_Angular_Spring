/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository methods for image collection.
 *
 * @author Sergej Meister.
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
    @SuppressWarnings("unchecked")
    public Collection findByUserAndCollectionId(final long userId, final long collectionId) {
        StringBuilder queryBuilder = initCollectionDataSelect();
        queryBuilder.append("WHERE owner.id = :userId AND collection.id = :collectionId");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("userId", userId);
        query.setParameter("collectionId", collectionId);
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
    @SuppressWarnings("unchecked")
    public List<Collection> findCollectionsByUser(final long userId, final int start, final int count,
                                                  Optional<Boolean> isPublic) {
        StringBuilder queryBuilder = initFullDataCollectionSelect();
        queryBuilder.append("WHERE owner.id = :userId");
        if (isPublic.isPresent()) {
            queryBuilder.append(" AND collection.isPublic = :isPublic");
        }

        Query query = createQuery(queryBuilder.toString());
        query.setParameter("userId", userId);

        if (isPublic.isPresent()) {
            query.setParameter("isPublic", isPublic.get());
        }

        if (start > 0) {
            query.setFirstResult(start);
        }

        if (count > 0) {
            query.setMaxResults(count);
        }

        return (List<Collection>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<CollectionImage> findCollectionImagesBy(Long userId, Long collectionId, List<Long> imageIds) {
        StringBuilder queryBuilder = initCollectionAndImagesSelect();
        queryBuilder.append("WHERE collection.id = :collectionId ");
        queryBuilder.append("AND owner.id = :userId ");
        queryBuilder.append("AND image.id IN (:imageIds) ");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("collectionId", collectionId);
        query.setParameter("userId", userId);
        query.setParameter("imageIds", imageIds);

        Collection result = (Collection) query.getSingleResult();
        return result.getCollectionImages();
    }

    private StringBuilder initFullDataCollectionSelect() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(collection) FROM Collection collection ");
        queryBuilder.append("LEFT JOIN FETCH collection.user owner ");
        queryBuilder.append("LEFT JOIN FETCH collection.thumbnail thumbnail ");
        queryBuilder.append("LEFT JOIN FETCH collection.collectionImages collectionImages ");
        queryBuilder.append("LEFT JOIN FETCH collectionImages.image image ");
        queryBuilder
                .append("LEFT JOIN FETCH collection.collectionCategories collectionCategories ");

        return queryBuilder;
    }

    private StringBuilder initCollectionAndImagesSelect() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(collection) FROM Collection collection ");
        queryBuilder.append("LEFT JOIN FETCH collection.user owner ");
        queryBuilder.append("LEFT JOIN FETCH collection.collectionImages collectionImages ");
        queryBuilder.append("LEFT JOIN FETCH collectionImages.image image ");

        return queryBuilder;
    }

    private StringBuilder initCollectionDataSelect() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(collection) FROM Collection collection ");
        queryBuilder.append("LEFT JOIN FETCH collection.user owner ");
        queryBuilder.append("LEFT JOIN FETCH collection.collectionImages collectionImages ");
        return queryBuilder;
    }
}
