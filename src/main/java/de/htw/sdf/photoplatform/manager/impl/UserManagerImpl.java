/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserRole;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

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
    public void createCustomer(User customer)
    {
        userDAO.create(customer);
        UserRole userRole = new UserRole();
        userRole.setUser(customer);
        Role role = roleDAO.findByName(Role.CUSTOMER);
        if (role == null)
        {
            throw new RuntimeException("User role = " + Role.CUSTOMER + " does not exists.");
        }

        userRole.setRole(role);
        userRoleDAO.create(userRole);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerUser(String username, String email, String password)
            throws ManagerException
    {
        if (username == null || email == null || password == null)
        {
            throw new RuntimeException("This should not happen");
        }

        // 1. check if username or email already exists in DB
        User user = userDAO.findByUserName(username);
        if (user != null)
        {
            throw new ManagerException(AbstractBaseException.USER_USERNAME_EXISTS);
        }

        user = userDAO.findByEmail(email);
        if (user != null)
        {
            throw new ManagerException(AbstractBaseException.USER_EMAIL_EXISTS);
        }

        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        //user.setPassword(new BCryptPasswordEncoder().encode(password));

        // Enable user
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setEmail(email);

        createCustomer(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(User entity)
    {
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
    public User findById(long id)
    {
        return userDAO.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll()
    {
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
    public User findByName(String username)
    {
        return userDAO.findByUserName(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> find(Integer start, Integer count) {
        return userDAO.find(start,count);
    }

    @Override
    public Boolean isUserAdmin(User user) {
        for(UserRole userRole : user.getUserRoles()){
            if(userRole.getRole().getName().equals(Role.ADMIN)){
                return true ;
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
        return userDAO.findByRoleAndEnabledFilter(roleId,enabled);
    }

    /**
     * {inheritDoc}
     */
    @Override public User lockUser(String name) {
        User user = userDAO.findByUserName(name);
        user.setAccountNonLocked(false);
        userDAO.update(user);
        return user;
    }
}
