/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.htw.sdf.photoplatform.common.Messages;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.WebFormException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.webservice.dto.UserData;
import de.htw.sdf.photoplatform.webservice.dto.UserProfileData;

/**
 * This is a help class for user controller.
 *
 * Convert domain objects User, UserBank, UserProfile to corresponded data transfer objects
 * UserData,UserProfileData. Validate input data.
 *
 * @author Sergej Meister
 */
@Component
public class UserUtility {

    private static Messages messages;

    private static UserManager userManager;

    private static UserUtility ourInstance = new UserUtility();

    private UserUtility() {
    }

    public static UserUtility getInstance() {
        return ourInstance;
    }

    /**
     * Convert domain object User to data transfer object UserProfileData.
     *
     * @param user domain object user.
     * @return UserProfileData.
     */
    public UserProfileData convertToUserProfileData(User user) {
        UserProfileData userProfileData = new UserProfileData();
        userProfileData.setId(user.getId());
        userProfileData.setUsername(user.getUsername());
        userProfileData.setEmail(user.getEmail());
        if(user.getUserProfile()!=null){
            userProfileData.setProfileId(user.getUserProfile().getId());
            userProfileData.setFirstname(user.getUserProfile().getFirstName());
            userProfileData.setSurname(user.getUserProfile().getSurname());
            userProfileData.setAddress(user.getUserProfile().getAddress());
            userProfileData.setPhone(user.getUserProfile().getPhone());
            userProfileData.setBirthday(user.getUserProfile().getBirthday());
            userProfileData.setCompany(user.getUserProfile().getCompany());
            userProfileData.setHomepage(user.getUserProfile().getHomepage());
        }

        if(user.getUserBank()!= null){
            userProfileData.setBankId(user.getUserBank().getId());
            userProfileData.setReceiver(user.getUserBank().getReceiver());
            userProfileData.setIban(user.getUserBank().getIban());
            userProfileData.setBic(user.getUserBank().getBic());
        }

        userProfileData.setShowBankData(userManager.isRoleIncluded(user, Role.PHOTOGRAPHER));

        return userProfileData;
    }

    /**
     * Convert domain object user to data transfer object UserData.
     *
     * @param user domain object user.
     * @return data transfer object UserData.
     */
    public UserData convertToUserData(User user) {
        UserData result = new UserData();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setBanned(!user.isAccountNonLocked());
        result.setEnabled(user.isEnabled());
        Boolean isAdmin = userManager.isUserAdmin(user);
        result.setAdmin(isAdmin);
        return result;
    }

    /**
     * Convert list of domain object user to list of transfer objects UserData.
     *
     * @param users list of users.
     * @return list of user data.
     */
    public List<UserData> convertToUserData(List<User> users) {
        List<UserData> result = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserData userData = convertToUserData(users.get(i));
            userData.setIndex(i + 1);
            result.add(userData);
        }

        return result;
    }

    /**
     * Validation of user Profile input data.
     *
     * @param userProfileData input data.
     * @throws AbstractBaseException exception.
     */
    public void validate(UserProfileData userProfileData) throws AbstractBaseException {
        validateEmail(userProfileData.getEmail());
        validateBirthday(userProfileData.getBirthday());
        validateBankData(userProfileData.getBic(),userProfileData.getIban());
    }


    private void validateEmail(String value) throws AbstractBaseException {
        if(value == null){
            throw new WebFormException(AbstractBaseException.USER_EMAIL_NOT_VALID);
        }else{
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(value.trim());
            if(!matcher.matches()){
                throw new WebFormException(AbstractBaseException.USER_EMAIL_NOT_VALID);
            }
        }
    }

    private void validateBirthday(String value) throws AbstractBaseException {
        if(value == null) {
            return;
        }

        String formatValue = messages.getMessage("DateFormat");
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatValue);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(value.trim());
        } catch (ParseException e) {
            throw new WebFormException(AbstractBaseException.DATE_FORMAT_NOT_VALID);
        }
    }

    private Boolean validateBankData(String bic, String iban) throws AbstractBaseException {
        //is not relevant for uni project :).
        return true;
    }

    @Autowired(required = true)
    public void setUserManager(UserManager userManager) {
        UserUtility.userManager = userManager;
    }

    @Autowired(required = true)
    public void setMessages(Messages messages) {
        UserUtility.messages = messages;
    }
}
