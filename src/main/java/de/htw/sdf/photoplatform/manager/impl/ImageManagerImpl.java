/**
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;

/**
 * business methods for images.
 *
 * @author Sergej Meister
 */
@Service
@Transactional
public class ImageManagerImpl extends DAOReferenceCollector implements ImageManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getPhotographImages(User owner) {
        return userImageDAO.getPhotographImages(owner);
    }
}
