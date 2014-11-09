/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.Collection;
import de.htw.sdf.photoplatform.persistence.models.User;

/**
 * Interface defining business methods for collections.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface CollectionManager {

    /**
     * persist a collection.
     * 
     * @param entity
     *            the collection to create
     */
    void create(final Collection entity);

    /**
     * update a collection.
     * 
     * @param entity
     *            collection you want to update
     * 
     * @return the updated category
     */
    Collection update(final Collection entity);

    /**
     * delete a collection.
     * 
     * @param entity
     *            collection to be deleted
     */
    void delete(final Collection entity);

    /**
     * find collection by its id.
     * 
     * @param id
     *            collection id
     * @return collection class
     */
    Collection findById(final long id);

    /**
     * find all collections.
     * 
     * @return a list of all collections
     */
    List<Collection> findAll();

    /**
     * delete all collections.
     * 
     */
    void deleteAll();

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
    Collection findById(final Long collectionId);

    /**
     * Returns list of user's collection.
     *
     * @param user
     *            collection owner.
     *
     * @return list of user's collection
     */
    List<Collection> findByUser(final User user);
}
