/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.User;
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

    @Override
    public List<Collection> getCollectionByUser(User user) {
        return collectionDAO.findByUser(user);
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
}
