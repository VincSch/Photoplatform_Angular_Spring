/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.controller;

import org.springframework.stereotype.Controller;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;

/**
*
*/
@Controller
public class RecipeController extends BaseAPIController
{
    //
    // @Autowired
    // RecipeManager recipeManager;
    //
    // /**
    // * Recipe by name.
    // *
    // * @param name
    // * the name
    // *
    // * @return the recipe
    // */
    // @RequestMapping(value = Endpoints.RECIPE_BY_NAME, method = RequestMethod.GET)
    // @ResponseBody
    // public Recipe recipeByName(@PathVariable String name)
    // {
    // Recipe recipe = recipeManager.findByName(name);
    // return recipe;
    // }
    //
    // /**
    // * Recipe by Id.
    // *
    // * @param id
    // * the id
    // *
    // * @return the recipe
    // */
    // @RequestMapping(value = Endpoints.RECIPE_BY_ID, method = RequestMethod.GET)
    // @ResponseBody
    // public Recipe recipeById(@PathVariable int id)
    // {
    // Recipe recipe = recipeManager.findById(id);
    // return recipe;
    // }
    //
    // /**
    // * Recipe by name.
    // *
    // * @param name
    // * the name
    // *
    // * @return the recipe
    // */
    // @RequestMapping(value = Endpoints.RECIPE_BY_USERNAME, method = RequestMethod.GET)
    // @ResponseBody
    // public List<Recipe> recipeByUserName(@PathVariable String name)
    // {
    // return recipeManager.findByUserName(name);
    // }
    //
    // /**
    // * Retrieve all recipes.
    // *
    // * @return a list of recipes
    // *
    // * @throws NotFoundException
    // * the exception
    // */
    // @RequestMapping(value = Endpoints.RECIPE_ALL, method = RequestMethod.GET)
    // @ResponseBody
    // public List<Recipe> retrieveAllRecipes() throws NotFoundException
    // {
    // List<Recipe> recipeList = recipeManager.findAll();
    // if (recipeList == null)
    // {
    // throw new NotFoundException("No recipes found!");
    // }
    // else
    // {
    // return recipeList;
    // }
    // }
    //
    // /**
    // * Delete recipe by name.
    // *
    // * @param name
    // * the name
    // *
    // * @return true if delete was successfully
    // */
    // @RequestMapping(value = Endpoints.RECIPE_DELETE_BY_NAME, method = RequestMethod.GET)
    // @ResponseBody
    // public boolean deleteRecipeByName(@PathVariable String name)
    // {
    // Recipe recipeToDelete = recipeManager.findByName(name);
    // recipeManager.delete(recipeToDelete);
    // return true;
    // }
    //
    // /**
    // * Create recipe.
    // *
    // * @param recipe
    // * the recipe
    // *
    // * @return the recipe
    // */
    // @RequestMapping(value = Endpoints.RECIPE_CREATE, method = RequestMethod.POST)
    // @ResponseBody
    // @ResponseStatus(HttpStatus.CREATED)
    // public Recipe createRecipe(@RequestBody Recipe recipe)
    // {
    // recipeManager.create(recipe);
    // return recipe;
    // }
    //
    // /**
    // * Update recipe.
    // *
    // * @param id
    // * the id
    // * @param recipe
    // * the recipe
    // *
    // * @return true if update was successfully
    // */
    // @RequestMapping(value = Endpoints.RECIPE_UPDATE, method = RequestMethod.PUT)
    // @ResponseBody
    // @ResponseStatus(HttpStatus.OK)
    // public boolean updateRecipe(@PathVariable long id, @RequestBody Recipe recipe)
    // {
    // recipeManager.update(recipe);
    // return true;
    // }
    //
    // /**
    // * Retrieve all recipes difficulties.
    // *
    // * @return list of difficulties
    // *
    // * @throws Exception
    // * the exception
    // */
    // @RequestMapping(value = Endpoints.RECIPE_DIFFICULTY_ALL, method = RequestMethod.GET)
    // @ResponseBody
    // public List<RecipeDifficulty> retrieveAllRecipeDifficulties() throws Exception
    // {
    // List<RecipeDifficulty> recipeDifficultyList = recipeManager.findAllRecipeDifficulties();
    // if (recipeDifficultyList == null)
    // {
    // throw new Exception("No difficulties found!");
    // }
    // else
    // {
    // return recipeDifficultyList;
    // }
    // }
    //
    // /**
    // * Remove unused ingredient by name.
    // *
    // * @param usedIngredient
    // * the usedIngredient
    // *
    // * @return true if remove was successfully
    // */
    // @RequestMapping(value = Endpoints.RECIPE_DELETE_BY_NAME, method = RequestMethod.POST)
    // @ResponseBody
    // public boolean removeUsedIngredientByName(@RequestBody UsedIngredient usedIngredient)
    // {
    // recipeManager.deleteUsedIngredient(usedIngredient);
    // return true;
    // }
    //
    // /**
    // * Add used ingredient by name.
    // *
    // * @param param
    // * the param
    // *
    // * @return true if add was successfully
    // *
    // * @throws JsonProcessingException
    // * the exception
    // * @throws IOException
    // * the exception
    // */
    // @RequestMapping(value = Endpoints.RECIPE_ADD_INGREDIENT, method = RequestMethod.POST)
    // @ResponseBody
    // public boolean addUsedIngredientByName(@RequestBody String param)
    // throws JsonProcessingException, IOException
    // {
    // ObjectMapper mapper = new ObjectMapper();
    // JsonNode node = mapper.readTree(param);
    // UsedIngredient usedIngredient = new UsedIngredient();
    // usedIngredient.setAmount(mapper.convertValue(node.get("webAmount"), Double.class));
    // usedIngredient.setUnit(mapper.convertValue(node.get("webUnit"), Unit.class));
    // usedIngredient.setRecipe(mapper.convertValue(node.get("webRecipe"), Recipe.class));
    // usedIngredient.setIngredient(mapper.convertValue(
    // node.get("webIngredient"),
    // Ingredient.class));
    //
    // recipeManager.addIngredient(usedIngredient);
    // return true;
    // }
}
