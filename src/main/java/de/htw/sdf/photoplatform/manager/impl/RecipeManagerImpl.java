/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.RecipeManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.models.Ingredient;
import de.htw.sdf.photoplatform.persistence.models.Recipe;
import de.htw.sdf.photoplatform.persistence.models.RecipeDifficulty;
import de.htw.sdf.photoplatform.persistence.models.Unit;
import de.htw.sdf.photoplatform.persistence.models.UsedIngredient;

/**
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Service
@Transactional
public class RecipeManagerImpl extends DAOReferenceCollector implements RecipeManager
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Recipe entity)
    {
        recipeDAO.create(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Recipe entity)
    {
        recipeDAO.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe findById(long id)
    {
        return recipeDAO.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Recipe> findAll()
    {
        return recipeDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe findByName(String name)
    {
        return recipeDAO.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe update(Recipe entity)
    {
        return recipeDAO.update(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll()
    {
        recipeDAO.deleteAll();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEntityAndUsedIngredients(Recipe entity)
    {
        List<UsedIngredient> recipesIngredients = entity.getUsedIngredients();
        for (UsedIngredient recipeIngredient : recipesIngredients)
        {
            recipeUsesIngredientsDAO.delete(recipeIngredient);
        }

        recipeDAO.delete(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe addIngredient(Recipe recipe, Ingredient ingredient, double amount, Unit unit)
    {
        UsedIngredient newUsedIngredient = new UsedIngredient();
        newUsedIngredient.setAmount(amount);
        newUsedIngredient.setUnit(unit);
        newUsedIngredient.setIngredient(ingredient);
        newUsedIngredient.setRecipe(recipe);
        recipeUsesIngredientsDAO.create(newUsedIngredient);
        return recipeDAO.findOne(recipe.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe addIngredient(UsedIngredient recipesIngredient)
    {
        Recipe recipe = recipesIngredient.getRecipe();
        recipeUsesIngredientsDAO.create(recipesIngredient);
        return recipeDAO.findOne(recipe.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe addIngredients(List<UsedIngredient> recipesIngredients)
    {
        Recipe recipe = recipesIngredients.get(0).getRecipe();
        for (UsedIngredient recipeIngredient : recipesIngredients)
        {
            recipeUsesIngredientsDAO.create(recipeIngredient);
        }
        return recipeDAO.findOne(recipe.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUsedIngredient(UsedIngredient usedIngredient)
    {
        recipeUsesIngredientsDAO.delete(usedIngredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe deleteAllUsedIngredients(Recipe recipe)
    {
        List<UsedIngredient> recipesIngredients = recipe.getUsedIngredients();
        for (UsedIngredient recipeIngredient : recipesIngredients)
        {
            recipeUsesIngredientsDAO.delete(recipeIngredient);
        }

        return recipeDAO.findOne(recipe.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe deleteListOfUsedIngredients(List<UsedIngredient> usedIngredients)
    {
        Recipe recipe = usedIngredients.get(0).getRecipe();
        for (UsedIngredient recipeIngredient : usedIngredients)
        {
            recipeUsesIngredientsDAO.delete(recipeIngredient);
        }
        return recipeDAO.findOne(recipe.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RecipeDifficulty> findAllRecipeDifficulties()
    {
        return recipeDifficultyDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Recipe> findByUserName(String name)
    {
        return recipeDAO.findByUserName(name);
    }
}
