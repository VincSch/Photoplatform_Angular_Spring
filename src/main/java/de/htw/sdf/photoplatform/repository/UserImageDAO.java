/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
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
     * @param user user
     * @return Return List of user images.
     */
    List<UserImage> getUserImagesBy(User user);

    /**
     * Returns all photograph's images.
     *
     * The image belong to photograph, when both the owner and the user is photograph.
     *
     * @param owner photograph.
     * @return Return List of user images.
     */
    List<UserImage> getPhotographImages(User owner);

}
