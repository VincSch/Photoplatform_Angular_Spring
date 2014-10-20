package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import de.htw.sdf.photoplatform.manager.CategoryManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.Category;

public class CategoryManagerImpl extends DAOReferenceCollector implements
		CategoryManager {

	@Override
	public void create(Category entity) {
		categoryDAO.create(entity);
	}

	@Override
	public Category update(Category entity) {
		return categoryDAO.update(entity);
	}

	@Override
	public void delete(Category entity) {
		categoryDAO.delete(entity);
	}

	@Override
	public Category findById(long id) {
		return categoryDAO.findOne(id);
	}

	@Override
	public List<Category> findAll() {
		return categoryDAO.findAll();
	}

	@Override
	public Category findByName(String name) {
		return categoryDAO.findByName(name);
	}

	@Override
	public void deleteAll() {
		categoryDAO.deleteAll();
	}

}
