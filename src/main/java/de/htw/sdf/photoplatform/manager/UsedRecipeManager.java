/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.UsedRecipe;

/**
 * Interface defining business methods for recipe ingredient mapping.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface UsedRecipeManager
{

    /**
     * persist a recipe book recipe mapping.
     * 
     * @param entity
     *            book recipe mapping
     */
    void create(final UsedRecipe entity);

    /**
     * update a recipe book recipe mapping.
     * 
     * @param entity
     *            the recipe book recipe mapping you want to update
     * 
     * @return the updated recipe ingredient mapping
     */
    UsedRecipe update(final UsedRecipe entity);

    /**
     * delete a recipe book recipe mapping.
     * 
     * @param entity
     *            book recipe mapping to be deleted
     */
    void delete(final UsedRecipe entity);

    /**
     * find recipe book recipe mapping by its id.
     * 
     * @param id
     *            recipe book recipe mapping id
     * 
     * @return recipe book recipe mapping class
     */
    UsedRecipe findById(final long id);

    /**
     * find all recipe book recipe mappings.
     * 
     * @return a list of all recipe book recipe mappings
     */
    List<UsedRecipe> findAll();

    /**
     * delete all recipe book recipe mappings.
     * 
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
    List<UsedRecipe> findByRecipeId(Long id);

    /**
     * find a receipt ingredient mapping by a ingredient id.
     * 
     * @param id
     *            unique ingredient id
     * 
     * @return list of RecipeUsesIngredient objects
     */
    List<UsedRecipe> findByRecipeBookId(Long id);

    /**
     * find a receipt ingredient mapping by a ingredient id.
     * 
     * @param recipeBookId
     *            unique ingredient id
     * @param recipeId
     *            unique recipeId id
     * 
     * @return list of RecipeUsesIngredient objects
     */
    UsedRecipe findByRecipeBookAndRecipeId(Long recipeBookId, Long recipeId);
}
