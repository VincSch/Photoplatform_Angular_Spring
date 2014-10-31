/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.models.NutritionFact;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for recipe books.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface NutritionFactDAO extends GenericDAO<NutritionFact>
{

    /**
     * find a nutrition fact by a ingredient id.
     * 
     * @param id
     *            unique id
     *
     * @return the nutrition fact
     */
    NutritionFact findByIngredientId(Long id);
}
