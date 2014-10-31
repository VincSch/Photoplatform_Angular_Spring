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
 * Entity class for a recipe representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_RECIPE_DIFFICULTY")
public class RecipeDifficulty extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 174609213319757623L;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

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
