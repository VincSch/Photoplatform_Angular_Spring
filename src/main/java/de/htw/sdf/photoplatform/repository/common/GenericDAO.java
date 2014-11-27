/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.common;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Generic abstract data access interface defining the most important functions.
 *
 * @param <T> the corresponding entity class
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface GenericDAO<T extends Serializable> {

    /**
     * set entity class to be used.
     *
     * @param clazzToSet entity class to use
     */
    void setClazz(final Class<T> clazzToSet);

    /**
     * find an entity by its id.
     *
     * @param id entity id
     * @return entity class
     */
    T findOne(final long id);

    /**
     * find all entities.
     *
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * persist an entity.
     *
     * @param entity the entity
     */
    void create(final T entity);

    /**
     * update an entity.
     *
     * @param entity the entity you want to update
     * @return the updated entity
     */
    T update(final T entity);

    /**
     * delete an entity.
     *
     * @param entity to be deleted
     */
    void delete(final T entity);

    /**
     * delete all entities.
     */
    void deleteAll();

    /**
     * delete an entity identified by its id.
     *
     * @param entityId id of the entity you want to delete
     */
    void deleteById(final long entityId);

    /**
     * creates a JPA query out of a given query string without exposing the
     * entity manager.
     *
     * @param queryString your query string
     * @return a {@link javax.persistence.Query} object
     */
    Query createQuery(String queryString);
}
