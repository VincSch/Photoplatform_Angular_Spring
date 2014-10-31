/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.UnitManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.persistence.Unit.GermanUnitName;

/**
 * repository methods for units.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Service
@Transactional
public class UnitManagerImpl extends DAOReferenceCollector implements
        UnitManager
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Unit entity)
    {
        unitDAO.create(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Unit entity)
    {
        unitDAO.delete(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Unit findById(long id)
    {
        return unitDAO.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Unit> findAll()
    {
        return unitDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Unit findByName(GermanUnitName name)
    {
        return unitDAO.findByGermanName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Unit entity)
    {
        unitDAO.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll()
    {
        unitDAO.deleteAll();

    }

}
