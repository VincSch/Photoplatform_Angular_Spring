/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for image collection.
 *
 * @author Sergej Meister.
 */
public interface CollectionDAO extends GenericDAO<Collection> {

    /**
     * Returns exact one with all data.
     * <p>
     * Fetch all data.
     *
     * @param collectionId collection id
     * @return collection
     */
    Collection findById(final Long collectionId);

    /**
     * Returns only Collection data for affected user.
     *
     * @param userId user id.
     * @param collectionId collection id.
     * @return collection.
     */
    Collection findByUserAndCollectionId(final long userId, final long collectionId);

    /**
     * Returns list of user's collection.
     *
     * @param userId user id owner.
     * @param start  index to start
     * @param count  max return
     * @return list of user's collection
     */
    List<Collection> findCollectionsByUser(final long userId, int start, int count);
}
