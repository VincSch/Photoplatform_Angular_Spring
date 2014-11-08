/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import de.htw.sdf.photoplatform.manager.CollectionManager;
import de.htw.sdf.photoplatform.persistence.models.Collection;
import de.htw.sdf.photoplatform.persistence.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;

/**
 * business methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
@Service
@Transactional
public class CollectionManagerImpl extends DAOReferenceCollector implements CollectionManager
{
    @Override
    public void create(Collection entity)
    {
        collectionDAO.create(entity);
    }

    @Override
    public Collection update(Collection entity)
    {
        return collectionDAO.update(entity);
    }

    @Override
    public void delete(Collection entity)
    {
        collectionDAO.delete(entity);
    }

    @Override
    public Collection findById(long id)
    {
        return collectionDAO.findOne(id);
    }

    @Override
    public List<Collection> findAll()
    {
        return collectionDAO.findAll();
    }

    @Override
    public void deleteAll()
    {
        collectionDAO.findAll();
    }

    @Override
    public Collection findById(Long collectionId)
    {
        return collectionDAO.findById(collectionId);
    }

    @Override
    public List<Collection> findByUser(User user)
    {
        return collectionDAO.findByUser(user);
    }
}
