/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.Ingredient;
import de.htw.sdf.photoplatform.persistence.Recipe;
import de.htw.sdf.photoplatform.persistence.RecipeDifficulty;
import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.persistence.UsedIngredient;

/**
 * Interface defining business methods for recipes.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface RecipeManager
{

    /**
     * find a recipes created by a specified user.
     * 
     * @param name
     *            unique user name
     * 
     * @return the recipe entity
     */
    List<Recipe> findByUserName(String name);

    /**
     * persist a recipe.
     * 
     * @param entity
     *            the recipe
     */
    void create(final Recipe entity);

    /**
     * update a recipe.
     * 
     * @param entity
     *            the recipe you want to update
     * @return the updated recipe
     */
    Recipe update(final Recipe entity);

    /**
     * delete a recipe.
     * 
     * @param entity
     *            to be deleted
     */
    void delete(final Recipe entity);

    /**
     * find recipe by its id.
     * 
     * @param id
     *            recipe id
     * @return recipe class
     */
    Recipe findById(final long id);

    /**
     * find all recipes.
     * 
     * @return a list of all recipes
     */
    List<Recipe> findAll();

    /**
     * find a recipe by its unique name.
     * 
     * @param name
     *            unique name
     * 
     * @return the recipe entity
     */
    Recipe findByName(String name);

    /**
     * delete all recipes.
     */
    void deleteAll();

    /**
     * delete the recipe and the used ingredients.
     * 
     * @param entity
     *            recipe with fetched ingredients
     */
    void deleteEntityAndUsedIngredients(Recipe entity);

    /**
     * remove a specific ingredient from the recipe.
     * 
     * @param usedIngredient
     *            used ingredient to remove
     */
    void deleteUsedIngredient(UsedIngredient usedIngredient);

    /**
     * remove all ingredients from the recipe.
     * 
     * @param recipe
     *            the recipe
     * @return updated Recipe entity
     */
    Recipe deleteAllUsedIngredients(Recipe recipe);

    /**
     * remove a specific list of used Ingredients from the recipe.
     * 
     * @param usedIngredients
     *            used ingredient to remove
     * 
     * @return updated Recipe entity
     */
    Recipe deleteListOfUsedIngredients(List<UsedIngredient> usedIngredients);

    /**
     * Add ingredient.
     * 
     * @param recipe
     *            the recipe you want to add an ingredient to.
     * @param ingredient
     *            ingredient to add
     * @param amount
     *            how much of this ingredient is going to be used
     * @param unit
     *            the unit of this ingredient
     * 
     * @return updated Recipe entity
     */
    Recipe addIngredient(
            Recipe recipe,
            Ingredient ingredient,
            double amount,
            Unit unit);

    /**
     * Add ingredient.
     * 
     * @param recipesIngredient
     *            the recipe uses ingredient object you want to add
     * 
     * @return updated Recipe entity
     */
    Recipe addIngredient(UsedIngredient recipesIngredient);

    /**
     * delete the recipe and the used ingredients recipesIngredients list of
     * RecipeUsesIngredients entity.
     * 
     * @param recipesIngredients
     *            the recipes
     * 
     * @return updated Recipe entity
     */
    Recipe addIngredients(List<UsedIngredient> recipesIngredients);

    /**
     * get all recipe difficulties.
     * 
     * @return list of recipe difficulties
     */
    List<RecipeDifficulty> findAllRecipeDifficulties();
}
