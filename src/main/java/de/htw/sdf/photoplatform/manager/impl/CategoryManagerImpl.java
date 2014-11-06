/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import de.htw.sdf.photoplatform.manager.CategoryManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.models.CategoryRecept;

/**
 * Interface defining business methods for recipes.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
public class CategoryManagerImpl extends DAOReferenceCollector implements CategoryManager
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(CategoryRecept entity)
    {
        categoryDAO.create(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryRecept update(CategoryRecept entity)
    {
        return categoryDAO.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(CategoryRecept entity)
    {
        categoryDAO.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryRecept findById(long id)
    {
        return categoryDAO.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryRecept> findAll()
    {
        return categoryDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryRecept findByName(String name)
    {
        return categoryDAO.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll()
    {
        categoryDAO.deleteAll();
    }

}
