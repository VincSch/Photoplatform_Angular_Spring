/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * repository methods for roles.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO {

    /**
     * Role DAO constructor.
     */
    public RoleDAOImpl() {
        super();
        setClazz(Role.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findByName(final String name) {
        String queryString = "SELECT role FROM Role role "
                + "WHERE role.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Role) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getAdmin() {
        return findOne(Role.ADMIN_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findAllNotAdminRoles() {
        String queryString = "SELECT role FROM Role role "
                + "WHERE role.id != ?1";
        Query query = createQuery(queryString);
        query.setParameter(1, Role.ADMIN_ID);
        return (List<Role>) query.getResultList();
    }

}
