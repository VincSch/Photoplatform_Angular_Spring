/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.UsedIngredient;

/**
 * Interface defining business methods for recipe ingredient mapping.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
public interface UsedIngredientManager
{

    /**
     * persist a recipe ingredient mapping.
     * 
     * @param entity
     *            ingredient mapping
     */
    void create(final UsedIngredient entity);

    /**
     * update a recipe ingredient mapping.
     * 
     * @param entity
     *            the recipe ingredient mapping you want to update
     * 
     * @return the updated recipe ingredient mapping
     */
    UsedIngredient update(final UsedIngredient entity);

    /**
     * delete a recipe ingredient mapping.
     * 
     * @param entity
     *            ingredient mapping to be deleted
     */
    void delete(final UsedIngredient entity);

    /**
     * find recipe ingredient mapping by its id.
     * 
     * @param id
     *            recipe ingredient mapping id
     *
     * @return recipe ingredient mapping class
     */
    UsedIngredient findById(final long id);

    /**
     * find all recipe ingredient mappings.
     * 
     * @return a list of all recipes
     */
    List<UsedIngredient> findAll();

    /**
     * delete all recipe ingredient mappings.
     */
    void deleteAll();

    /**
     * find a receipt ingredient mapping by a recipe id.
     * 
     * @param id
     *            unique recipe id
     * 
     * @return list of RecipeUsesIngredient objects
     */
    List<UsedIngredient> findByRecipeId(Long id);

    /**
     * find a receipt ingredient mapping by a ingredient id.
     * 
     * @param id
     *            unique ingredient id
     * 
     * @return list of RecipeUsesIngredient objects
     */
    List<UsedIngredient> findByIngredientId(Long id);

    /**
     * find a receipt ingredient mapping by a ingredient id.
     * 
     * @param ingredientId
     *            unique ingredient id
     * @param recipeId
     *            unique recipeId id
     * 
     * @return list of RecipeUsesIngredient objects
     */
    UsedIngredient findByIngredientAndRecipeId(Long ingredientId, Long recipeId);
}
