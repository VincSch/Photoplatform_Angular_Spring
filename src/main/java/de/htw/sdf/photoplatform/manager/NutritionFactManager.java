/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.HashMap;
import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.NutritionFact;
import de.htw.sdf.photoplatform.persistence.models.UsedIngredient;

/**
 * Interface defining business methods for nutrition facts.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface NutritionFactManager
{

    /**
     * persist a nutrition fact.
     * 
     * @param entity
     *            nutrition fact
     */
    void create(final NutritionFact entity);

    /**
     * update an nutrition fact.
     * 
     * @param entity
     *            the nutrition fact you want to update
     * 
     * @return the updated nutrition fact
     */
    NutritionFact update(final NutritionFact entity);

    /**
     * delete an nutrition fact.
     * 
     * @param entity
     *            fact to be deleted
     */
    void delete(final NutritionFact entity);

    /**
     * find an nutrition fact by its id.
     * 
     * @param id
     *            nutrition fact id
     * 
     * @return nutrition fact class
     */
    NutritionFact findById(final long id);

    /**
     * find all nutrition fact.
     * 
     * @return a list of all nutrition fact
     */
    List<NutritionFact> findAll();

    /**
     * find a nutrition fact fact by a nutrition fact id.
     * 
     * @param id
     *            unique id
     * @return the nutrition fact
     * 
     */
    NutritionFact findByIngredientId(Long id);

    /**
     * calculate calories for used ingredients.
     * 
     * @param usedIngredients
     *            list of used ingredients
     * 
     * @return the calories
     */
    HashMap<String, Double> calculateCalories(List<UsedIngredient> usedIngredients);
}
