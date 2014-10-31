/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.Recipe;
import de.htw.sdf.photoplatform.repository.RecipeDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for receipts.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class RecipeDAOImpl extends GenericDAOImpl<Recipe> implements RecipeDAO
{

    /**
     * Recipe DAO constructor.
     */
    public RecipeDAOImpl()
    {
        super();
        setClazz(Recipe.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe findByName(String name)
    {
        String queryString = "SELECT recipe FROM Recipe recipe "
                + "LEFT JOIN FETCH recipe.usedIngredients usedIngredients "
                + "LEFT JOIN FETCH usedIngredients.ingredient ingredient "
                + "WHERE recipe.name like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return (Recipe) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe findOne(final long id)
    {
        String queryString = "SELECT recipe FROM Recipe recipe "
                + "LEFT JOIN FETCH recipe.usedIngredients usedIngredients "
                + "LEFT JOIN FETCH usedIngredients.ingredient ingredient "
                + "WHERE recipe.id like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return (Recipe) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Recipe> findByNameContaing(String word)
    {
        String queryString = "SELECT recipe FROM Recipe recipe "
                + "LEFT JOIN FETCH recipe.usedIngredients usedIngredients "
                + "LEFT JOIN FETCH usedIngredients.ingredient ingredient "
                + "WHERE recipe.name like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, "%" + word + "%");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Recipe> findAll()
    {
        String queryString = "SELECT DISTINCT(recipe) FROM Recipe recipe "
                + "LEFT JOIN FETCH recipe.usedIngredients usedIngredients "
                + "LEFT JOIN FETCH usedIngredients.ingredient ingredient ";
        Query query = createQuery(queryString);
        return query.getResultList();
    }

    @Override
    public List<Recipe> findByUserName(String name)
    {
        String queryString = "SELECT DISTINCT(recipe) FROM Recipe recipe "
                + "LEFT JOIN FETCH recipe.usedIngredients usedIngredients "
                + "LEFT JOIN FETCH usedIngredients.ingredient ingredient "
                + "WHERE recipe.createdBy like ?1";
        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return query.getResultList();
    }

}
