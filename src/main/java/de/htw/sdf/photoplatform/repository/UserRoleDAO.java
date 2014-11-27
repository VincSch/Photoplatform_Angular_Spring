/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.UserRole;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 *
 */
public interface UserRoleDAO extends GenericDAO<UserRole> {

    /**
     * find a user role mapping by a user id.
     *
     * @param id unique user id
     * @return list of UserRole objects
     */
    List<UserRole> findByUserId(final Long id);

    /**
     * find a user role mapping by user and role id.
     *
     * @param userId   unique user id
     * @param recipeId unique role id
     * @return list of UserRole object
     */
    List<UserRole> findByUserAndRoleId(final Long userId, final Long recipeId);
}
