package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.Ingredient;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for ingredients
 * @author Vincent Schwarzer
 *
 */
public interface IngredientDAO extends GenericDAO<Ingredient>{
	
	/**
	 * find a ingredient by its unique name
	 * @param name unique name
	 * @return the ingredient entity 
	 */
	Ingredient findByName(String name);

}
