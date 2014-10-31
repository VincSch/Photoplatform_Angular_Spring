/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.Ingredient;
import de.htw.sdf.photoplatform.repository.IngredientDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for ingredients.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class IngredientDAOImpl extends GenericDAOImpl<Ingredient> implements IngredientDAO
{

    /**
     * Ingredient DAO constructor.
     */
    public IngredientDAOImpl()
    {
        super();
        setClazz(Ingredient.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ingredient findByName(String name)
    {
        String queryString = "SELECT ingredient FROM Ingredient ingredient "
                + "WHERE ingredient.name like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return (Ingredient) query.getSingleResult();
    }

}
