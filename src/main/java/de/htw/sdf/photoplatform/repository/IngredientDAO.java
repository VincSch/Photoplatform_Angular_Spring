/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.models.Ingredient;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for ingredients.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
public interface IngredientDAO extends GenericDAO<Ingredient>
{

    /**
     * find a ingredient by its unique name.
     * 
     * @param name
     *            unique name
     * 
     * @return the ingredient entity
     */
    Ingredient findByName(String name);

}
