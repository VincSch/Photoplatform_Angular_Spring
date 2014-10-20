package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.RecipeBookManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.RecipeBook;
import de.htw.sdf.photoplatform.persistence.UsedRecipe;

@Service
@Transactional
public class RecipeBookManagerImpl extends DAOReferenceCollector implements
		RecipeBookManager {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(RecipeBook entity) {
		recipeBookDAO.create(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RecipeBook update(RecipeBook entity) {
		return recipeBookDAO.update(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(RecipeBook entity) {
		recipeBookDAO.delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RecipeBook findById(long id) {
		return recipeBookDAO.findOne(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RecipeBook> findAll() {
		return recipeBookDAO.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RecipeBook findByName(String name) {
		return recipeBookDAO.findByName(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		recipeBookDAO.deleteAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRecipe(UsedRecipe usedRecipes) {
		recipeBookHasRecipeDAO.create(usedRecipes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteRecipe(UsedRecipe usedRecipes) {
		recipeBookHasRecipeDAO.delete(usedRecipes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RecipeBook> findByUserName(String name) {
		return recipeBookDAO.findByUserName(name);
	}

}
