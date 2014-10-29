/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.User;
import de.htw.sdf.photoplatform.security.TokenUtils;
import de.htw.sdf.photoplatform.webservice.common.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.common.Endpoints;

import javax.annotation.Resource;

/**
 * This controller generates the token that must be present in subsequent REST
 * invocations.
 *
 * @author <a href="mailto:philip@sorst.net">Philip W. Sorst</a>
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@RestController
public class AuthenticationController extends BaseAPIController
{

    private TokenUtils tokenUtils = new TokenUtils();

    @Autowired
    @Qualifier(value = "myAuthManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Resource
    private UserManager userManager;

    /**
     * Login user.
     * 
     * @param param
     *            the param
     * 
     * @return the user
     * 
     * @throws JsonProcessingException
     *             the json exception
     * @throws IOException
     *             the io exception
     */
    @RequestMapping(value = Endpoints.USER_LOGIN, method = {RequestMethod.POST})
    public User login(@RequestBody String param)
            throws JsonProcessingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(param);
        String username = mapper.convertValue(
                node.get("username"),
                String.class);
        String password = mapper.convertValue(
                node.get("password"),
                String.class);

        try
        {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    password);

            Authentication authentication = this.authenticationManager
                    .authenticate(token);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }

        User details = (User) this.userDetailsService
                .loadUserByUsername(username);

        details.setSecToken(tokenUtils.createToken(details));
        return details;
    }

    /**
     * Register user.
     * 
     * @param user
     *            the user
     * 
     * @throws IOException
     *             the io exception
     */
    @RequestMapping(value = Endpoints.USER_REGISTER, method = {RequestMethod.POST})
    public void register(@RequestBody User user) throws IOException
    {
        userManager.create(user);
    }

    /**
     * Update user.
     * 
     * @param user
     *            the user
     * 
     * @throws Exception
     *             the exception
     */
    @RequestMapping(value = Endpoints.USER_UPDATE, method = {RequestMethod.POST})
    @ResponseBody
    public void updateUser(@RequestBody User user) throws Exception
    {
        userManager.update(user);
    }

    /**
     * Recipe by name.
     * 
     * @param name
     *            the name
     * 
     * @return the user
     */
    @RequestMapping(value = Endpoints.USER_BY_NAME, method = RequestMethod.GET)
    @ResponseBody
    public User recipeByName(@PathVariable String name)
    {
        User user = userManager.findByName(name);
        return user;
    }

    /**
     * Get amount of recipes.
     * 
     * @param name
     *            the name
     * 
     * @return the amount of recipes
     */
    @RequestMapping(value = Endpoints.USER_GET_RECIPE_COUNT, method = RequestMethod.GET)
    @ResponseBody
    public Long getAmountOfRecipes(@PathVariable String name)
    {
        return userManager.getRecipeAmount(name);
    }

    /**
     * Get Amount of recipe in book.
     * 
     * @param name
     *            the name
     * 
     * @return the amount of recipe in book
     */
    @RequestMapping(value = Endpoints.USER_GET_RECIPEBOOK_COUNT, method = RequestMethod.GET)
    @ResponseBody
    public Long getAmountOfRecipeBooks(@PathVariable String name)
    {
        return userManager.getRecipeBookAmount(name);
    }
}
