/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.persistence.model.Collection;

import java.util.List;

/**
 * Interface defining business methods for collections.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface PhotographerManager {

    /**
     * update a collection.
     *
     * @param entity collection you want to update
     * @return the updated category
     */
    Collection update(final Collection entity);

    /**
     * delete a collection.
     *
     * @param entity collection to be deleted
     */
    void delete(final Collection entity);

    /**
     * find collection by its id.
     *
     * @param id collection id
     * @return collection class
     */
    Collection getCollectionById(final long id);

    /**
     * find all collections.
     *
     * @return a list of all collections
     */
    List<Collection> getAllCollection();

    /**
     * delete all collections.
     */
    void deleteAllCollections();

    /**
     * Returns exact one with all data.
     * <p>
     * Fetch all data.
     *
     * @param collectionId collection id
     * @return collection
     */
    Collection getCollectionById(final Long collectionId);

    /**
     * Returns list of user's collection.
     *  @param user collection owner.
     * @param userId
     * @param start
     * @param count @return list of user's collection
     */
    List<Collection> getCollectionByUser(final long userId, int start, int count);

    /**
     * Create collection
     * @param userId the user id
     * @param name   the name
     * @param description the description
     */
    Collection createCollection(Long userId, String name, String description);
}
