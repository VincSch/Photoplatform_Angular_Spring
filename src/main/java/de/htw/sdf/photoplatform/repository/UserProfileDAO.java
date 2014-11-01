/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.models.user.UserProfile;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Created by Sergej Meister
 */
public interface UserProfileDAO extends GenericDAO<UserProfile>
{

    /**
     * find a user details data by a user id.
     *
     * @param id
     *            unique user id
     * @return UserProfile objects
     */
    UserProfile findByUserId(Long id);
}
