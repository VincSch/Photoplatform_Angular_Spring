/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import de.htw.sdf.photoplatform.persistence.models.user.Role;
import de.htw.sdf.photoplatform.persistence.models.user.User;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface UserDAO extends UserDetailsService, GenericDAO<User>
{

    /**
     * {@inheritDoc}
     */
    @Override
    void create(User defaultUser);

    /**
     * find a user by its unique name.
     * 
     * @param userName
     *            unique name
     * 
     * @return the user entity
     */
    User findByUserName(String userName);

    /**
     * Returns all user by role.
     *
     * @param role
     *            role entity
     * @return all users by role
     */
    List<User> findByRole(Role role);

    /**
     * Returns all user by role id.
     *
     * @param roleId
     *            role id
     * @return all users by role id
     */
    List<User> findByRoleId(Long roleId);

    /**
     * Returns all not admin users.
     *
     * @return all not admin users
     */
    List<User> findAllNotAdminUsers();

    /**
     * Returns not admin users. If enabled is true, than all enabled not admin users else otherwise.
     *
     * @param enabled
     *            true, false
     * @return not admin users
     */
    List<User> findByEnabled(boolean enabled);

    /**
     * Returns not admin users. If locked is true, than all not admin users which account is not
     * locked else otherwise
     *
     * @param locked
     *            true, false
     * @return not admin users
     */
    List<User> findByAccountLocked(boolean locked);

    /**
     * find a number of recipes belonging to this user.
     * 
     * @param userName
     *            unique name
     * 
     * @return amount of recipes
     */
    Long getRecipeAmount(String userName);

    /**
     * find a number of recipe books belonging to this user.
     * 
     * @param userName
     *            unique name
     * 
     * @return amount of recipe books
     */
    Long getRecipeBookAmount(String userName);
}
