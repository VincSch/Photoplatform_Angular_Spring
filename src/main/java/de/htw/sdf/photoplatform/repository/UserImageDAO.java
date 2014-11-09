/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserImage;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for relation between user and image.
 * 
 * @author Sergej Meister.
 */
public interface UserImageDAO extends GenericDAO<UserImage> {
    /**
     * Returns all images of given user.
     *
     * @param user
     *            user
     *
     * @return Return List of user images.
     */
    List<UserImage> getUserImagesBy(User user);
}
