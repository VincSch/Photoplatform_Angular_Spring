/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface UserDAO extends UserDetailsService, GenericDAO<User> {

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
    User findByUserName(final String userName);

    /**
     * Find user by specified email.
     *
     * @param email
     *            the email
     *
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Returns all user by role.
     *
     * @param role
     *            role entity
     * @return all users by role
     */
    List<User> findByRole(final Role role);

    /**
     * Returns all user between start and count.
     * IF start and count contains 0, than return first user.
     * IF start contains 0 and count contains 1, than return first user.
     *
     * @param start index for first.
     * @param count index for last.
     *
     * @return users
     */
    List<User> find(Integer start, Integer count);

    /**
     * Returns all user by role id.
     *
     * @param roleId
     *            role id
     * @return all users by role id
     */
    List<User> findByRoleId(final Long roleId);

    /**
     * Returns all not admin users.
     *
     * @return all not admin users
     */
    List<User> findAllNotAdminUsers();

    /**
     * Returns not admin users. If enabled is true, than all enabled not admin
     * users else otherwise.
     *
     * @param enabled
     *            true, false
     * @return not admin users
     */
    List<User> findByEnabled(boolean enabled);

    /**
     * Returns not admin users. If locked is true, than all not admin users
     * which account is not locked else otherwise
     *
     * @param locked
     *            true, false
     * @return not admin users
     */
    List<User> findByAccountLocked(boolean locked);
}
