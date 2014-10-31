/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.Ingredient;

/**
 * Interface defining business methods for ingredients.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface IngredientManager
{

    /**
     * persist an ingredient.
     * 
     * @param entity
     *            ingredient
     */
    void create(final Ingredient entity);

    /**
     * update an ingredient.
     * 
     * @param entity
     *            the ingredient you want to update
     * 
     * @return the updated ingredient
     */
    Ingredient update(final Ingredient entity);

    /**
     * delete an ingredient.
     * 
     * @param entity
     *            ingredient to be deleted
     */
    void delete(final Ingredient entity);

    /**
     * find an ingredient by its id.
     * 
     * @param id
     *            ingredient id
     * 
     * @return ingredient class
     */
    Ingredient findById(final long id);

    /**
     * find all ingredients.
     * 
     * @return a list of all ingredients
     */
    List<Ingredient> findAll();

    /**
     * find a ingredient by its unique name.
     * 
     * @param name
     *            unique name
     * @return the ingredient entity
     */
    Ingredient findByName(String name);

    /**
     * delete all ingredients.
     * 
     */
    void deleteAll();
}
