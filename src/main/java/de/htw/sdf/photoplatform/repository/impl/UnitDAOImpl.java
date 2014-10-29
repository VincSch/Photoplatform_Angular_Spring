/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.persistence.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.repository.UnitDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for units.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class UnitDAOImpl extends GenericDAOImpl<Unit> implements UnitDAO
{

    /**
     * Unit DAO constructor.
     */
    public UnitDAOImpl()
    {
        super();
        setClazz(Unit.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Unit findByGermanName(GermanUnitName name)
    {
        String queryString = "SELECT unit FROM Unit unit "
                + "WHERE unit.germanUnitName like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return (Unit) query.getSingleResult();
    }

}
