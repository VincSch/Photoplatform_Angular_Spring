/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;

/**
 * business methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * @author <a href="mailto:s0521159@htw-berlin.de">Sergej Meister</a>
 */
@Service
@Transactional
public class PhotographerManagerImpl extends DAOReferenceCollector implements
        PhotographerManager {

    @Override
    public Collection update(Collection entity) {
        return collectionDAO.update(entity);
    }

    @Override
    public void delete(Collection entity) {
        collectionDAO.delete(entity);
    }

    @Override
    public Collection getCollectionById(long id) {
        return collectionDAO.findOne(id);
    }

    @Override
    public List<Collection> getAllCollection() {
        return collectionDAO.findAll();
    }

    @Override
    public void deleteAllCollections() {
        collectionDAO.findAll();
    }

    @Override
    public Collection getCollectionById(Long collectionId) {
        return collectionDAO.findById(collectionId);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Collection> getCollectionByUser(long userId, int start, int count) {
        return collectionDAO.findCollectionsByUser(userId, start, count, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Collection> getShowcaseByUser(Long userId, int start, int count) {
        return collectionDAO.findCollectionsByUser(userId, start, count, Boolean.TRUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserImage createPhotographImage(User photograph, Image image) throws ManagerException {
        List<Image> images = new ArrayList<>();
        images.add(image);
        return createPhotographImage(photograph, images).get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> createPhotographImage(User photograph, List<Image> images) throws ManagerException {
        List<UserImage> result = new ArrayList<>();
        for (Image image : images) {
            imageDAO.create(image);

            UserImage userImage = new UserImage();
            userImage.setOwner(photograph);
            userImage.setUser(photograph);
            userImage.setImage(image);
            userImageDAO.create(userImage);
            result.add(userImage);
        }

        if (images.size() != result.size()) {
            throw new RuntimeException(
                    "The size of image to create is different to size of created images. This should not happen");
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection createCollection(final Long userId, final String name, final String description) {
        User user = userDAO.findById(userId);

        Collection collection = new Collection();
        collection.setUser(user);
        collection.setName(name);
        collection.setDescription(description);
        // Not public, not in show case
        collection.setPublic(false);

        collectionDAO.create(collection);

        return collection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollectionImage addImageToCollection(Long userId, Long collectionId, Long imageId) throws ManagerException {
        List<Long> imageIds = new ArrayList<>();
        imageIds.add(imageId);
        return addImagesToCollection(userId, collectionId, imageIds).get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CollectionImage> addImagesToCollection(Long userId, Long collectionId,
                                                       List<Long> imageIds) throws ManagerException {
        List<CollectionImage> result = new ArrayList<>();

        if (imageIds == null || imageIds.isEmpty()) {
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }

        Collection affectedCollection = getCollection(userId, collectionId);

        List<UserImage> imagesToAdd = userImageDAO.getOwnerImages(userId, imageIds);
        if (imagesToAdd.isEmpty()) {
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }

        for (UserImage userImage : imagesToAdd) {
            CollectionImage collectionImage = new CollectionImage();
            collectionImage.setCollection(affectedCollection);
            collectionImage.setImage(userImage.getImage());
            collectionImageDAO.create(collectionImage);
            result.add(collectionImage);
        }

        if (result.size() != imageIds.size()) {
            throw new RuntimeException(
                    "The size of image id's is different to size of founded images. This should not happen");
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteImagesFromCollection(Long userId, Long collectionId, Long imageId) throws ManagerException {
        List<Long> imageIds = new ArrayList<>();
        imageIds.add(imageId);
        return deleteImagesFromCollection(userId, collectionId, imageIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteImagesFromCollection(Long userId, Long collectionId,
                                              List<Long> imageIds) throws ManagerException {
        if (collectionId == null) {
            throw new ManagerException(AbstractBaseException.COLLECTION_ID_NOT_VALID);
        }

        if (imageIds == null || imageIds.isEmpty()) {
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }

        Set<CollectionImage> collectionImages = collectionDAO.findCollectionImagesBy(userId, collectionId, imageIds);
        if (collectionImages.size() != imageIds.size()) {
            throw new RuntimeException(
                    "The size of image id's is different to size of founded images. This should not happen");
        }

        for (CollectionImage collectionImage : collectionImages) {
            collectionImageDAO.delete(collectionImage);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteImage(Long ownerId, Long imageId) throws ManagerException {
        if (imageId == null) {
            throw new ManagerException(AbstractBaseException.PARAM_IS_NOT_VALID);
        }

        List<UserImage> imagesToDelete = userImageDAO.getOwnerImages(ownerId, imageId);
        if (imagesToDelete.isEmpty()) {
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }

        //check reference to collection?
        CollectionImage collectionImage = collectionImageDAO.findBy(ownerId,imageId);
        if(collectionImage != null) {
            //check thumbnail
            if(collectionImage.getCollection().getThumbnail() != null &&
                    collectionImage.getCollection().getThumbnail().getId().equals(imageId)){
                //remove this image from collection thumbnail.
                collectionImage.getCollection().setThumbnail(null);
                collectionDAO.update(collectionImage.getCollection());
            }
            //remove reference to collection.
            collectionImageDAO.delete(collectionImage);
        }

        if(imagesToDelete.size() > 1){
            //The image was bought.
            //remove only reference to photograph.
            for(UserImage userImage : imagesToDelete){
                if(userImage.getOwner().getId().equals(ownerId) &&
                        userImage.getUser().getId().equals(ownerId)){
                    userImageDAO.delete(userImage);
                }
            }
        }else{
            UserImage userImage = imagesToDelete.get(0);
            imageDAO.delete(userImage.getImage());
            userImageDAO.delete(userImage);
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteCollection(Long userId, Long collectionId) throws ManagerException {

        Collection affectedCollection = getCollection(userId, collectionId);

        for (CollectionImage collectionImage : affectedCollection.getCollectionImages()) {
            //Hm, collection include images
            //what should happen with images by deleting a collection.
            //My Solution, delete reference between collection and image, but not the image!
            collectionImageDAO.delete(collectionImage);
        }

        //now we can remove the collection!
        collectionDAO.delete(affectedCollection);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean updateCollectionsPublicValue(Long userId, Long collectionId,
                                                Boolean publicValue) throws ManagerException {
        Collection collectionToUpdate = getCollection(userId, collectionId);
        collectionToUpdate.setPublic(publicValue);
        collectionDAO.update(collectionToUpdate);
        return true;
    }

    private Collection getCollection(Long userId, Long collectionId) throws ManagerException {
        if (collectionId == null) {
            throw new ManagerException(AbstractBaseException.COLLECTION_ID_NOT_VALID);
        }

        Collection affectedCollection = collectionDAO.findByUserAndCollectionId(userId, collectionId);
        if (affectedCollection == null) {
            throw new ManagerException(AbstractBaseException.COLLECTION_ID_NOT_VALID);
        }

        return affectedCollection;
    }
}
