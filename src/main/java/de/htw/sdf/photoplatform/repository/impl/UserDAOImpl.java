/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import de.htw.sdf.photoplatform.common.Constants;
import de.htw.sdf.photoplatform.persistence.models.user.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.user.User;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

import java.util.List;

/**
 * repository methods for users.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO, UserDetailsService
{
    /**
     * User DAO constructor.
     */
    public UserDAOImpl()
    {
        super();
        setClazz(User.class);
    }

    public void create(User entity)
    {
        super.create(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUserName(String userName)
    {
        String queryString = "SELECT user FROM User user "
                + "LEFT JOIN FETCH user.userRoles userRoles " + "WHERE user.username like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, userName);
        return (User) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username)
    {
        String queryString = "SELECT user FROM User user "
                + "LEFT JOIN FETCH user.userRoles userRoles " + "WHERE user.username like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, username);
        return (User) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findByRole(Role role)
    {
        return findByRoleId(role.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findByRoleId(Long roleId)
    {
        StringBuilder queryBuilder = new StringBuilder("SELECT user FROM User user ");
        queryBuilder.append("LEFT JOIN FETCH user.userRoles userRoles ");
        if (Constants.ROLE_PHOTOGRAPHER.equals(roleId))
        {
            queryBuilder.append("LEFT JOIN FETCH user.userBank ");
        }
        queryBuilder.append("WHERE userRoles.role.id = ?1");

        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, roleId);
        return (List<User>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllNotAdminUsers()
    {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE userRoles.role.id != ?1");

        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, Constants.ROLE_ADMIN);
        return (List<User>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findByEnabled(boolean enabled)
    {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE user.enabled = ?1");

        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, enabled);
        return (List<User>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findByAccountLocked(boolean locked)
    {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE user.accountNonLocked = ?1");

        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, locked);
        return (List<User>) query.getResultList();
    }

    private StringBuilder initSelectQuery()
    {
        StringBuilder queryBuilder = new StringBuilder("SELECT user FROM User user ");
        queryBuilder.append("LEFT JOIN FETCH user.userRoles userRoles ");
        queryBuilder.append("LEFT JOIN FETCH user.userBank ");

        return queryBuilder;
    }

    // can be removed later

    @Override
    public Long getRecipeAmount(String userName)
    {
        String queryString = "SELECT COUNT(recipe.id) FROM Recipe recipe "
                + "WHERE recipe.createdBy like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, userName);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getRecipeBookAmount(String userName)
    {
        String queryString = "SELECT COUNT(book.id) FROM RecipeBook book "
                + "WHERE book.createdBy like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, userName);
        return (Long) query.getSingleResult();
    }

}
