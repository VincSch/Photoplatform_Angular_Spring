/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import de.htw.sdf.photoplatform.common.Messages;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.WebFormException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.dto.UserData;
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
     * Convert list of domain object user to list of transfer objects UserData.
     *
     * @param users list of users.
     * @return list of user data.
     */
    public static List<UserData> convertToUserData(List<User> users) {
        List<UserData> result = new ArrayList<>();
        int i = 0;
        for (User user: users) {
            UserData userData = new UserData(user);
            result.add(userData);
        }

        return result;
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
