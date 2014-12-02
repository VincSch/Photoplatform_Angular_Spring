/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.common;

import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.repository.CategoryDAO;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.ImageDAO;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserImageDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public class DAOReferenceCollector {

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected UserRoleDAO userRoleDAO;

    @Autowired
    protected RoleDAO roleDAO;

    @Autowired
    protected CollectionDAO collectionDAO;

    @Autowired
    protected CategoryDAO categoryDAO;

    @Autowired
    protected ImageDAO imageDAO;

    @Autowired
    protected UserImageDAO userImageDAO;

    @Autowired
    protected CollectionImageDAO collectionImageDAO;
}
