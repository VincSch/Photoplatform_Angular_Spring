/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * repository methods for relation between collection and image.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class CollectionImageDAOImpl extends GenericDAOImpl<CollectionImage>
        implements CollectionImageDAO {
    /**
     * CollectionImage DAO constructor.
     */
    public CollectionImageDAOImpl() {
        super();
        setClazz(CollectionImage.class);
    }
}
