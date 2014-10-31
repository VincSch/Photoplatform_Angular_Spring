/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.models.Recipe;
import de.htw.sdf.photoplatform.persistence.models.RecipeDifficulty;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

/**
 * Interface defining repository methods for receipts difficulty.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
public interface RecipeDifficultyDAO extends GenericDAO<RecipeDifficulty>
{

    /**
     * find a receipt difficulty by its unique name.
     * 
     * @param name
     *            unique name
     * @return the recipe difficulty entity
     */
    Recipe findByName(String name);
}
