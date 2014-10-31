/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.models.Unit;
import de.htw.sdf.photoplatform.persistence.models.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for units.
 * 
 * @author Vincent Schwarzer
 * 
 */
public interface UnitDAO extends GenericDAO<Unit>
{

    /**
     * find a unit by its unique name.
     * 
     * @param name
     *            unique name
     *
     * @return the unit entity
     */
    Unit findByGermanName(GermanUnitName name);
}
