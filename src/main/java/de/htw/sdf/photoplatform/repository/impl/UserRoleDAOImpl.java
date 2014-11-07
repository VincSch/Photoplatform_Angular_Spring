/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.UserRole;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

/**
 * repository methods for user roles.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole> implements UserRoleDAO
{

    /**
     * User Role DAO constructor.
     */
    public UserRoleDAOImpl()
    {
        super();
        setClazz(UserRole.class);
    }

    @Override
    public List<UserRole> findByUserId(final Long id)
    {
        String queryString = "SELECT userRole FROM UserRole userRole "
                + "LEFT JOIN FETCH userRole.user user " + "LEFT JOIN FETCH userRole.role role "
                + "WHERE user.id = ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public List<UserRole> findByRecipeRoleId(final Long id)
    {
        String queryString = "SELECT userRole FROM UserRole userRole "
                + "LEFT JOIN FETCH userRole.user user " + "LEFT JOIN FETCH userRole.role role "
                + "WHERE role.id = ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public List<UserRole> findByUserAndRoleId(final Long userId, final Long roleId)
    {
        String queryString = "SELECT userRole FROM UserRole userRole "
                + "LEFT JOIN FETCH userRole.user user " + "LEFT JOIN FETCH userRole.role role "
                + "WHERE user.id = ?1 AND role.id = ?2";

        Query query = createQuery(queryString);
        query.setParameter(1, userId);
        query.setParameter(2, roleId);
        return query.getResultList();
    }

}
