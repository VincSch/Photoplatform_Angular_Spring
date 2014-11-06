/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.CategoryRecept;
import de.htw.sdf.photoplatform.repository.CategoryReceptDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for categories.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class CategoryReceptDAOImpl extends GenericDAOImpl<CategoryRecept> implements
        CategoryReceptDAO
{

    /**
     * Category DAO constructor.
     */
    public CategoryReceptDAOImpl()
    {
        super();
        setClazz(CategoryRecept.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryRecept findByName(String name)
    {
        String queryString = "SELECT category FROM Category category "
                + "WHERE category.name like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return (CategoryRecept) query.getSingleResult();
    }

}
