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

    private static UserUtility ourInstance = new UserUtility();

    private UserUtility() {
    }

    public static UserUtility getInstance() {
        return ourInstance;
    }
    /**
     * Lassen Sie ,bitte, getInstance drin! Danke!
     *
     * 1) Singleton class can be extended. Polymorphism can save a lot of repetition.
     * 2) A Singleton class can implement an interface, which can come in handy when you want to separate implementation from API.
     * 3)Singleton can be extended. Static not.
     * 4)Singleton creation may not be threadsafe if it isn't implemented properly. Static not.
     * 5)Singleton can be passed around as an object. Static not.
     * 6)Singleton can be garbage collected. Static not.
     * 7)Singleton object stores in Heap but, static object stores in stack
     * 8)We can clone the object of Singleton but, we can not clone the static class object
     * 9)Singleton class follow the OOP(object oriented principles) but not static class
     * 10)Another advantage of a singleton is that it can easily be serialized, which may be necessary if you need to save its state to disc, or send it somewhere remotely.
     *
     * The big difference between a singleton and a bunch of static methods is that singletons can implement interfaces
     * (or derive from useful base classes, although that's less common IME), so you can pass around the singleton as
     * if it were "just another" implementation.
     *
     * Read more: http://javarevisited.blogspot.com/2013/03/difference-between-singleton-pattern-vs-static-class-java.html#ixzz3KpawX7FI
     */


    /**
     * Convert list of domain object user to list of transfer objects UserData.
     *
     * @param users list of users.
     * @return list of user data.
     */
    public List<UserData> convertToUserData(List<User> users) {
        List<UserData> result = new ArrayList<>();
        for (User user : users) {
            UserData userData = new UserData(user);
            result.add(userData);
        }

        return result;
    }
}
