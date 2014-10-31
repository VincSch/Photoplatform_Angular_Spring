/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a unit representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_UNIT")
public class Unit extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 4144282015263601060L;

    /**
     * German Unit Names.
     */
    public enum GermanUnitName
    {
        /** Esslöfel. */
        EL,
        /** Teelöfel. */
        TL,
        /** Milligram. */
        mg,
        /** Gram. */
        g,
        /** Kg. */
        Kg,
        /** t. */
        t,
        /** l. */
        l,
        /** ml. */
        ml;

    }

    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    private GermanUnitName germanUnitName;

    /**
     * Unit consctructor.
     */
    public Unit()
    {
        super();
    }

    /**
     * Unit constructor.
     *
     * @param unitName
     *            the unit name
     */
    public Unit(GermanUnitName unitName)
    {
        setGermanUnitName(unitName);
    }

    /**
     * @return the german unit name
     */
    public GermanUnitName getGermanUnitName()
    {
        return germanUnitName;
    }

    /**
     * @param germanUnitName
     *            the the german unit name to set
     */
    public void setGermanUnitName(GermanUnitName germanUnitName)
    {
        this.germanUnitName = germanUnitName;
    }
}
