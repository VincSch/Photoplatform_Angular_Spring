package de.htw.sdf.photoplatform.repository.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

/**
 * Generic abstract data access interface defining the most important functions.
 * 
 * @author Vincent Schwarzer
 * 
 * @param <T>
 *            the corresponding entity class
 */
public interface GenericDAO<T extends Serializable> {

	/**
	 * set entity class to be used
	 * 
	 * @param clazzToSet
	 *            entity class to use
	 */
	void setClazz(final Class<T> clazzToSet);

	/**
	 * find an entity by its id
	 * 
	 * @param id
	 *            entity id
	 * @return entity class
	 */
	T findOne(final long id);

	/**
	 * find all entities
	 * 
	 * @return a list of all entities
	 */
	List<T> findAll();

	/**
	 * persist an entity
	 * 
	 * @param entity
	 */
	void create(final T entity);

	/**
	 * update an entity
	 * 
	 * @param entity
	 *            the entity you want to update
	 * @return the updated entity
	 */
	T update(final T entity);

	/**
	 * delete an entity
	 * 
	 * @param entity
	 *            to be deleted
	 */
	void delete(final T entity);

	/**
	 * delete all entites
	 * 
	 */
	void deleteAll();

	/**
	 * delete an entity identified by its id
	 * 
	 * @param entityId
	 *            id of the entity you want to delete
	 */
	void deleteById(final long entityId);

	/**
	 * creates a JPA query out of a given query string without exposing the
	 * entity manager.
	 * 
	 * @param queryString
	 *            your query string
	 * @return a {@link javax.persistence.Query} object
	 */
	Query createQuery(String queryString);
}