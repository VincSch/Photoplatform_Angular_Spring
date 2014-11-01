/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseEntity;

/**
 * Entity class for a role representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "SYS_ROLE")
public class Role extends AbstractBaseEntity
{

    private static final long serialVersionUID = -219754255462744875L;

    @Column(name = "NAME")
    private String name;

    /**
     * Role constructor.
     */
    public Role()
    {
        super();
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
