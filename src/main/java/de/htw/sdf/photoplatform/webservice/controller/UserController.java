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
import de.htw.sdf.photoplatform.persistence.models.Role;
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

    /**
     * GET list of enabled users between start an count.
     *
     * @param start start parameter.
     * @param count list size.
     * //@param bindingResult
     * @return list of enabled users between start an count.
     * @throws IOException
     * @throws AbstractBaseException
     */
    @RequestMapping(value = Endpoints.USERS_START_COUNT, method = RequestMethod.GET)
    @ResponseBody
    public List<UserData> getEnabledUsers(@PathVariable int start,@PathVariable int count)throws IOException, AbstractBaseException
    {
        List<User> users = userManager.find(start,count);
        return getResponseUserData(users);
    }

    @RequestMapping(value = Endpoints.USERS_DISABLED_BY_ROLE, method = RequestMethod.GET)
    @ResponseBody
    public List<UserData> getDisabledUsersByRole(@PathVariable String roleName)throws IOException, AbstractBaseException
    {
        List<User> users = new ArrayList<>();
        if(roleName.trim().equals(Role.PHOTOGRAPHER)){
            users = userManager.findPhotographToActivate();
        }else{
            String msg = "The role (" + roleName + ") is not correct or get users for this role is not supported in this version!";
//            bindingResult.addError(new ObjectError("getUsers", msg));
//            throw new BadRequestException("getUsers", bindingResult);
        }

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
        result.setEnabled(user.isEnabled());
        Boolean isAdmin = userManager.isUserAdmin(user);
        result.setAdmin(isAdmin);
        return result;
    }

}
