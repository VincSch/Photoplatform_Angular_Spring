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
     * Returns list of userImages by given user id and list of image id's.
     *
     * @param ownerId user id of the image owner.
     * @param imageId affected image.
     * @return list of userImage.
     */
    List<UserImage> getOwnerImages(long ownerId, Long imageId);

    /**
     * Returns list of userImages by given user id and list of image id's.
     *
     * @param ownerId user id of the image owner.
     * @param imageIds list of affected images.
     * @return list of userImage.
     */
    List<UserImage> getOwnerImages(long ownerId, List<Long> imageIds);

    /**
     * Returns all photograph's images.
     *
     * The image belong to photograph, when both the owner and the user is photograph.
     *
     * @param owner photograph.
     * @return Return List of user images.
     */
    List<UserImage> getPhotographImages(User owner);

    /**
     * Returns all photograph's images between start and count.
     *
     * The image belong to photograph, when both the owner and the user is photograph.
     *
     * @param owner photograph.
     * @param start the start.
     * @param count the max.
     * @return List of user images.
     */
    List<UserImage> getPhotographImages(User owner, int start, int count);

    /**
     * Returns one photograph's image.
     *
     * @param owner photograph.
     * @param imageId affected image id.
     * @return one photograph's image.
     */
    UserImage getPhotographImage(User owner, Long imageId);

    /**
     * Returns images that are not added to the collection.
     *
     * @param owner photograph.
     * @return list of UserImage.
     */
    List<UserImage> getPhotographImagesWithoutCollection(User owner);
}
