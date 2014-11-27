/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.common;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Generic abstract data access class implementing the most important functions.
 * Should be the super class of each and every other DAO class.
 *
 * @param <T> the corresponding entity class
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public abstract class GenericDAOImpl<T extends Serializable> implements
        GenericDAO<T> {

    private Class<T> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(final long id) {
        return entityManager.find(clazz, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName())
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(final T entity) {
        entityManager.persist(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(final T entity) {
        return entityManager.merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity
                : entityManager.merge(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from " + clazz.getName())
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query createQuery(String queryString) {
        return entityManager.createQuery(queryString);
    }

    /**
     * @return the entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
