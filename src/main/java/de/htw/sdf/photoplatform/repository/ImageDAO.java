/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for image.
 *
 * @author Sergej Meister.
 */
public interface ImageDAO extends GenericDAO<Image> {

    /**
     * Returns all public images.
     *
     * @return lit of images
     */
    List<Image> getPublicImages();
}
