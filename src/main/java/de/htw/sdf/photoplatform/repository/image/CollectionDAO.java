/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.image;

import java.util.List;

import javax.persistence.NoResultException;

import de.htw.sdf.photoplatform.persistence.models.image.Collection;
import de.htw.sdf.photoplatform.persistence.models.user.User;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for image collection.
 *
 * @autor Sergej Meister.
 */
public interface CollectionDAO extends GenericDAO<Collection>
{

    /**
     * Returns exact one with all data.
     *
     * Fetch all data.
     *
     * @param collectionId
     *            collection id
     *
     * @return collection
     */
    Collection findById(final Long collectionId) throws NoResultException;

    /**
     * Returns list of user's collection.
     *
     * @param user
     *            collection owner.
     *
     * @return list of user's collection
     */
    List<Collection> findByUser(final User user) throws NoResultException;
}
