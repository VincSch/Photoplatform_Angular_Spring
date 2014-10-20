package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.Recipe;
import de.htw.sdf.photoplatform.persistence.RecipeDifficulty;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for receipts difficulty
 * 
 * @author Vincent Schwarzer
 * 
 */
public interface RecipeDifficultyDAO extends GenericDAO<RecipeDifficulty> {

	/**
	 * find a receipt difficulty by its unique name
	 * 
	 * @param name
	 *            unique name
	 * @return the recipe difficulty entity
	 */
	Recipe findByName(String name);
}
