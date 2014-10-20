package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.UsedRecipeManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.UsedRecipe;

/**
 * business methods for recipe book recipe mappings
 * 
 * @author Vincent Schwarzer
 * 
 */
@Service
@Transactional
public class UsedRecipeManagerImpl extends DAOReferenceCollector
		implements UsedRecipeManager {

	@Override
	public void create(UsedRecipe entity) {
		recipeBookHasRecipeDAO.create(entity);

	}

	@Override
	public UsedRecipe update(UsedRecipe entity) {
		return recipeBookHasRecipeDAO.update(entity);
	}

	@Override
	public void delete(UsedRecipe entity) {
		recipeBookHasRecipeDAO.delete(entity);
	}

	@Override
	public UsedRecipe findById(long id) {
		return recipeBookHasRecipeDAO.findOne(id);
	}

	@Override
	public List<UsedRecipe> findAll() {
		return recipeBookHasRecipeDAO.findAll();
	}

	@Override
	public void deleteAll() {
		recipeBookHasRecipeDAO.deleteAll();
	}

	@Override
	public List<UsedRecipe> findByRecipeId(Long id) {
		return recipeBookHasRecipeDAO.findByRecipeId(id);
	}

	@Override
	public List<UsedRecipe> findByRecipeBookId(Long id) {
		return recipeBookHasRecipeDAO.findByRecipeBookId(id);
	}

	@Override
	public UsedRecipe findByRecipeBookAndRecipeId(Long recipeBookId,
			Long recipeId) {
		return recipeBookHasRecipeDAO.findByRecipeBookAndRecipeId(recipeBookId,
				recipeId);
	}

}
