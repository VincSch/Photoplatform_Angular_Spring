/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import de.htw.sdf.photoplatform.common.Messages;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.WebFormException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserRole;
import de.htw.sdf.photoplatform.webservice.dto.response.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a help class for user controller.
 * <p/>
 * Convert domain objects User, UserBank, UserProfile to corresponded data
 * transfer objects UserData,UserProfileData. Validate input data.
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
        result.setAdmin(userManager.isUserAdmin(user));
        result.setPassword(user.getPassword());
        result.setSecToken(user.getSecToken());
        result.setEmail(user.getEmail());
        result.setCompany(user.getCompany());
        result.setPhone(user.getPhone());
        result.setHomepage(user.getHomepage());
        result.setSwift(user.getSwift());
        result.setIban(user.getIban());
        List<Role> authorities = new ArrayList<Role>();
        for (UserRole userRole : user.getUserRoles()) {
            authorities.add(userRole.getRole());
        }
        result.setAuthorities(authorities);
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
    public void validate(UserData userProfileData)
            throws AbstractBaseException {
        validateEmail(userProfileData.getEmail());
    }

    private void validateEmail(String value) throws AbstractBaseException {
        if (value == null) {
            throw new WebFormException(
                    AbstractBaseException.USER_EMAIL_NOT_VALID);
        } else {
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(value.trim());
            if (!matcher.matches()) {
                throw new WebFormException(
                        AbstractBaseException.USER_EMAIL_NOT_VALID);
            }
        }
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
