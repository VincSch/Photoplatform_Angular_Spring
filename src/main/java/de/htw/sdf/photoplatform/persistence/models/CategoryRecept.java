/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a recipe category corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "RB_CATEGORY")
public class CategoryRecept extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 318495786260623088L;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    /** Category. */
    public CategoryRecept()
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
