/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for roles.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface RoleDAO extends GenericDAO<Role> {

    /**
     * find a role by its unique name.
     *
     * @param name unique name
     * @return the role entity
     */
    Role findByName(final String name);

    /**
     * find a admin role.
     *
     * @return the admin role entity
     */
    Role getAdmin();

    /**
     * Returns all roles without admin role.
     *
     * @return all roles without admin role
     */
    List<Role> findAllNotAdminRoles();
}
