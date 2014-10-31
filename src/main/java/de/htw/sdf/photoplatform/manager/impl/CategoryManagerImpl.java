/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import de.htw.sdf.photoplatform.manager.CategoryManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.models.Category;

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
    public void create(Category entity)
    {
        categoryDAO.create(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category update(Category entity)
    {
        return categoryDAO.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Category entity)
    {
        categoryDAO.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findById(long id)
    {
        return categoryDAO.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll()
    {
        return categoryDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findByName(String name)
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
