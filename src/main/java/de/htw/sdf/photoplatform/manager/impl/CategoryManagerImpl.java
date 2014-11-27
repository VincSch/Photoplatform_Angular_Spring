/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.manager.CategoryManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Category;
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
public class CategoryManagerImpl extends DAOReferenceCollector implements
        CategoryManager {

    @Override
    public void create(Category entity) {
        categoryDAO.create(entity);
    }

    @Override
    public Category update(Category entity) {
        return categoryDAO.update(entity);
    }

    @Override
    public void delete(Category entity) {
        categoryDAO.delete(entity);
    }

    @Override
    public Category findById(long id) {
        return categoryDAO.findOne(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public void deleteAll() {
        categoryDAO.deleteAll();
    }
}
