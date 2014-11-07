/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.CollectionImage;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;

/**
 * repository methods for relation between collection and image.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class CollectionImageDAOImpl extends GenericDAOImpl<CollectionImage> implements
        CollectionImageDAO
{
    /**
     * CollectionImage DAO constructor.
     */
    public CollectionImageDAOImpl()
    {
        super();
        setClazz(CollectionImage.class);
    }
}
