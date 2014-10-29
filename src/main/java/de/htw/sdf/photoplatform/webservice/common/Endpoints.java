/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.common;

/**
 * Constant class keeping track of all service endpoints.
 * 
 * @author Vincent Schwarzer
 * 
 */
public final class Endpoints
{

    /**
     * Endpoints for ingredients.
     */
    public static final String API_PREFIX = "/api";

    /** INGREDIENT_BY_NAME. */
    public static final String INGREDIENT_BY_NAME = "/ingredient/byname/{name}";
    /** INGREDIENT_ALL. */
    public static final String INGREDIENT_ALL = "/ingredient/all";
    /** INGREDIENT_DELETE_BY_NAME. */
    public static final String INGREDIENT_DELETE_BY_NAME = "/ingredient/delete/{name}";
    /** INGREDIENT_CREATE. */
    public static final String INGREDIENT_CREATE = "/ingredient/create";
    /** INGREDIENT_UPDATE. */
    public static final String INGREDIENT_UPDATE = "/ingredient/update/{id}";

    /**
     * Endpoints for recipes.
     */
    /** RECIPE_BY_NAME. */
    public static final String RECIPE_BY_NAME = "/recipe/byname/{name}";
    /** RECIPE_BY_ID. */
    public static final String RECIPE_BY_ID = "/recipe/byid/{id}";
    /** RECIPE_BY_USERNAME. */
    public static final String RECIPE_BY_USERNAME = "/recipe/byusername/{name}";
    /** RECIPE_ALL. */
    public static final String RECIPE_ALL = "/recipe/all";
    /** RECIPE_DELETE_BY_NAME. */
    public static final String RECIPE_DELETE_BY_NAME = "/recipe/delete/{name}";
    /** RECIPE_CREATE. */
    public static final String RECIPE_CREATE = "/recipe/create";
    /** RECIPE_UPDATE. */
    public static final String RECIPE_UPDATE = "/recipe/update/{id}";
    /** RECIPE_DIFFICULTY_ALL. */
    public static final String RECIPE_DIFFICULTY_ALL = "/recipe/difficulty/all";
    /** RECIPE_REMOVE_INGREDIENT. */
    public static final String RECIPE_REMOVE_INGREDIENT = "/recipe/remove/ingredient";
    /** RECIPE_ADD_INGREDIENT. */
    public static final String RECIPE_ADD_INGREDIENT = "/recipe/add/ingredient";

    /**
     * Endpoints for recipe books.
     */
    /** RECIPEBOOK_BY_NAME. */
    public static final String RECIPEBOOK_BY_NAME = "/recipebook/byname/{name}";
    /** RECIPEBOOK_BY_USERNAME. */
    public static final String RECIPEBOOK_BY_USERNAME = "/recipebook/byusername/{name}";
    /** RECIPEBOOK_ALL. */
    public static final String RECIPEBOOK_ALL = "/recipebook/all";
    /** RECIPEBOOK_DELETE_BY_NAME. */
    public static final String RECIPEBOOK_DELETE_BY_NAME = "/recipebook/delete/{name}";
    /** RECIPEBOOK_CREATE. */
    public static final String RECIPEBOOK_CREATE = "/recipebook/create";
    /** RECIPEBOOK_UPDATE. */
    public static final String RECIPEBOOK_UPDATE = "/recipebook/update/{id}";
    /** RECIPEBOOK_REMOVE_RECIPE. */
    public static final String RECIPEBOOK_REMOVE_RECIPE = "/recipebook/remove/recipe";
    /** RECIPEBOOK_ADD_RECIPE. */
    public static final String RECIPEBOOK_ADD_RECIPE = "/recipebook/add/recipe";

    /**
     * Endpoints for units.
     */
    public static final String UNIT_ALL = "/unit/all";

    /**
     * Endpoints for nutrition facts.
     */
    /** NUTRITIONFACT_UPDATE. */
    public static final String NUTRITIONFACT_UPDATE = "/nutritionfact/update";
    /** NUTRITIONFACT_CREATE. */
    public static final String NUTRITIONFACT_CREATE = "/nutritionfact/create";
    /** NUTRITION_BY_INGREDIENT. */
    public static final String NUTRITION_BY_INGREDIENT = "/nutritionfact/byingredient/{id}";
    /** NUTRITION_ALL. */
    public static final String NUTRITION_ALL = "/nutritionfact/all";
    /** CALORIES_FOR_USEDINGREDIENTS. */
    public static final String CALORIES_FOR_USEDINGREDIENTS = "/nutritionfact/caloriesforusedingredients";

    /**
     * Endpoints for users.
     */
    /** USER_LOGIN. */
    public static final String USER_LOGIN = "/user/login";
    /** USER_REGISTER. */
    public static final String USER_REGISTER = "/user/register";
    /** USER_UPDATE. */
    public static final String USER_UPDATE = "/user/update";
    /** USER_BY_NAME. */
    public static final String USER_BY_NAME = "/user/byname/{name}";
    /** USER_GET_RECIPE_COUNT. */
    public static final String USER_GET_RECIPE_COUNT = "/user/recipes/amount/{name}";
    /** USER_GET_RECIPEBOOK_COUNT. */
    public static final String USER_GET_RECIPEBOOK_COUNT = "/user/recipebooks/amount/{name}";

    /**
     * Endpoints for application maintenance.
     */
    public static final String MAINTENANCE_STATISTIC = "/statistic";

    /**
     * Private Endpoint constructor.
     */
    private Endpoints()
    {
        // Private
    }

    /**
     * End points as strings which have to be secured and can only be accessed
     * by an admin or user.
     * 
     * @return array of end points as strings which have to be secured and can
     *         only be accessed by an admin or user
     */
    public static String[] securedUserEndpoints()
    {
        String[] securedEnpoints = {
                // Ingredients
                restBuilder(INGREDIENT_UPDATE),
                restBuilder(INGREDIENT_CREATE),
                restBuilder(INGREDIENT_DELETE_BY_NAME),
                restBuilder(INGREDIENT_BY_NAME),
                // Recipes
                restBuilder(RECIPE_DELETE_BY_NAME),
                restBuilder(RECIPE_CREATE),
                restBuilder(RECIPE_UPDATE),
                restBuilder(RECIPE_DIFFICULTY_ALL),
                restBuilder(RECIPEBOOK_REMOVE_RECIPE),
                restBuilder(RECIPEBOOK_ADD_RECIPE),
                // Recipe books
                restBuilder(RECIPEBOOK_DELETE_BY_NAME),
                restBuilder(RECIPEBOOK_CREATE), restBuilder(RECIPEBOOK_UPDATE),
                restBuilder(RECIPEBOOK_REMOVE_RECIPE),
                restBuilder(RECIPEBOOK_ADD_RECIPE),
                // Nutrition facts
                restBuilder(NUTRITIONFACT_CREATE),
                // Units
                restBuilder(UNIT_ALL),
                // User
                restBuilder(USER_UPDATE)};
        return securedEnpoints;
    }

    /**
     * End points as strings which have to be secured and can only be accessed
     * by an admin.
     * 
     * @return array of end points as strings which have to be secured and can
     *         only be accessed by an admin
     */
    public static String[] securedAdminEndpoints()
    {
        String[] securedEnpoints = {
        // Ingredients
        restBuilder(MAINTENANCE_STATISTIC)};
        return securedEnpoints;
    }

    /**
     * Res builder.
     * 
     * @param endPoint
     *            the end point
     * 
     * @return res builder
     */
    private static String restBuilder(String endPoint)
    {
        return API_PREFIX + endPoint;
    }
}
