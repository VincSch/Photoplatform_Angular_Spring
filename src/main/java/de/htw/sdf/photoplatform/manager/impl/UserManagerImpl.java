/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserRole;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * business methods for users.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Service
@Transactional
public class UserManagerImpl implements UserManager {

    @Resource
    private UserDAO userDAO;

    @Resource
    private RoleDAO roleDAO;

    @Resource
    private UserRoleDAO userRoleDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerUser(final String username, final String email,
                             final String password) throws ManagerException {

        checkUser(username, email, password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        // Enable user
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setEmail(email);

        userDAO.create(user);

        UserRole userRole = new UserRole();
        userRole.setUser(user);

        Role role = roleDAO.findByName(Role.CUSTOMER);
        if (role == null) {
            throw new RuntimeException("User role = " + Role.CUSTOMER
                    + " does not exists.");
        }

        userRole.setRole(role);
        userRoleDAO.create(userRole);
    }

    /**
     * Check is email or username already exists.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     * @throws ManagerException the exception
     */
    private void checkUser(final String username, final String email, final String password)
            throws ManagerException {
        if (username == null || email == null || password == null) {
            throw new RuntimeException("This should not happen");
        }

        // 1. check if username or email already exists in DB
        User user = userDAO.findByUserName(username);
        if (user != null) {
            throw new ManagerException(
                    AbstractBaseException.USER_USERNAME_EXISTS);
        }

        // 2. check if email already in use
        user = userDAO.findByEmail(email);
        if (user != null) {
            throw new ManagerException(AbstractBaseException.USER_EMAIL_EXISTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(User entity) {
        return userDAO.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(User entity) {
        userDAO.delete(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(final long id) {
        return userDAO.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        userDAO.deleteAll();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByName(String username) {
        return userDAO.findByUserName(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> find(Integer start, Integer count) {
        return userDAO.find(start, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isUserAdmin(User user) {
        return isRoleIncluded(user, Role.ADMIN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isUserPhotographer(User user) {
        return isRoleIncluded(user, Role.PHOTOGRAPHER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isRoleIncluded(User user, String roleName) {
        for (UserRole userRole : user.getUserRoles()) {
            if (userRole.getRole().getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findPhotographToActivate() {
        return findByRoleAndEnabled(Role.PHOTOGRAPHER_ID, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findByRoleAndEnabled(Long roleId, boolean enabled) {
        return userDAO.findByRoleAndEnabledFilter(roleId, enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User enablePhotograph(long id) {
        User user = userDAO.findOne(id);
        if (isUserPhotographer(user) && (!user.isEnabled())) {
            user.setEnabled(true);
            userDAO.update(user);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User makeAdmin(long id) {
        User user = userDAO.findOne(id);
        if (!isUserAdmin(user)) {
            Role admin = roleDAO.findOne(Role.ADMIN_ID);

            UserRole userRole = new UserRole();
            userRole.setRole(admin);
            userRole.setUser(user);
            userRoleDAO.create(userRole);

            userDAO.update(user);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User lockUser(long id) {
        User user = userDAO.findOne(id);
        user.setAccountNonLocked(false);
        userDAO.update(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User unlockUser(long id) {
        User user = userDAO.findOne(id);
        user.setAccountNonLocked(true);
        userDAO.update(user);
        return user;
    }
}
