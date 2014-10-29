/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.UsedRecipe;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for recipe book has ingredients.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface UsedRecipeDAO extends GenericDAO<UsedRecipe>
{

    /**
     * find a recipe book recipe mapping by a recipe id.
     * 
     * @param id
     *            unique recipe id
     * @return list of RecipeUsesIngredient objects
     */
    List<UsedRecipe> findByRecipeId(Long id);

    /**
     * find a recipe book recipe mapping by a recipe book id.
     * 
     * @param id
     *            unique recipe book id
     * @return list of RecipeBookHasRecipe objects
     */
    List<UsedRecipe> findByRecipeBookId(Long id);

    /**
     * find a receipt ingredient mapping by a ingredient id.
     * 
     * @param recipeBookId
     *            unique recipe book id
     * @param recipeId
     *            unique recipe id
     * @return list of RecipeBookHasRecipe objects
     */
    UsedRecipe findByRecipeBookAndRecipeId(Long recipeBookId, Long recipeId);

}
