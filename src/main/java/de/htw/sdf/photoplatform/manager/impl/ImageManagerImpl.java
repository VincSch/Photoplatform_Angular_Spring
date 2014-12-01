/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * business methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Service
@Transactional
public class ImageManagerImpl extends DAOReferenceCollector implements
    ImageManager {

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
}
