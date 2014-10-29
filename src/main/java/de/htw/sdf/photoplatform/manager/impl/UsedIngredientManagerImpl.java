/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.UsedIngredientManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.UsedIngredient;

/**
 * business methods for used ingredients.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Service
@Transactional
public class UsedIngredientManagerImpl extends DAOReferenceCollector implements
        UsedIngredientManager
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(UsedIngredient entity)
    {
        recipeUsesIngredientsDAO.create(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsedIngredient update(UsedIngredient entity)
    {
        return recipeUsesIngredientsDAO.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UsedIngredient entity)
    {
        recipeUsesIngredientsDAO.delete(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsedIngredient findById(long id)
    {
        return recipeUsesIngredientsDAO.findOne(id);
    }

    @Override
    public List<UsedIngredient> findAll()
    {
        return recipeUsesIngredientsDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll()
    {
        recipeUsesIngredientsDAO.deleteAll();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UsedIngredient> findByRecipeId(Long id)
    {
        return recipeUsesIngredientsDAO.findByRecipeId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UsedIngredient> findByIngredientId(Long id)
    {
        return findByIngredientId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsedIngredient findByIngredientAndRecipeId(
            Long ingredientId,
            Long recipeId)
    {
        return findByIngredientAndRecipeId(ingredientId, recipeId);
    }

}
