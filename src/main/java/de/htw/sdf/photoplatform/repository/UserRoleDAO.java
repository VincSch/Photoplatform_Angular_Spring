/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.user.UserRole;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 *
 */
public interface UserRoleDAO extends GenericDAO<UserRole>
{

    /**
     * find a user role mapping by a user id.
     * 
     * @param id
     *            unique user id
     * @return list of UserRole objects
     */
    List<UserRole> findByUserId(Long id);

    /**
     * find a user role mapping by a role id.
     * 
     * @param id
     *            unique role id
     * 
     * @return list of UserRole objects
     */
    List<UserRole> findByRecipeRoleId(Long id);

    /**
     * find a user role mapping by user and role id.
     * 
     * @param userId
     *            unique user id
     * @param recipeId
     *            unique role id
     * 
     * @return list of UserRole object
     */
    List<UserRole> findByUserAndRoleId(Long userId, Long recipeId);
}
