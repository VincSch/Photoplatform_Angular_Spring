/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.user.User;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

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

    @Override
    public User findByUserName(String userName)
    {
        String queryString = "SELECT user FROM User user "
                + "LEFT JOIN FETCH user.userRoles userRoles " + "WHERE user.username like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, userName);
        return (User) query.getSingleResult();
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        String queryString = "SELECT user FROM User user "
                + "LEFT JOIN FETCH user.userRoles userRoles " + "WHERE user.username like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, username);
        return (User) query.getSingleResult();
    }

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
