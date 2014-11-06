/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.image.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.image.Image;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import de.htw.sdf.photoplatform.repository.image.ImageDAO;

/**
 * Repository methods for image.
 *
 * @autor Sergej Meister.
 */
@Repository
@Transactional
public class ImageDAOImpl extends GenericDAOImpl<Image> implements ImageDAO
{

    /**
     * Image DAO constructor.
     */
    public ImageDAOImpl()
    {
        super();
        setClazz(Image.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Image> getPublicImages()
    {
        String queryString = "SELECT image FROM Image image " + "WHERE image.isPublic = "
                + Boolean.TRUE;
        Query query = createQuery(queryString);
        return (List<Image>) query.getResultList();
    }
}
