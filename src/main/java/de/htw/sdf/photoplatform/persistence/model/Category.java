/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for a category representing the corresponding database table.
 * <p/>
 * Created by Sergej Meister.
 */
@Entity
@Table(name = "RES_CATEGORY")
public class Category extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = -5734114755016412092L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Returns category name.
     *
     * @return category name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets category name.
     *
     * @param name category name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns category description.
     *
     * @return category description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets category decription.
     *
     * @param description category description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Category{" + "name='" + name + '\'' + '}';
    }
}
