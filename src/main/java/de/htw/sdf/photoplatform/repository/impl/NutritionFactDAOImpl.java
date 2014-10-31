/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.NutritionFact;
import de.htw.sdf.photoplatform.repository.NutritionFactDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for nutritionfacts.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class NutritionFactDAOImpl extends GenericDAOImpl<NutritionFact> implements NutritionFactDAO
{

    /**
     * Nutrition Fact DAO constructor.
     */
    public NutritionFactDAOImpl()
    {
        super();
        setClazz(NutritionFact.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NutritionFact findByIngredientId(Long id)
    {
        String queryString = "SELECT nutritionfact FROM NutritionFact nutritionfact "
                + "WHERE nutritionfact.ingredient.id = ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return (NutritionFact) query.getSingleResult();
    }

}
