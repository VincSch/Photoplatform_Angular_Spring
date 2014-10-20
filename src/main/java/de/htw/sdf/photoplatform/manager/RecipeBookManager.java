package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.RecipeBook;
import de.htw.sdf.photoplatform.persistence.UsedRecipe;

/**
 * Interface defining business methods for recipe books
 * 
 * @author Vincent Schwarzer
 * 
 */
public interface RecipeBookManager {

	/**
	 * persist a recipe book
	 * 
	 * @param entity
	 *            recipe book
	 */
	void create(final RecipeBook entity);

	/**
	 * update a recipe book
	 * 
	 * @param entity
	 *            the recipe book you want to update
	 * @return the updated recipe book
	 */
	RecipeBook update(final RecipeBook entity);

	/**
	 * delete an recipe book
	 * 
	 * @param recipe
	 *            book to be deleted
	 */
	void delete(final RecipeBook entity);

	/**
	 * find an recipe book by its id
	 * 
	 * @param id
	 *            recipe book id
	 * @return recipe book class
	 */
	RecipeBook findById(final long id);

	/**
	 * find all recipe books
	 * 
	 * @return a list of all recipe books
	 */
	List<RecipeBook> findAll();

	/**
	 * find a recipe book by its unique name
	 * 
	 * @param name
	 *            unique name
	 * @return the recipe book entity
	 */
	RecipeBook findByName(String name);

	/**
	 * delete all recipe books
	 * 
	 */
	void deleteAll();

	/**
	 * adds a recipe to a recipe book
	 * 
	 * @param usedRecipes
	 *            RecipeBookHasRecipe entity
	 */
	void addRecipe(UsedRecipe usedRecipes);

	/**
	 * remove a recipe to a recipe book
	 * 
	 * @param usedRecipes
	 *            RecipeBookHasRecipe entity
	 */
	void deleteRecipe(UsedRecipe usedRecipes);
	
	/**
	 * find a recipe books created by a specified user
	 * 
	 * @param name
	 *            unique user name
	 * @return the recipe entity
	 */
	List<RecipeBook> findByUserName(String name);
}
