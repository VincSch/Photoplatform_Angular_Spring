/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 * Interface defining relation between collection and image.
 *
 * @author Sergej Meister.
 */
public interface CollectionImageDAO extends GenericDAO<CollectionImage> {

    /**
     * Returns CollectionImage by filter collection owner id and image id.
     *
     * @param ownerId
     * @param imageId
     */
    CollectionImage findBy(Long ownerId, Long imageId);

    /**
     * Returns list of all public collection's with their images.
     *
     * @param isCollectionPublic if set filter by public.
     * @return list of collection with images.
     */
    List<CollectionImage> findCollectionImagesBy(Boolean isCollectionPublic);

    /**
     * Returns collectionImage for corresponded image id.
     *
     * @param imageId image id.
     * @return collectionImage for corresponded image id.
     */
    CollectionImage findCollectionImagesBy(Long imageId);
}
