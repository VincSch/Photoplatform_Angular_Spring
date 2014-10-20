package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.NutritionFact;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for recipe books
 * 
 * @author Vincent Schwarzer
 * 
 */
public interface NutritionFactDAO extends GenericDAO<NutritionFact> {

	/**
	 * find a nutrition fact by a ingredient id
	 * 
	 * @param id
	 *            unique id
	 * @return the nutrition fact
	 */
	NutritionFact findByIngredientId(Long id);
}
