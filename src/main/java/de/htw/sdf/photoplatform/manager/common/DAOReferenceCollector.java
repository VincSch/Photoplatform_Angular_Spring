/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.common;

import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.repository.CategoryDAO;
import de.htw.sdf.photoplatform.repository.IngredientDAO;
import de.htw.sdf.photoplatform.repository.NutritionFactDAO;
import de.htw.sdf.photoplatform.repository.RecipeBookDAO;
import de.htw.sdf.photoplatform.repository.RecipeDAO;
import de.htw.sdf.photoplatform.repository.RecipeDifficultyDAO;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UnitDAO;
import de.htw.sdf.photoplatform.repository.UsedIngredientsDAO;
import de.htw.sdf.photoplatform.repository.UsedRecipeDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

/**
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public class DAOReferenceCollector
{

    @Autowired
    protected IngredientDAO ingredientDAO;

    @Autowired
    protected UnitDAO unitDAO;

    @Autowired
    protected RecipeDAO recipeDAO;

    @Autowired
    protected UsedIngredientsDAO recipeUsesIngredientsDAO;

    @Autowired
    protected RecipeDifficultyDAO recipeDifficultyDAO;

    @Autowired
    protected UsedRecipeDAO recipeBookHasRecipeDAO;

    @Autowired
    protected CategoryDAO categoryDAO;

    @Autowired
    protected RecipeBookDAO recipeBookDAO;

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected UserRoleDAO userRoleDAO;

    @Autowired
    protected RoleDAO roleDAO;

    @Autowired
    protected NutritionFactDAO nutritionDAO;

}
