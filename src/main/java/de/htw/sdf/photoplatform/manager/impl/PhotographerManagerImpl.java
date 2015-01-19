/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.ImageSearchManager;
import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private ImageSearchManager imageSearchManager;

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
        collection.setThumbnail(null);
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
            if (userImage.getImage().getPrice() == null || userImage.getImage().getPrice().doubleValue() <= 0) {
                throw new ManagerException(AbstractBaseException.IMAGE_PRICE_EMPTY);
            }

            if (userImage.getImage().getName() == null || userImage.getImage().getName().isEmpty()) {
                throw new ManagerException(AbstractBaseException.IMAGE_NAME_EMPTY);
            }

            CollectionImage collectionImage = new CollectionImage();
            collectionImage.setCollection(affectedCollection);
            collectionImage.setImage(userImage.getImage());
            collectionImageDAO.create(collectionImage);
            if (affectedCollection.getCollectionImages().size() == 0)
                affectedCollection.setThumbnail(userImage.getImage());
            result.add(collectionImage);

            if (affectedCollection.isPublic()) {
                //create index for full-text search data!
                //only for images in public collections.
                imageSearchManager.createIndex(collectionImage.getImage());
            }
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
                    "The size of image id's is different to size of found images. This should not happen");
        }

        Collection affectedCollection = collectionImages.iterator().next().getCollection();
        //check thumbnail
        if (affectedCollection.getThumbnail() != null && imageIds.contains(affectedCollection.getThumbnail().getId())) {
            updateThumbnail(affectedCollection, null);
        }

        for (CollectionImage collectionImage : collectionImages) {
            collectionImageDAO.delete(collectionImage);

            //remove image elasticsearch index
            removeImageSearchIndexIfPublic(collectionImage.getCollection(), collectionImage.getImage());
        }

        return true;
    }

    /**
     * Delete search index of image, if collection is public.
     *
     * @param collection affected collection.
     * @param image      affected image.
     */
    private void removeImageSearchIndexIfPublic(Collection collection, Image image) {
        if (collection.isPublic()) {
            imageSearchManager.deleteIndex(image);
        }
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
        CollectionImage collectionImage = collectionImageDAO.findBy(ownerId, imageId);
        if (collectionImage != null) {
            //check thumbnail
            if (collectionImage.getCollection().getThumbnail() != null &&
                    collectionImage.getCollection().getThumbnail().getId().equals(imageId)) {
                updateThumbnail(collectionImage.getCollection(), null);
            }
            //remove reference to collection.
            collectionImageDAO.delete(collectionImage);
        }


        if (imagesToDelete.size() > 1) {
            //old logic for purchased images.
            //remove only reference to photograph.
            for (UserImage userImage : imagesToDelete) {
                if (userImage.getOwner().getId().equals(ownerId) &&
                        userImage.getUser().getId().equals(ownerId)) {
                    userImageDAO.delete(userImage);
                }
            }
        }else {
            UserImage userImage = imagesToDelete.get(0);
            userImageDAO.delete(userImage);
            //new logic for purchased images.
            if(!purchaseItemDAO.isPurchased(userImage.getImage())){
                imageDAO.delete(userImage.getImage());
            }
            imageSearchManager.deleteIndex(userImage.getImage());
            return true;
        }


        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getImagesWithoutCollection(final User photograph) {
        return userImageDAO.getPhotographImagesWithoutCollection(photograph);
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
            //remove image elasticsearch index
            removeImageSearchIndexIfPublic(collectionImage.getCollection(), collectionImage.getImage());
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

        //remove or add image search index!
        if (publicValue) {
            for (CollectionImage collectionImage : collectionToUpdate.getCollectionImages()) {
                imageSearchManager.createIndex(collectionImage.getImage());
            }
        } else {
            for (CollectionImage collectionImage : collectionToUpdate.getCollectionImages()) {
                imageSearchManager.deleteIndex(collectionImage.getImage());
            }
        }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateThumbnail(Collection collection, Long imageId) {
        if (imageId == null) {
            Image newThumbnail = collectionImageDAO.findNewThumbnail(collection);
            collection.setThumbnail(newThumbnail);
            collectionDAO.update(collection);
        } else {
            Image img = collectionImageDAO.findCollectionImagesBy(imageId).getImage();
            collection.setThumbnail(img);
            collectionDAO.update(collection);
        }
    }
}
