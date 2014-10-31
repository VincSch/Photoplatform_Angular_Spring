/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.Recipe;
import de.htw.sdf.photoplatform.persistence.models.RecipeDifficulty;
import de.htw.sdf.photoplatform.repository.RecipeDifficultyDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for receipt diffculties.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class RecipeDifficultyDAOImpl extends GenericDAOImpl<RecipeDifficulty> implements
        RecipeDifficultyDAO
{

    /**
     * Recipe dofficulty DAO constructor.
     */
    public RecipeDifficultyDAOImpl()
    {
        super();
        setClazz(RecipeDifficulty.class);
    }

    @Override
    public Recipe findByName(String name)
    {
        String queryString = "SELECT recipediff FROM RecipeDifficulty recipediff "
                + "WHERE recipediff.name like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return (Recipe) query.getSingleResult();
    }

}
