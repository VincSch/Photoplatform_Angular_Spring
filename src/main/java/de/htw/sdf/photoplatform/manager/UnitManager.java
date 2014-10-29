/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.persistence.Unit.GermanUnitName;

/**
 * Interface defining business methods for units.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
public interface UnitManager
{

    /**
     * persist a entity.
     * 
     * @param entity
     *            the entity
     */
    void create(final Unit entity);

    /**
     * update a unit.
     * 
     * @param entity
     *            the unit you want to update
     */
    void update(final Unit entity);

    /**
     * delete a unit.
     * 
     * @param entity
     *            to be deleted
     */
    void delete(final Unit entity);

    /**
     * find unit by its id.
     * 
     * @param id
     *            unit id
     * 
     * @return unit class
     */
    Unit findById(final long id);

    /**
     * find all units.
     * 
     * @return a list of all units
     */
    List<Unit> findAll();

    /**
     * find a unit by its unique name.
     * 
     * @param name
     *            unique name
     * @return the unit entity
     */
    Unit findByName(GermanUnitName name);

    /**
     * delete all units.
     */
    void deleteAll();
}
