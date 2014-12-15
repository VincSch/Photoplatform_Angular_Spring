/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.repository.UserImageDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * Repository methods for relation between user and image.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class UserImageDAOImpl extends GenericDAOImpl<UserImage> implements
        UserImageDAO {

    /**
     * UserImage DAO constructor.
     */
    public UserImageDAOImpl() {
        super();
        setClazz(UserImage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<UserImage> getUserImagesBy(User user) {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE user.id = ?1");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, user.getId());
        return (List<UserImage>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getOwnerImages(long ownerId, Long imageId) {
        List<Long> imageIds= new ArrayList<>();
        imageIds.add(imageId);
        return getOwnerImages(ownerId, imageIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<UserImage> getOwnerImages(long ownerId, List<Long> imageIds) {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE owner.id = :ownerId AND image.id IN (:imageIds)");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("ownerId", ownerId);
        query.setParameter("imageIds", imageIds);
        return (List<UserImage>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getPhotographImages(User owner) {
        Query query = initPhotographImageQuery(owner);

        return (List<UserImage>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getPhotographImages(User owner, int start, int count) {
        Query query = initPhotographImageQuery(owner);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return (List<UserImage>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserImage getPhotographImage(User owner, Long imageId) {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE owner.id = :ownerId AND user.id = :userId AND image.id= :imageId");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("ownerId", owner.getId());
        query.setParameter("userId", owner.getId());
        query.setParameter("imageId", imageId);
        try {
            return (UserImage) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getPhotographImagesWithoutCollection(User owner) {
        StringBuilder queryBuilder = initSelectWithCollection();
        queryBuilder.append("WHERE owner.id = :ownerId AND user.id = :userId AND collectionImages IS NULL ");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("ownerId", owner.getId());
        query.setParameter("userId", owner.getId());

        return (List<UserImage>) query.getResultList();
    }

    private Query initPhotographImageQuery(User owner) {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE owner.id = :ownerId AND user.id = :userId");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("ownerId", owner.getId());
        query.setParameter("userId", owner.getId());
        return query;
    }

    private StringBuilder initSelectQuery() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(userImage) FROM UserImage userImage ");
        queryBuilder.append("LEFT JOIN FETCH userImage.image image ");
        queryBuilder.append("LEFT JOIN FETCH userImage.owner owner ");
        queryBuilder.append("LEFT JOIN FETCH userImage.user user ");

        return queryBuilder;
    }

    private StringBuilder initSelectWithCollection() {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("LEFT JOIN FETCH image.collectionImages collectionImages ");

        return queryBuilder;
    }
}
