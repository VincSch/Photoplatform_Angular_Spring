/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

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

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<CollectionImage> findCollectionImagesBy(Boolean isCollectionPublic) {
        StringBuilder queryBuilder = initCollectionAndImagesSelect();
        queryBuilder.append("WHERE collection.isPublic = :isPublic ");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("isPublic", isCollectionPublic);

        return (List<CollectionImage>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public CollectionImage findCollectionImagesBy(Long imageId) {
        StringBuilder queryBuilder = initCollectionAndImagesSelect();
        queryBuilder.append("WHERE image.id = :imageId ");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("imageId", imageId);
        try {
            return (CollectionImage) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Image findNewThumbnail(final Collection collection) {
        StringBuilder queryBuilder = initCollectionAndImagesSelect();
        queryBuilder.append("WHERE collection.id = :collectionId AND ");
        queryBuilder.append("image.id != :imageId ");
        Query query = createQuery(queryBuilder.toString());
        query.setFirstResult(0);
        query.setMaxResults(1);
        query.setParameter("collectionId", collection.getId());
        query.setParameter("imageId", collection.getThumbnail().getId());
        try {
            CollectionImage collectionImage = (CollectionImage) query.getSingleResult();
            return collectionImage.getImage();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private StringBuilder initCollectionAndImagesSelect() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(collectionImage) FROM CollectionImage collectionImage ");
        queryBuilder.append("LEFT JOIN FETCH collectionImage.collection collection ");
        queryBuilder.append("LEFT JOIN FETCH collectionImage.image image ");
        return queryBuilder;
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
