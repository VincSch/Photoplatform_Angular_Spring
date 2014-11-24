/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import de.htw.sdf.photoplatform.persistence.models.UserBank;
import de.htw.sdf.photoplatform.persistence.models.UserProfile;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.UserData;
import de.htw.sdf.photoplatform.webservice.dto.UserProfileData;
import de.htw.sdf.photoplatform.webservice.util.UserUtility;

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
     *Return list of all disabled users by id.
     *
     * @param roleName role name.
     * @return list of all disabled users.
     * @throws IOException input output exception.
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
            String msg = "The role (" + roleName
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
    public UserData enablePhotograph(@PathVariable String id) {
    	long longId = Long.valueOf(id);
    	User Photograph = userManager.enablePhotograph(longId);
        return UserUtility.getInstance().convertToUserProfileData(Photograph);
    }


	/**
 	* Set admin role to user.
 	*
 	* @param id user id.
 	* @return data transfer object userData.
 	*/
    @RequestMapping(value = Endpoints.USER_MAKE_ADMIN, method = RequestMethod.GET)
    @ResponseBody
    public UserData makeAdmin(@PathVariable String id) {
        long longId = Long.valueOf(id);
        User NewAdmin = userManager.makeAdmin(longId);
        return UserUtility.getInstance().convertToUserProfileData(NewAdmin);
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
    public UserData lockUser(@PathVariable String id)
        throws IOException, AbstractBaseException {
        long longId = Long.valueOf(id);
        User lockedUser = userManager.lockUser(longId);
        return UserUtility.getInstance().convertToUserProfileData(lockedUser);
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
    public UserData unlockUser(@PathVariable String id)
        throws IOException, AbstractBaseException {
        long longId = Long.valueOf(id);
        User lockedUser = userManager.unlockUser(longId);
        return UserUtility.getInstance().convertToUserProfileData(lockedUser);
    }

    /**
     * Update user profile data included bank data.
     *
     * @param userProfileData the full user data.
     *
     * @throws AbstractBaseException the exception.
     */
    @RequestMapping(value = Endpoints.USERS_UPDATE, method = { RequestMethod.POST })
    @ResponseBody
    public void updateUser(@RequestBody UserProfileData userProfileData,BindingResult bindingResult)  throws AbstractBaseException {

        //validate.
        validateProfileForm(userProfileData, bindingResult);

        //find affected user
        User userToUpdate = userManager.findById(userProfileData.getId());
        //change user data
        userToUpdate.setUsername(userProfileData.getUsername());
        userToUpdate.setEmail(userProfileData.getEmail());

        //Change profile data
        UserProfile profile = userToUpdate.getUserProfile();
        if(profile==null){
            profile = new UserProfile(userToUpdate);
        }
        profile.setFirstName(userProfileData.getFirstName());
        profile.setSurname(userProfileData.getSurname());
        profile.setAddress(userProfileData.getAddress());
        profile.setPhone(userProfileData.getPhone());
        profile.setBirthday(userProfileData.getBirthday());
        profile.setCompany(userProfileData.getCompany());
        profile.setHomepage(userProfileData.getHomepage());

        //Change bank data
        UserBank bank = userToUpdate.getUserBank();
        if(bank == null){
            bank = new UserBank(userToUpdate);
        }
        bank.setReceiver(userProfileData.getReceiver());
        bank.setBic(userProfileData.getBic());
        bank.setIban(userProfileData.getIban());

        //update user
        userManager.update(userToUpdate,profile,bank);
    }

    private void validateProfileForm(UserProfileData userProfileData,BindingResult bindingResult) throws BadRequestException {
        //validate
        try {
            UserUtility.getInstance().validate(userProfileData);
        } catch (AbstractBaseException abe) {
            switch (abe.getCode())
            {
                case AbstractBaseException.USER_EMAIL_NOT_VALID:
                    bindingResult.addError(new ObjectError("email", messages
                            .getMessage("Email")));
                    break;

                case AbstractBaseException.DATE_FORMAT_NOT_VALID:
                    bindingResult.addError(new ObjectError("BirthdayNotValid", messages
                            .getMessage("BirthdayNotValid")));
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
    @RequestMapping(value = Endpoints.USERS_PROFILE_BY_USER_ID, method = { RequestMethod.GET })
    @ResponseBody
    public UserProfileData getUserProfileData(@PathVariable String userId) throws AbstractBaseException {
        try{
            authorizationController.checkUserPermissions(userId);
        }catch(AbstractBaseException abe){
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
        return UserUtility.getInstance().convertToUserProfileData(user);
    }

    /**
     * Returns user by id.
     *
     * @param requestUserId user id
     * @return requestUserId
     * @throws AbstractBaseException validation exception.
     */
    private User findUserById(String requestUserId) throws AbstractBaseException {
        Long userId ;
        try {
            userId = Long.parseLong(requestUserId);
        }catch(NumberFormatException nfe){
            String msg = "The request parameter userId can not be parsed to Long value!";
            throw new BadRequestException(msg);
        }

        try {
            return userManager.findById(userId);
        }catch(Exception nfe){
            String msg = "The user profile data for user id " + requestUserId + " can not be found!";
            throw new NotFoundException(msg);
        }
    }
}
