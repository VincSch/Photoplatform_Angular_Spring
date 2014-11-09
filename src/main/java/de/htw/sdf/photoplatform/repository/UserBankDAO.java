/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.models.UserBank;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 *
 * @author Sergej Meister.
 */
public interface UserBankDAO extends GenericDAO<UserBank> {

    /**
     * find a user details data by a user id.
     *
     * @param id
     *            unique user id
     * @return UserBank objects
     */
    UserBank findByUserId(Long id);
}
