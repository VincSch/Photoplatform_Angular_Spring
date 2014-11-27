/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.common;

import de.htw.sdf.photoplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    protected CollectionImageDAO collectionImageDAO;
}
