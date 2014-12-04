/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for image.
 *
 * @author Sergej Meister.
 */
public interface ImageDAO extends GenericDAO<Image> {

    /**
     * Returns all public images.
     *
     * @return list of images
     */
    List<Image> getPublicImages();
}
