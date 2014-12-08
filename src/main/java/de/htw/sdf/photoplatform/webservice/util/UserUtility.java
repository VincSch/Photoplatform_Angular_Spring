/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.dto.UserData;

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

    private UserUtility() {
    }

    /**
     * Convert list of domain object user to list of transfer objects UserData.
     *
     * @param users list of users.
     * @return list of user data.
     */
    public static List<UserData> convertToUserData(List<User> users) {
        List<UserData> result = new ArrayList<>();
        for (User user : users) {
            UserData userData = new UserData(user);
            result.add(userData);
        }

        return result;
    }
}
