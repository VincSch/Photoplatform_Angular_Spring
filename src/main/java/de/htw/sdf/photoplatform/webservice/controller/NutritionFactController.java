/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.htw.sdf.photoplatform.manager.IngredientManager;
import de.htw.sdf.photoplatform.manager.NutritionFactManager;
import de.htw.sdf.photoplatform.manager.UnitManager;
import de.htw.sdf.photoplatform.persistence.models.NutritionFact;
import de.htw.sdf.photoplatform.persistence.models.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.persistence.models.UsedIngredient;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;

/**
 *
 */
@Controller
public class NutritionFactController extends BaseAPIController
{

    @Autowired
    NutritionFactManager nutritionFactManager;

    @Autowired
    UnitManager unitManager;

    @Autowired
    IngredientManager ingredientManager;

    /**
     * Recipe book by name.
     * 
     * @param id
     *            the id
     * 
     * @return book by name
     * 
     * @throws Exception
     *             the exception
     */
    @RequestMapping(value = Endpoints.NUTRITION_BY_INGREDIENT, method = RequestMethod.GET)
    @ResponseBody
    public NutritionFact recipeBookByName(@PathVariable Long id) throws Exception
    {
        NutritionFact fact = nutritionFactManager.findByIngredientId(id);
        return fact;
    }

    /**
     * Get calroiesForUsedIngredients.
     * 
     * @param usedIngredients
     *            the usedIngredients
     * 
     * @return the calroiesForUsedIngredients
     * 
     * @throws Exception
     *             the exception
     */
    @RequestMapping(value = Endpoints.CALORIES_FOR_USEDINGREDIENTS, method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, Double> calroiesForUsedIngredients(
            @RequestBody List<UsedIngredient> usedIngredients) throws Exception
    {
        return nutritionFactManager.calculateCalories(usedIngredients);
    }

    /**
     * Create Recipe.
     * 
     * @param fact
     *            the fact
     * 
     * @return the nutrition fact
     */
    @RequestMapping(value = Endpoints.NUTRITIONFACT_CREATE, method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public NutritionFact createRecipe(@RequestBody NutritionFact fact)
    {
        fact.setUnit(unitManager.findByName(GermanUnitName.g));
        fact.setIngredient(ingredientManager.findByName(fact.getIngredient().getName()));
        nutritionFactManager.create(fact);
        return fact;
    }

    /**
     * Update recipe.
     * 
     * @param nutrition
     *            the nutrition
     * 
     * @return true if update was successfully
     */
    @RequestMapping(value = Endpoints.NUTRITIONFACT_UPDATE, method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public boolean updateRecipe(@RequestBody NutritionFact nutrition)
    {
        nutritionFactManager.update(nutrition);
        return true;
    }
}
