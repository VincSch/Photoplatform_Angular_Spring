/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;

/**
 * Interface defining business methods for collections.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface PhotographerManager {

    /**
     * update a collection.
     *
     * @param entity collection you want to update
     * @return the updated category
     */
    Collection update(final Collection entity);

    /**
     * delete a collection.
     *
     * @param entity collection to be deleted
     */
    void delete(final Collection entity);

    /**
     * find collection by its id.
     *
     * @param id collection id
     * @return collection class
     */
    Collection getCollectionById(final long id);

    /**
     * find all collections.
     *
     * @return a list of all collections
     */
    List<Collection> getAllCollection();

    /**
     * delete all collections.
     */
    void deleteAllCollections();

    /**
     * Returns exact one with all data.
     * <p>
     * Fetch all data.
     *
     * @param collectionId collection id
     * @return collection
     */
    Collection getCollectionById(final Long collectionId);

    /**
     * Returns list of user's collection.
     * @param userId collection owner id.
     * @param start from value
     * @param count @return list of user's collection
     */
    List<Collection> getCollectionByUser(final long userId, int start, int count);


    /**
     * Return only public collection for specified user id
     * @param userId the user id
     * @param start the start
     * @param count the max
     * @return list of collection
     */
    List<Collection> getShowcaseByUser(Long userId, int start, int count);

    /**
     * Create image and set reference to user.
     *
     * @param photograph user with photograph role.
     * @param image image to create.
     * @return created userImage.
     * @throws ManagerException manager exception.
     */
    UserImage createPhotographImage(User photograph, Image image) throws ManagerException ;

    /**
     * Create images and set reference to user.
     *
     * @param photograph user with photograph role.
     * @param images list of image to create.
     * @return list of created userImage.
     * @throws ManagerException manager exception.
     */
    List<UserImage> createPhotographImage(User photograph, List<Image> images) throws ManagerException ;

    /**
     * Create collection
     * @param userId the user id
     * @param name   the name
     * @param description the description
     */
    Collection createCollection(Long userId, String name, String description) ;

    /**
     * Add List of images to collection.
     *
     * Find collection by collection and user id.
     * Find all images by image and user id.
     * add images to collection.
     *
     * @param userId collection owner.
     * @param collectionId affected collection id.
     * @param imageId image id
     * @return created collectionImage.
     * @throws ManagerException manager exception.
     */
    CollectionImage addImageToCollection(Long userId, Long collectionId, Long imageId) throws ManagerException ;

    /**
     * Add List of images to collection.
     *
     * Find collection by collection and user id.
     * Find all images by image and user id.
     * add images to collection.
     *
     * @param userId collection owner.
     * @param collectionId affected collection id.
     * @param imageIds list of image id's
     * @return list of created collectionImages.
     * @throws ManagerException manager exception.
     */
    List<CollectionImage> addImagesToCollection(Long userId, Long collectionId, List<Long> imageIds) throws ManagerException ;

    /**
     * Delete one image from collection.
     *
     * @param userId collection owner.
     * @param collectionId affected collection id.
     * @param imageId deleted collectionImages.
     * @return true if success deleted, otherwise exception.
     * @throws ManagerException manager exception.
     */
    Boolean deleteImagesFromCollection(Long userId, Long collectionId, Long imageId) throws ManagerException;

    /**
     * Delete list of images from collection.
     * @param userId collection owner.
     * @param collectionId affected collection id.
     * @param imageIds list of deleted collectionImages.
     * @return true if success deleted, otherwise exception.
     * @throws ManagerException manager exception.
     */
    Boolean deleteImagesFromCollection(Long userId, Long collectionId, List<Long> imageIds) throws ManagerException ;

    /**
     * Delete collection.
     *
     * If collection include images,
     * than the reference between collection and images will be deleted too!
     *
     * @param userId collection owner.
     * @param collectionId collection id.
     * @return true, if deleted successfully, otherwise exception.
     * @throws ManagerException manager exception.
     */
    Boolean deleteCollection(Long userId, Long collectionId) throws ManagerException ;

    /**
     * This method add collection to showcase and remove from showcase.
     *
     * @param userId collection owner.
     * @param collectionId collection id.
     * @param publicValue showcase value.
     *        true, add to showcase
     *        false, remove from showcase.
     * @return true if ok, otherwise exception.
     * @throws ManagerException manager exception.
     */
    Boolean updateCollectionsPublicValue(Long userId, Long collectionId,Boolean publicValue) throws ManagerException;


    /**
     * Delete photograph image.
     *
     * If the image was bought, than delete only reference.
     * Returns false if only reference was deleted, return true if the image was hard deleted.
     *
     * @param ownerId image owner.
     * @param imageId image id
     * @return false if only reference was deleted, return true if the image was hard deleted.
     * @throws ManagerException manager exception.
     */
    Boolean deleteImage(Long ownerId, Long imageId) throws ManagerException ;
}
