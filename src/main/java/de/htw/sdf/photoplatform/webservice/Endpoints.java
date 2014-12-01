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
 */
public final class Endpoints {

    /**
     * Examples: GET /tickets - Retrieves a list of tickets GET /tickets/12 -
     * Retrieves a specific ticket POST /tickets - Creates a new ticket PUT
     * /tickets/12 - Updates ticket #12 PATCH /tickets/12 - Partially updates
     * ticket #12 DELETE /tickets/12 - Deletes ticket #12
     *
     * GET /tickets/12/messages - Retrieves list of messages for ticket #12 GET
     * /tickets/12/messages/5 - Retrieves message #5 for ticket #12 POST
     * /tickets/12/messages - Creates a new message in ticket #12 PUT
     * /tickets/12/messages/5 - Updates message #5 for ticket #12 PATCH
     * /tickets/12/messages/5 - Partially updates message #5 for ticket #12aa
     * DELETE /tickets/12/messages/5 - Deletes message #5 for ticket #12
     */

    /**
     * API entry point.
     */
    public static final String API_PREFIX = "/api";

    /**
     * Endpoints for users.
     */
    /**
     * USER_LOGIN.
     */
    public static final String USER_LOGIN = "/user/login";
    /**
     * USER_REGISTER.
     */
    public static final String USER_REGISTER = "/user/register";

    /**
     * USER_REGISTER.
     */
    public static final String USER_BECOME_PHOTOGRAPHER = "/user/becomePhotographer";

    /**
     * USERS_UPDATE.
     */
    public static final String USERS_UPDATE = "/users/update";
    /**
     * USER_BY_NAME.
     */
    public static final String USER_BY_NAME = "/user/{name}";

    /**
     * all users between start and count.
     */
    public static final String USERS_START_COUNT = "/users/admin/{start}/{count}";

    /**
     * Get user profile included bank data.
     */
    public static final String USERS_PROFILE_BY_USER_ID = "/users/profile/{userId}";

    /**
     * all disabled users for given role.
     */
    public static final String USERS_BECOME_PHOTOGRAPHERS = "/users/becomephotographers";

    /**
     * activates a photograph user
     */
    public static final String USER_ENABLE_PHOTOGRAPHER = "/user/enablephotographer";

    /**
     * grants an user admin role
     */
    public static final String USER_MAKE_ADMIN = "/user/makeadmin/{id}";

    /**
     * lock a user.
     */
    public static final String USER_LOCK = "/user/lock/{id}";

    /**
     * unlock a user.
     */
    public static final String USER_UNLOCK = "/user/unlock/{id}";

    /**
     * Private Endpoint constructor.
     */
    private Endpoints() {
        // Private
    }

    /**
     * End points as strings which have to be secured and can only be accessed
     * by all authenticated users.
     *
     * @return array of end points as strings which have to be secured and can
     * only be accessed by an admin or user
     */
    public static String[] securedUserEndpoints() {
        String[] securedEndpoints = {
                restBuilder(USERS_UPDATE),
                restBuilder(USER_BECOME_PHOTOGRAPHER)
        };
        return securedEndpoints;
    }

    /**
     * End points as strings which have to be secured and can only be accessed
     * by an admin.
     *
     * @return array of end points as strings which have to be secured and can
     * only be accessed by an admin
     */
    public static String[] securedAdminEndpoints() {
        String[] securedEndpoints = {
                restBuilder(USERS_START_COUNT),
                restBuilder(USERS_BECOME_PHOTOGRAPHERS),
                restBuilder(USER_MAKE_ADMIN),
                restBuilder(USER_LOCK),
                restBuilder(USER_UNLOCK)
        };
        return securedEndpoints;
    }

    /**
     * End points as strings which have to be secured and can only be accessed
     * by users with role photograph or admin.
     *
     * @return array of end points as strings which have to be secured and can
     * only be accessed by an admin or user
     */
    public static String[] securedPhotographEndpoints() {
        String[] securedEndpoints = {
                restBuilder(USERS_UPDATE),
                restBuilder(USERS_START_COUNT),
                restBuilder(USERS_BECOME_PHOTOGRAPHERS)
        };
        return securedEndpoints;
    }

    /**
     * Res builder.
     *
     * @param endPoint the end point
     * @return res builder
     */
    private static String restBuilder(String endPoint) {
        return API_PREFIX + endPoint;
    }
}
