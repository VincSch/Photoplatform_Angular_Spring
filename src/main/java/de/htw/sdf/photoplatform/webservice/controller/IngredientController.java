/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.htw.sdf.photoplatform.exception.NotFoundException;
import de.htw.sdf.photoplatform.manager.IngredientManager;
import de.htw.sdf.photoplatform.persistence.models.Ingredient;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;

/**
 * 
 */
@Controller
public class IngredientController extends BaseAPIController
{

    @Autowired
    IngredientManager ingredientManager;

    /**
     * Ingredient by name.
     * 
     * @param name
     *            the name
     * 
     * @return the ingredient
     * 
     * @throws NotFoundException
     *             the exception
     */
    @RequestMapping(value = Endpoints.INGREDIENT_BY_NAME, method = RequestMethod.GET)
    @ResponseBody
    public Ingredient ingredientByName(@PathVariable String name) throws NotFoundException
    {
        Ingredient ingredient = ingredientManager.findByName(name);
        if (ingredient == null)
        {
            throw new NotFoundException("No Ingredient found!");
        }
        else
        {
            return ingredient;
        }
    }

    /**
     * Get all ingredients.
     * 
     * @return list of ingredients
     * @throws NotFoundException
     *             the exception
     */
    @RequestMapping(value = Endpoints.INGREDIENT_ALL, method = RequestMethod.GET)
    @ResponseBody
    public List<Ingredient> retrieveAllIngredients() throws NotFoundException
    {
        List<Ingredient> ingredientList = ingredientManager.findAll();
        if (ingredientList.isEmpty())
        {
            throw new NotFoundException("No Ingredients found!");
        }
        else
        {
            return ingredientList;
        }
    }

    /**
     * Delete ingredient by name.
     * 
     * @param name
     *            the name
     * 
     * @return true if delete was successfully
     */
    @RequestMapping(value = Endpoints.INGREDIENT_DELETE_BY_NAME, method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteIngredientByName(@PathVariable String name)
    {
        Ingredient ingredientToDelete = ingredientManager.findByName(name);
        ingredientManager.delete(ingredientToDelete);
        return true;
    }

    /**
     * Create ingredient.
     * 
     * @param ingredient
     *            the ingredient
     * 
     * @return true if create was successfully
     */
    @RequestMapping(value = Endpoints.INGREDIENT_CREATE, method = RequestMethod.POST)
    @ResponseBody
    @Secured("USER")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createIngredient(@RequestBody Ingredient ingredient)
    {
        ingredientManager.create(ingredient);
        return true;
    }

    /**
     * Update recipe.
     * 
     * @param id
     *            the id
     * @param ingredient
     *            the ingredient
     * 
     * @return true if update was successfully
     */
    @RequestMapping(value = Endpoints.INGREDIENT_UPDATE, method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public boolean updateRecipe(@PathVariable long id, @RequestBody Ingredient ingredient)
    {
        ingredientManager.update(ingredient);
        return true;
    }
}