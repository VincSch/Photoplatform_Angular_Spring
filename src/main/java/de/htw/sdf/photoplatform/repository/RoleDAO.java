/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.Role;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for roles.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface RoleDAO extends GenericDAO<Role>
{

    /**
     * find a role by its unique name.
     * 
     * @param name
     *            unique name
     * 
     * @return the role entity
     */
    Role findByName(String name);
}
