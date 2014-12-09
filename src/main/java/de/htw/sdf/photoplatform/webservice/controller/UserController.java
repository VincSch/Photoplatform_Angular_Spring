/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.NotFoundException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.PhotographerData;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.security.TokenUtils;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.BecomePhotographer;
import de.htw.sdf.photoplatform.webservice.dto.UserData;
import de.htw.sdf.photoplatform.webservice.dto.UserPasswordChange;
import de.htw.sdf.photoplatform.webservice.util.UserUtility;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.validation.Valid;
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

    @Resource
    @Qualifier(value = "myAuthManager")
    private AuthenticationManager authenticationManager;

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
        return UserUtility.convertToUserData(users);
    }

    /**
     * Return list of users who wants to be a photographer.
     *
     * @return list of all disabled users.
     * @throws IOException           input output exception.
     * @throws AbstractBaseException abstract exception.
     */
    @RequestMapping(value = Endpoints.USERS_BECOME_PHOTOGRAPHERS, method = RequestMethod.GET)
    @ResponseBody
    public List<UserData> getBecomePhotographers()
            throws IOException, AbstractBaseException {
        List<User> users = userManager.getPhotographersToActivate();

        return UserUtility.convertToUserData(users);
    }


    /**
     * Become Photographer.
     *
     * @return data transfer object userData.
     */
    @RequestMapping(value = Endpoints.USER_BECOME_PHOTOGRAPHER, method = RequestMethod.POST)
    @ResponseBody
    public boolean becomePhotographer(@Valid @RequestBody BecomePhotographer data, BindingResult bindingResult)
            throws BadRequestException, ManagerException {
        if (bindingResult.hasErrors()) {
            // User input errors
            throw new BadRequestException("becomePhotographer", bindingResult);
        }

        User user = getAuthenticatedUser();

        return userManager.becomePhotographer(user.getId(), data.getCompany(), data.getPhone(), data.getHomepage(),
                data.getPaypalID(), data.getIban(), data.getSwift());
    }


    /**
     * enable user with photographer role.
     *
     * @param json user id.
     * @return data transfer object userData.
     */
    @RequestMapping(value = Endpoints.USER_ENABLE_PHOTOGRAPHER, method = RequestMethod.POST)
    @ResponseBody
    public boolean enablePhotographer(@RequestBody String json) throws ManagerException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        Long userId = mapper.convertValue(node.get("userId"),
                Long.class);

        userManager.enablePhotographer(userId);

        return true;
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
    @RequestMapping(value = Endpoints.USERS_UPDATE, method = {
            RequestMethod.POST})
    @ResponseBody
    public void updateUser(@Valid @RequestBody UserData userData,
                           BindingResult bindingResult)
            throws AbstractBaseException {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException("updateUser", bindingResult);
        }

        try {
            authorizationController
                    .checkUserPermissions(userData.getId().toString());
        } catch (AbstractBaseException abe) {
            switch (abe.getCode()) {
                case AbstractBaseException.AUTHORIZATION_NOT_VALID:
                    ObjectError error = new ObjectError("authorization",
                            messages
                                    .getMessage("SystemHack"));
                    throw new BadRequestException(error.getDefaultMessage());
                default:
                    throw new RuntimeException("Unhandled error");
            }
        }

        // find affected user
        User userToUpdate = userManager.findById(userData.getId());
        // change user data
        userToUpdate.setUsername(userData.getEmail());
        userToUpdate.setFirstName(userData.getFirstName());
        userToUpdate.setLastName(userData.getLastName());
        PhotographerData data = new PhotographerData();
        data.setPhone(userData.getPhone());
        data.setCompany(userData.getCompany());
        data.setHomepage(userData.getHomepage());
        data.setSwift(userData.getSwift());
        data.setIban(userData.getIban());
        data.setPaypalID(userData.getPaypalID());
        userToUpdate.setPhotographerData(data);
        userManager.update(userToUpdate);
    }

    /**
     * Gets user profile data included bank data.
     *
     * @param userId the user id
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.USERS_PROFILE_BY_USER_ID, method = {
            RequestMethod.GET})
    @ResponseBody
    public UserData getUserProfileData(@PathVariable String userId)
            throws AbstractBaseException {
        try {
            authorizationController.checkUserPermissions(userId);
        } catch (AbstractBaseException abe) {
            switch (abe.getCode()) {
                case AbstractBaseException.AUTHORIZATION_NOT_VALID:
                    ObjectError error = new ObjectError("authorization",
                            messages
                                    .getMessage("SystemHack"));
                    throw new BadRequestException(error.getDefaultMessage());
                default:
                    throw new RuntimeException("Unhandled error");
            }
        }

        User user = findUserById(userId);
        return new UserData(user);
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

    /**
     * Update user password.
     *
     * @param userPwData Passworddata containing old password, new password and confirmation password.
     * @throws AbstractBaseException the exception.
     */
    @RequestMapping(value = Endpoints.USERS_CHANGE_PASSWORD, method = {
            RequestMethod.POST})
    public UserData changePassword(@Valid @RequestBody UserPasswordChange userPwData,
                           BindingResult bindingResult) throws Exception {

        // Check if password match
        if (!userPwData.getNewPassword().equals(
                userPwData.getPasswordConfirm())) {
            bindingResult
                    .addError(new FieldError("changePassword", "passwordConfirm",
                            messages.getMessage("Password.confirm")));
        }

        if (bindingResult.hasErrors()) {
            throw new BadRequestException("changePassword", bindingResult);
        }

        //get the user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        //try to authenticate user, to check if old password was correct
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authenticatedUser.getUsername(), userPwData.getPassword());

        Authentication auth;
        try {
            auth = authenticationManager.authenticate(token);
        } catch (DisabledException | LockedException | BadCredentialsException  | NoResultException ex) {
            bindingResult.addError(new FieldError("changePassword", "password", messages
                    .getMessage("Password.confirm")));
            throw new BadRequestException("changePassword", bindingResult);
        }

        // change user password data
        authenticatedUser.setPassword(new BCryptPasswordEncoder().encode(userPwData.getNewPassword()));
        userManager.update(authenticatedUser);

        // set the new token
        SecurityContextHolder.getContext().setAuthentication(auth);
        authenticatedUser.setSecToken(TokenUtils.createToken(authenticatedUser));

        return new UserData(authenticatedUser);
    }
}
