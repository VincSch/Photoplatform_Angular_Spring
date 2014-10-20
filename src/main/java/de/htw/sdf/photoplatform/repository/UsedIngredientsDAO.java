package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.UsedIngredient;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for recipe ingredient mappings
 * 
 * @author Vincent Schwarzer
 * 
 */
public interface UsedIngredientsDAO extends
		GenericDAO<UsedIngredient> {

	/**
	 * find a receipt ingredient mapping by a recipe id
	 * 
	 * @param id
	 *            unique recipe id
	 * @return list of RecipeUsesIngredient objects
	 */
	List<UsedIngredient> findByRecipeId(Long id);

	/**
	 * find a receipt ingredient mapping by a ingredient id
	 * 
	 * @param id
	 *            unique ingredient id
	 * @return list of RecipeUsesIngredient objects
	 */
	List<UsedIngredient> findByIngredientId(Long id);

	/**
	 * find a receipt ingredient mapping by a ingredient id
	 * 
	 * @param id
	 *            unique ingredient id
	 * @param id
	 *            unique recipeId id
	 * @return list of RecipeUsesIngredient objects
	 */
	UsedIngredient findByIngredientAndRecipeId(Long ingredientId,
			Long recipeId);

}
