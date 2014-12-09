/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for relation between collection and image.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class CollectionImageDAOImpl extends GenericDAOImpl<CollectionImage>
        implements CollectionImageDAO {
    /**
     * CollectionImage DAO constructor.
     */
    public CollectionImageDAOImpl() {
        super();
        setClazz(CollectionImage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public CollectionImage findBy(Long ownerId, Long imageId) {
        StringBuilder queryBuilder = initSelect();
        queryBuilder.append("WHERE image.id = :imageId ");
        queryBuilder.append("AND owner.id = :ownerId ");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("imageId", imageId);
        query.setParameter("ownerId", ownerId);
        try{
            return (CollectionImage) query.getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
    }

    private StringBuilder initSelect() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(collectionImage) FROM CollectionImage collectionImage ");
        queryBuilder.append("LEFT JOIN FETCH collectionImage.collection collection ");
        queryBuilder.append("LEFT JOIN FETCH collection.thumbnail thumbnail ");
        queryBuilder.append("LEFT JOIN FETCH collection.user owner ");
        queryBuilder.append("LEFT JOIN FETCH collectionImage.image image ");
        return queryBuilder;
    }
}
