/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for image collection.
 *
 * @author Sergej Meister.
 */
public interface CollectionDAO extends GenericDAO<Collection> {

    /**
     * Returns exact one with all data.
     * <p/>
     * Fetch all data.
     *
     * @param collectionId collection id
     * @return collection
     */
    Collection findById(final Long collectionId);

    /**
     * Returns list of user's collection.
     *
     * @param user collection owner.
     * @return list of user's collection
     */
    List<Collection> findByUser(final User user);
}
