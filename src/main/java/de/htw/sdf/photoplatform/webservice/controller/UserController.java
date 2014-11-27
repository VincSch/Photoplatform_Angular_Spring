/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.NotFoundException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.response.UserData;
import de.htw.sdf.photoplatform.webservice.util.UserUtility;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * This controller present the REST user services.
 *
 * @author Sergej Meister
 */
@RestController
public class UserController extends BaseAPIController {

    @Resource
    private AuthorizationController authorizationController;

    @Resource
    private UserManager userManager;

    /**
     * GET list of enabled users between start an count.
     *
     * @param start start parameter.
     * @param count list size.
     * @return list of enabled users between start an count.
     * @throws IOException
     * @throws AbstractBaseException
     */
    @RequestMapping(value = Endpoints.USERS_START_COUNT, method = RequestMethod.GET)
    @ResponseBody
    public List<UserData> getEnabledUsers(@PathVariable int start,
                                          @PathVariable int count) throws IOException, AbstractBaseException {
        List<User> users = userManager.find(start, count);
        return UserUtility.getInstance().convertToUserData(users);
    }

    /**
     * Return list of all disabled users by id.
     *
     * @param roleName role name.
     * @return list of all disabled users.
     * @throws IOException           input output exception.
     * @throws AbstractBaseException abstract exception.
     */
    @RequestMapping(value = Endpoints.USERS_DISABLED_BY_ROLE, method = RequestMethod.GET)
    @ResponseBody
    public List<UserData> getDisabledUsersByRole(@PathVariable String roleName)
            throws IOException, AbstractBaseException {
        if (roleName.trim().equals(Role.PHOTOGRAPHER)) {
            List<User> users = userManager.findPhotographToActivate();
            return UserUtility.getInstance().convertToUserData(users);
        } else {
            String msg = "The role ("
                    + roleName
                    + ") is not correct or get users for this role is not supported in this version!";
            throw new BadRequestException(msg);
        }
    }

    /**
     * enable user with photographer role.
     *
     * @param id user id.
     * @return data transfer object userData.
     */
    @RequestMapping(value = Endpoints.USER_ENABLE_PHOTOGRAPH, method = RequestMethod.GET)
    @ResponseBody
    public void enablePhotograph(@PathVariable String id) {
        long longId = Long.valueOf(id);
        userManager.enablePhotograph(longId);
    }

    /**
     * Set admin role to user.
     *
     * @param id user id.
     * @return data transfer object userData.
     */
    @RequestMapping(value = Endpoints.USER_MAKE_ADMIN, method = RequestMethod.GET)
    @ResponseBody
    public void makeAdmin(@PathVariable String id) {
        long longId = Long.valueOf(id);
        userManager.makeAdmin(longId);
    }

    /**
     * Lock user.
     *
     * @param id user id.
     * @return data transfer object UserData.
     * @throws IOException
     * @throws AbstractBaseException
     */
    @RequestMapping(value = Endpoints.USER_LOCK, method = RequestMethod.GET)
    @ResponseBody
    public void lockUser(@PathVariable String id) throws IOException,
            AbstractBaseException {
        long longId = Long.valueOf(id);
        userManager.lockUser(longId);
    }

    /**
     * Unlock user.
     *
     * @param id user id.
     * @return data transfer object UserData.
     * @throws IOException
     * @throws AbstractBaseException
     */
    @RequestMapping(value = Endpoints.USER_UNLOCK, method = RequestMethod.GET)
    @ResponseBody
    public void unlockUser(@PathVariable String id) throws IOException,
            AbstractBaseException {
        long longId = Long.valueOf(id);
        userManager.unlockUser(longId);
    }

    /**
     * Update user profile data included bank data.
     *
     * @param userData the full user data.
     * @throws AbstractBaseException the exception.
     */
    @RequestMapping(value = Endpoints.USERS_UPDATE, method = {RequestMethod.POST})
    @ResponseBody
    public void updateUser(@RequestBody UserData userData, BindingResult bindingResult) throws AbstractBaseException {
        try {
            authorizationController.checkUserPermissions(userData.getId().toString());
        } catch (AbstractBaseException abe) {
            switch (abe.getCode()) {
                case AbstractBaseException.AUTHORIZATION_NOT_VALID:
                    ObjectError error = new ObjectError("authorization", messages
                            .getMessage("SystemHack"));
                    throw new BadRequestException(error.getDefaultMessage());
                default:
                    throw new RuntimeException("Unhandled error");
            }
        }

        // validate.
        validateProfileForm(userData, bindingResult);

        // find affected user
        User userToUpdate = userManager.findById(userData.getId());
        // change user data
        userToUpdate.setUsername(userData.getUsername());
        userToUpdate.setEmail(userData.getEmail());
        userToUpdate.setPhone(userData.getPhone());
        userToUpdate.setCompany(userData.getCompany());
        userToUpdate.setHomepage(userData.getHomepage());
        userToUpdate.setSwift(userData.getSwift());
        userToUpdate.setIban(userData.getIban());
        // update user
        userManager.update(userToUpdate);
    }

    private void validateProfileForm(UserData userProfileData,
                                     BindingResult bindingResult) throws BadRequestException {
        // validate
        try {
            UserUtility.getInstance().validate(userProfileData);
        } catch (AbstractBaseException abe) {
            switch (abe.getCode()) {
                case AbstractBaseException.USER_EMAIL_NOT_VALID:
                    bindingResult.addError(new ObjectError("email", messages
                            .getMessage("Email")));
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException("update user", bindingResult);
        }
    }

    /**
     * Gets user profile data included bank data.
     *
     * @param userId the user id
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.USERS_PROFILE_BY_USER_ID, method = {RequestMethod.GET})
    @ResponseBody
    public UserData getUserProfileData(@PathVariable String userId) throws AbstractBaseException {
        try {
            authorizationController.checkUserPermissions(userId);
        } catch (AbstractBaseException abe) {
            switch (abe.getCode()) {
                case AbstractBaseException.AUTHORIZATION_NOT_VALID:
                    ObjectError error = new ObjectError("authorization", messages
                            .getMessage("SystemHack"));
                    throw new BadRequestException(error.getDefaultMessage());
                default:
                    throw new RuntimeException("Unhandled error");
            }
        }

        User user = findUserById(userId);
        return UserUtility.getInstance().convertToUserData(user);
    }

    /**
     * Returns user by id.
     *
     * @param requestUserId user id
     * @return requestUserId
     * @throws AbstractBaseException validation exception.
     */
    private User findUserById(String requestUserId)
            throws AbstractBaseException {
        Long userId;
        try {
            userId = Long.parseLong(requestUserId);
        } catch (NumberFormatException nfe) {
            String msg = "The request parameter userId can not be parsed to Long value!";
            throw new BadRequestException(msg);
        }

        String msg = "The user profile data for user id " + requestUserId
                + " can not be found!";
        try {
            User user = userManager.findById(userId);
            if (user == null) {
                throw new NotFoundException(msg);
            }

            return user;

        } catch (Exception nfe) {
            throw new NotFoundException(msg);
        }
    }
}
