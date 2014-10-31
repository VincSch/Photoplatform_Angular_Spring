/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a recipe category corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "RB_CATEGORY")
public class Category extends BaseAuditEntity
{

    private static final long serialVersionUID = 318495786260623088L;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    /** Category. */
    public Category()
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
     *            the name
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
