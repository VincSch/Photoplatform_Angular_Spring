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

import de.htw.sdf.photoplatform.persistence.models.UsedRecipe;
import de.htw.sdf.photoplatform.repository.UsedRecipeDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for receipt book recipe mappings.
 * 
 * @author Vincent Schwarzer
 * 
 */
@Repository
@Transactional
public class UsedRecipeDAOImpl extends GenericDAOImpl<UsedRecipe> implements UsedRecipeDAO
{

    /**
     * Used recipe DAO constructor.
     */
    public UsedRecipeDAOImpl()
    {
        super();
        setClazz(UsedRecipe.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UsedRecipe> findByRecipeId(Long id)
    {
        String queryString = "SELECT usedRecipe FROM RecipeBookHasRecipeDAO usedRecipe "
                + "LEFT JOIN FETCH usedRecipe.recipeBook recipeBook "
                + "LEFT JOIN FETCH usedRecipe.recipe recipe " + "WHERE recipe.id = ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UsedRecipe> findByRecipeBookId(Long id)
    {
        String queryString = "SELECT usedRecipe FROM RecipeBookHasRecipeDAO usedRecipe "
                + "LEFT JOIN FETCH usedRecipe.recipeBook recipeBook "
                + "LEFT JOIN FETCH usedRecipe.recipe recipe " + "WHERE recipeBook.id = ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsedRecipe findByRecipeBookAndRecipeId(Long recipeBookId, Long recipeId)
    {
        String queryString = "SELECT usedRecipe FROM RecipeBookHasRecipeDAO usedRecipe "
                + "LEFT JOIN FETCH usedRecipe.recipeBook recipeBook "
                + "LEFT JOIN FETCH usedRecipe.recipe recipe "
                + "WHERE recipeBook.id = ?1 AND recipe.id = ?2";

        Query query = createQuery(queryString);
        query.setParameter(1, recipeBookId);
        query.setParameter(2, recipeId);
        return (UsedRecipe) query.getSingleResult();
    }
}
