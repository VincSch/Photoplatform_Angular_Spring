/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining relation between collection and image.
 *
 * @author Sergej Meister.
 */
public interface CollectionImageDAO extends GenericDAO<CollectionImage> {

    /**
     * Returns CollectionImage by filter collection owner id and image id.
     * @param ownerId
     * @param imageId
     */
    CollectionImage findBy(Long ownerId, Long imageId);
}
