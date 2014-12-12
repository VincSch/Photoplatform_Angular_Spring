/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * business methods for images.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Service
@Transactional
public class ImageManagerImpl extends DAOReferenceCollector implements
    ImageManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getPhotographImages(User owner, int start,
        int count) {
        if (start > 0 && count > 0) {
            return userImageDAO.getPhotographImages(owner, start, count);
        }

        return userImageDAO.getPhotographImages(owner);
    }

    @Override public void create(Image entity) {
        imageDAO.create(entity);
    }

    @Override public Image update(Image entity) {
        return imageDAO.update(entity);
    }

    @Override public void delete(Image entity) {
        imageDAO.delete(entity);
    }

    @Override public Image findById(long id) {
        return imageDAO.findOne(id);
    }

    @Override public List<Image> findAll() {
        return imageDAO.findAll();
    }

    @Override public void deleteAll() {
        imageDAO.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image update(Long imageId, String name, Double price,
        String description, User owner) throws ManagerException {
        if (imageId == null) {
            throw new ManagerException(
                AbstractBaseException.PARAM_IS_NOT_VALID);
        }

        UserImage imageToUpdate = userImageDAO
            .getPhotographImage(owner, imageId);
        if (imageToUpdate == null) {
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }
        imageToUpdate.getImage().setName(name);
        imageToUpdate.getImage().setPrice(price);
        imageToUpdate.getImage().setDescription(description);
        imageDAO.update(imageToUpdate.getImage());
        return imageToUpdate.getImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserImage addOwnerToImage(Image img, User owner) {
        UserImage entity = new UserImage();
        entity.setImage(img);
        entity.setOwner(owner);
        entity.setUser(owner);
        entity.setCreatedBy(owner.getUsername());
        userImageDAO.create(entity);
        return entity;
    }
}
