/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserBank;
import de.htw.sdf.photoplatform.persistence.model.UserProfile;

import java.util.List;

/**
 * Interface defining business methods for users.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface UserManager {

    /**
     * Register user.
     *
     * @param username
     *            the username
     * @param email
     *            the email
     * @param password
     *            the password
     *
     * @throws ManagerException
     *             the exception
     */
    void registerUser(final String username, final String email,
            final String password) throws ManagerException;

    /**
     * Update user data.
     *
     * @param entity
     *            User you want to update
     * @return the updated User
     */
    User update(final User entity);

    /**
     * Update user data.
     *
     * This method update user, userProfile and userBank. If userProfile or
     * UserBank not exist, than will be created.
     *
     * @param user
     *            User you want to update
     * @param profile
     *            user profile data to update
     * @param bank
     *            user bank data to update
     *
     * @return the updated User
     */
    User update(final User user, final UserProfile profile, final UserBank bank);

    /**
     * delete a User.
     *
     * @param entity
     *            user to be deleted
     */
    void delete(final User entity);

    /**
     * find User by id. Note. This method fetch all user data. To find only
     * user, use findOne.
     *
     * @param id
     *            User id
     * 
     * @return User class
     */
    User findById(final long id);

    /**
     * find all Users.
     *
     * @return a list of all Users
     */
    List<User> findAll();

    /**
     * delete all Users.
     */
    void deleteAll();

    /**
     * find a user by its unique name.
     *
     * @param name
     *            unique name
     * @return the user entity
     */
    User findByName(String name);

    /**
     * activates a photograph user account
     *
     * @param id
     *            the user id
     *
     * @return the user
     */
    User enablePhotograph(long id);

    /**
     * grants the user admin rights
     *
     * @param id
     *            the id
     *
     * @return the user
     */
    User makeAdmin(long id);

    /**
     * locks the user for the system
     *
     * @param id
     *            user id.
     * @return user.
     */
    User lockUser(long id);

    /**
     * unlocks the user for the system
     *
     * @param id
     *            user id.
     * @return user.
     */
    User unlockUser(long id);

    /**
     * Returns all user between start and count. IF start and count contains 0,
     * than return first user. IF start contains 0 and count contains 1, than
     * return first user.
     *
     * @param start
     *            index for first.
     * @param count
     *            index for last.
     * @return users
     */
    List<User> find(Integer start, Integer count);

    /**
     * Returns all photographs, that should be activated.
     *
     * @return users with role photograph.
     */
    List<User> findPhotographToActivate();

    /**
     * Returns all photographs, that should be activated.
     *
     * @param roleId
     *            the role id
     * @param enabled
     *            if true, enable
     *
     * @return users.
     */
    List<User> findByRoleAndEnabled(Long roleId, boolean enabled);

    /**
     * Check user admin role.
     *
     * @param user
     *            user.
     * @return true if user has role admin.
     */
    Boolean isUserAdmin(User user);

    /**
     * Check user photographer role.
     *
     * @param user
     *            user.
     * @return true if user has role photographer.
     */
    Boolean isUserPhotographer(User user);

    /**
     * Returns true, if user has the role.
     *
     * @param user
     *            user.
     * @param roleName
     *            role name.
     * @return true or false.
     */
    Boolean isRoleIncluded(User user, String roleName);
}
