/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;
import java.util.Set;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
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
     * @param userId       user id.
     * @param collectionId collection id.
     * @return collection.
     */
    Collection findByUserAndCollectionId(final long userId, final long collectionId);

    /**
     * Returns list of user's collection.
     *
     * @param userId   user id owner.
     * @param start    index to start
     * @param count    max return
     * @param isPublic if set filter by public
     * @return list of user's collection
     */
    List<Collection> findCollectionsByUser(final long userId, int start, int count, Boolean isPublic);

    /**
     * Returns set of collection images.
     *
     * @param userId       collection owner.
     * @param collectionId collection id.
     * @param imageIds     list of image ids.
     * @return set of collection images.
     */
    Set<CollectionImage> findCollectionImagesBy(Long userId, Long collectionId, List<Long> imageIds);

    /**
     * Returns set of collection images.
     *
     * @param collectionId collection id.
     * @return set of collection images.
     */
    Set<CollectionImage> findCollectionImagesBy(Long collectionId);

    /**
     * Returns set of collection images.
     *
     * @param collectionId collection id.
     * @param start    index to start
     * @param count    max return
     * @return set of collection images.
     */
    Set<CollectionImage> findCollectionImagesBy(Long collectionId, int start, int count);

}
