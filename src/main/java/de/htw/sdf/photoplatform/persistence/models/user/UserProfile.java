/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a unit representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_USER_PROFILE")
public class UserProfile extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 2595993675256650125L;

    /**
     * User Profile constructor.
     */
    public UserProfile()
    {
        super();
    }

}
