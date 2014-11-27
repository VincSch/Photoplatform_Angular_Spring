/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for relation between user and image.
 *
 * @author Sergej Meister.
 */
public interface UserImageDAO extends GenericDAO<UserImage> {
    /**
     * Returns all images of given user.
     *
     * @param user user
     * @return Return List of user images.
     */
    List<UserImage> getUserImagesBy(User user);
}
