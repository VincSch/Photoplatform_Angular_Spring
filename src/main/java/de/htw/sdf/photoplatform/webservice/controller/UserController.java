/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.NotFoundException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.UserData;
import de.htw.sdf.photoplatform.webservice.dto.UserProfileData;

/**
 * This controller present the REST user services.
 *
 * @author Sergej Meister
 */
@RestController
public class UserController extends BaseAPIController {

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
        return getResponseUserData(users);
    }

    @RequestMapping(value = Endpoints.USERS_DISABLED_BY_ROLE, method = RequestMethod.GET)
    @ResponseBody
    public List<UserData> getDisabledUsersByRole(@PathVariable String roleName)
        throws IOException, AbstractBaseException {
        if (roleName.trim().equals(Role.PHOTOGRAPHER)) {
            List<User> users = userManager.findPhotographToActivate();
            return getResponseUserData(users);
        } else {
            String msg = "The role (" + roleName
                + ") is not correct or get users for this role is not supported in this version!";
            throw new BadRequestException(msg);
        }
    }

    private List<UserData> getResponseUserData(List<User> users) {
        List<UserData> result = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserData userData = getResponseUserData(users.get(i));
            userData.setIndex(i + 1);
            result.add(userData);
        }

        return result;
    }
    
    @RequestMapping(value = Endpoints.USER_MAKE_ADMIN, method = RequestMethod.GET)
    @ResponseBody
    public UserData makeAdmin(@PathVariable String id) {
    	long longId = Long.valueOf(id);
    	User NewAdmin = userManager.makeAdmin(longId);
        return getResponseUserData(NewAdmin);
    }

    @RequestMapping(value = Endpoints.USER_LOCK, method = RequestMethod.GET)
    @ResponseBody
    public UserData lockUser(@PathVariable String id)
        throws IOException, AbstractBaseException {
        long longId = Long.valueOf(id);
        User lockedUser = userManager.lockUser(longId);
        return getResponseUserData(lockedUser);
    }

    @RequestMapping(value = Endpoints.USER_UNLOCK, method = RequestMethod.GET)
    @ResponseBody
    public UserData unlockUser(@PathVariable String id)
        throws IOException, AbstractBaseException {
        long longId = Long.valueOf(id);
        User lockedUser = userManager.unlockUser(longId);
        return getResponseUserData(lockedUser);
    }

    /**
     * Update user profile data included bank data.
     *
     * @param user
     *            the user
     *
     * @throws Exception
     *             the exception
     */
    @RequestMapping(value = Endpoints.USER_UPDATE, method = { RequestMethod.POST })
    @ResponseBody
    public void updateUser(@RequestBody User user) throws Exception {
        userManager.update(user);
    }

    /**
     * Gets user profile data included bank data.
     *
     * @param userId the user id
     * @throws Exception the exception
     */
    @RequestMapping(value = Endpoints.USERS_PROFILE_BY_USER_ID, method = { RequestMethod.GET })
    @ResponseBody
    public UserProfileData getUserProfileData(@PathVariable String userId) throws AbstractBaseException {

        Long requestId ;
        try {
            requestId = Long.parseLong(userId);
        }catch(NumberFormatException nfe){
            String msg = "The request parameter userId can not be parsed to Long value!";
            throw new BadRequestException(msg);
        }

        try {
            User user = userManager.findById(requestId);
            return getResponseUserProfileData(user);
        }catch(Exception nfe){
            String msg = "The user profile data for user id " + userId + "can not be found!";
            throw new NotFoundException(msg);
        }
    }

    private UserData getResponseUserData(User user) {
        UserData result = new UserData();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setBanned(!user.isAccountNonLocked());
        result.setEnabled(user.isEnabled());
        Boolean isAdmin = userManager.isUserAdmin(user);
        result.setAdmin(isAdmin);
        return result;
    }


    private UserProfileData getResponseUserProfileData(User user){
        UserProfileData userProfileData = new UserProfileData();
        userProfileData.setId(user.getId());
        userProfileData.setBankId(user.getUserBank().getId());
        userProfileData.setProfileId(user.getUserProfile().getId());
        userProfileData.setUsername(user.getUsername());
        userProfileData.setEmail(user.getEmail());
        userProfileData.setFirstName(user.getUserProfile().getFirstName());
        userProfileData.setSurname(user.getUserProfile().getSurname());
        userProfileData.setAddress(user.getUserProfile().getAddress());
        userProfileData.setPhone(user.getUserProfile().getPhone());
        //TODO: should be cast to date format!
        //userProfileData.setBirthday(user.getUserProfile().getBirthday().toString());
        userProfileData.setCompany(user.getUserProfile().getCompany());
        userProfileData.setHomepage(user.getUserProfile().getHomepage());
        userProfileData.setShowBankData(userManager.isRoleIncluded(user, Role.PHOTOGRAPHER));
        userProfileData.setReceiver(user.getUserBank().getReceiver());
        userProfileData.setIban(user.getUserBank().getIban());
        userProfileData.setBic(user.getUserBank().getBic());
        return userProfileData;
    }
}
