/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.AuthorizationException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * This controller authorize the users permissions.
 *
 * @author Sergej Meister
 */
@Controller
public class AuthorizationController {

    @Resource
    private UserManager userManager;

    /**
     * Check if the userId contains the id of signed user.
     *
     * @param requestUserId id of user, that call webservice.
     */
    public void isSigned(String requestUserId) throws AbstractBaseException {
        User authenticatedUser = getAuthenticatedUser();
        checkUserId(requestUserId, authenticatedUser.getId());
    }

    private void checkUserId(String requestUserId, Long authenticatedUserId) throws AbstractBaseException {
        Long userId;
        try {
            userId = Long.parseLong(requestUserId);
        } catch (NumberFormatException nfe) {
            throw new AuthorizationException(AbstractBaseException.AUTHORIZATION_NOT_VALID);
        }

        if (!authenticatedUserId.equals(userId)) {
            throw new AuthorizationException(AbstractBaseException.AUTHORIZATION_NOT_VALID);
        }
    }

    /**
     * can only be accessed by admin and authenticated user.
     *
     * @param requestUserId id of user, that call webservice.
     */
    public void checkUserPermissions(String requestUserId) throws AbstractBaseException {
        User authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser.isAdmin()) {
            return;
        }

        checkUserId(requestUserId, authenticatedUser.getId());
    }

    /**
     * Returns authenticated user.
     *
     * @return user.
     */
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        //Anonymous user.
        return null;
    }
}
