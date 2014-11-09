/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice;

/**
 * Constant class keeping track of all service endpoints.
 * 
 * @author Vincent Schwarzer
 * 
 */
public final class Endpoints
{
    /**
     * API entry point.
     */
    public static final String API_PREFIX = "/api";

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
    public static final String USER_BY_NAME = "/user/{name}";

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
     * End points as strings which have to be secured and can only be accessed by an admin or user.
     * 
     * @return array of end points as strings which have to be secured and can only be accessed by
     *         an admin or user
     */
    public static String[] securedUserEndpoints()
    {
        String[] securedEndpoints = {
        // User
        restBuilder(USER_UPDATE)};
        return securedEndpoints;
    }

    /**
     * End points as strings which have to be secured and can only be accessed by an admin.
     * 
     * @return array of end points as strings which have to be secured and can only be accessed by
     *         an admin
     */
    public static String[] securedAdminEndpoints()
    {
        String[] securedEndpoints = {
        // Ingredients
        restBuilder(MAINTENANCE_STATISTIC)};
        return securedEndpoints;
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
