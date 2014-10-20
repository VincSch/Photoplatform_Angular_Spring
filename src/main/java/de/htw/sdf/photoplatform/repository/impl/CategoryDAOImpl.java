package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.Category;
import de.htw.sdf.photoplatform.repository.CategoryDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for categories
 * 
 * @author Vincent Schwarzer
 * 
 */
@Repository
@Transactional
public class CategoryDAOImpl extends GenericDAOImpl<Category> implements
		CategoryDAO {

	public CategoryDAOImpl() {
		super();
		setClazz(Category.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Category findByName(String name) {
		String queryString = "SELECT category FROM Category category "
				+ "WHERE category.name like ?1";

		Query query = createQuery(queryString);
		query.setParameter(1, name);
		return (Category) query.getSingleResult();
	}

}
