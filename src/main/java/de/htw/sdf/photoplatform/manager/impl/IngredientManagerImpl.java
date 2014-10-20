package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.IngredientManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.Ingredient;

/**
 * business methods for ingredients
 * 
 * @author Vincent Schwarzer
 * 
 */
@Service
@Transactional
public class IngredientManagerImpl extends DAOReferenceCollector implements
		IngredientManager {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Ingredient entity) {
		ingredientDAO.create(entity);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Ingredient entity) {
		ingredientDAO.delete(entity);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ingredient findById(long id) {
		return ingredientDAO.findOne(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ingredient> findAll() {
		return ingredientDAO.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ingredient findByName(String name) {
		return ingredientDAO.findByName(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ingredient update(Ingredient entity) {
		return ingredientDAO.update(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		ingredientDAO.deleteAll();		
	}

}
