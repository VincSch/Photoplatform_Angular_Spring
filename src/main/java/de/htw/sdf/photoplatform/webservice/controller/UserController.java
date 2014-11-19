/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.UserData;

/**
 * This controller present the REST user services.
 *
 * @author Sergej Meister
 */
@RestController
public class UserController extends BaseAPIController {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private UserManager userManager;

    @RequestMapping(value = Endpoints.USERS_START_COUNT, method = RequestMethod.GET)
    @ResponseBody
    public List<UserData> getUsers(@PathVariable int start,@PathVariable int count)throws IOException, AbstractBaseException
    {
        List<User> users = userManager.find(start,count);
        return getResponseUserData(users);
    }

    private List<UserData> getResponseUserData(List<User> users){
        List<UserData> result = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            UserData userData = getResponseUserData(users.get(i));
            userData.setIndex(i+1);
            result.add(userData);
        }

        return result;
    }

    private UserData getResponseUserData(User user){
        UserData result = new UserData();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setBanned(!user.isAccountNonLocked());
        Boolean isAdmin = userManager.isUserAdmin(user);
        result.setAdmin(isAdmin);
        return result;
    }

}
