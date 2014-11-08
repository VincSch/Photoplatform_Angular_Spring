/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserImage;
import de.htw.sdf.photoplatform.repository.UserImageDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * Repository methods for relation between user and image.
 *
 * @author Sergej Meister.
 */
public class UserImageDAOImpl extends GenericDAOImpl<UserImage> implements UserImageDAO
{

    /**
     * UserImage DAO constructor.
     */
    public UserImageDAOImpl()
    {
        super();
        setClazz(UserImage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> findUserImagesBy(User user)
    {
        return null;
    }
}
