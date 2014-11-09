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
     * Examples:
     * GET /tickets - Retrieves a list of tickets
     * GET /tickets/12 - Retrieves a specific ticket
     * POST /tickets - Creates a new ticket
     * PUT /tickets/12 - Updates ticket #12
     * PATCH /tickets/12 - Partially updates ticket #12
     * DELETE /tickets/12 - Deletes ticket #12
     *
     * GET /tickets/12/messages - Retrieves list of messages for ticket #12
     * GET /tickets/12/messages/5 - Retrieves message #5 for ticket #12
     * POST /tickets/12/messages - Creates a new message in ticket #12
     * PUT /tickets/12/messages/5 - Updates message #5 for ticket #12
     * PATCH /tickets/12/messages/5 - Partially updates message #5 for ticket #12aa
     * DELETE /tickets/12/messages/5 - Deletes message #5 for ticket #12
     */


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
        String[] securedEnpoints = {
        // User
        restBuilder(USER_UPDATE)};
        return securedEnpoints;
    }

    /**
     * End points as strings which have to be secured and can only be accessed by an admin.
     * 
     * @return array of end points as strings which have to be secured and can only be accessed by
     *         an admin
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
