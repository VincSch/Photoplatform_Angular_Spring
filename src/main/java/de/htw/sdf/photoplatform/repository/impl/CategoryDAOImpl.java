/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.Category;
import de.htw.sdf.photoplatform.repository.CategoryDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Interface defining repository methods for image collection.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class CategoryDAOImpl extends GenericDAOImpl<Category> implements
        CategoryDAO {
    /**
     * Category DAO constructor.
     */
    public CategoryDAOImpl() {
        super();
        setClazz(Category.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findByName(String name) {
        String queryString = "SELECT category FROM Category category WHERE category.name = ?1";
        Query query = createQuery(queryString);
        query.setParameter(1, name);
        try {
            return (Category) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
