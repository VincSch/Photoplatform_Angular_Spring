package de.htw.sdf.photoplatform.repository.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.UsedIngredient;
import de.htw.sdf.photoplatform.repository.UsedIngredientsDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for receipts ingredient mappings
 * 
 * @author Vincent Schwarzer
 * 
 */
@Repository
@Transactional
public class UsedIngredientDAOImpl extends GenericDAOImpl<UsedIngredient>
		implements UsedIngredientsDAO {

	public UsedIngredientDAOImpl() {
		super();
		setClazz(UsedIngredient.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UsedIngredient> findByRecipeId(Long id) {
		String queryString = "SELECT usedIngredient FROM UsedIngredient usedIngredient "
				+ "LEFT JOIN FETCH usedIngredient.ingredient ingredient "
				+ "LEFT JOIN FETCH usedIngredient.recipe recipe "
				+ "WHERE recipe.id = ?1";

		Query query = createQuery(queryString);
		query.setParameter(1, id);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UsedIngredient> findByIngredientId(Long id) {
		String queryString = "SELECT usedIngredient FROM UsedIngredient usedIngredient "
				+ "LEFT JOIN FETCH usedIngredient.ingredient ingredient "
				+ "LEFT JOIN FETCH usedIngredient.recipe recipe "
				+ "WHERE ingredient.id = ?1";

		Query query = createQuery(queryString);
		query.setParameter(1, id);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UsedIngredient findByIngredientAndRecipeId(Long ingredientId,
			Long recipeId) {
		String queryString = "SELECT usedIngredient FROM UsedIngredient usedIngredient "
				+ "LEFT JOIN FETCH usedIngredient.ingredient ingredient "
				+ "LEFT JOIN FETCH usedIngredient.recipe recipe "
				+ "WHERE ingredient.id = ?1 AND recipe.id = ?2";

		Query query = createQuery(queryString);
		query.setParameter(1, ingredientId);
		query.setParameter(2, recipeId);
		return (UsedIngredient) query.getSingleResult();
	}
}
