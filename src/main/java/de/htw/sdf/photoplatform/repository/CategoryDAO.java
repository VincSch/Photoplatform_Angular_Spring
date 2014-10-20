package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.Category;
import de.htw.sdf.photoplatform.persistence.Ingredient;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for categories
 * 
 * @author Vincent Schwarzer
 * 
 */
public interface CategoryDAO extends GenericDAO<Category> {

	/**
	 * find a category by its unique name
	 * 
	 * @param name
	 *            unique name
	 * @return the category entity
	 */
	Category findByName(String name);
}
