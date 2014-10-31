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
 * Entity class for a ingredient representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_INGREDIENT")
public class Ingredient extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 6769576720889667073L;

    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Ingredient constructor.
     */
    public Ingredient()
    {
        super();
    }

    /**
     * Ingredient constructor.
     * 
     * @param name
     *            the name
     * @param uni
     *            the uni
     */
    public Ingredient(String name, Unit uni)
    {
        setName(name);
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
