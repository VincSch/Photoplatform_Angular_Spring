/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a category representing the corresponding database table.
 *
 * Created by Sergej Meister.
 */
@Entity
@Table(name = "SYS_CATEGORY")
public class Category extends AbstractBaseAuditEntity
{

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Returns category name.
     *
     * @return category name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets category name.
     *
     * @param name
     *            category name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns category description.
     *
     * @return category description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets category decription.
     *
     * @param description
     *            category description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "Category{" + "name='" + name + '\'' + '}';
    }
}
