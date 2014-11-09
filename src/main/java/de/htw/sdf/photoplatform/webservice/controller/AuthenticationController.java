/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.security.TokenUtils;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.UserCredential;
import de.htw.sdf.photoplatform.webservice.dto.UserRegister;

/**
 * This controller generates the token that must be present in subsequent REST invocations.
 *
 * @author <a href="mailto:philip@sorst.net">Philip W. Sorst</a>
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@RestController
public class AuthenticationController extends BaseAPIController {

    private TokenUtils tokenUtils = new TokenUtils();

    @Resource
    @Qualifier(value = "myAuthManager")
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private UserManager userManager;

    /**
     * Login user.
     *
     * @param userCredential
     *            the login for user
     *
     * @return the user
     *
     * @throws IOException
     *             the io exception
     */
    @RequestMapping(value = Endpoints.USER_LOGIN, method = RequestMethod.POST)
    public User login(@Valid @RequestBody UserCredential userCredential, BindingResult bindingResult)
            throws IOException, AbstractBaseException
    {
        if (bindingResult.hasErrors())
        {
            throw new BadRequestException("login rejected", bindingResult);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userCredential.getUsername(),
                userCredential.getPassword());

        Authentication authentication;

        try
        {
            authentication = authenticationManager.authenticate(token);
        }
        // User is disabled
        catch (DisabledException | LockedException | BadCredentialsException ex)
        {
            bindingResult.addError(new ObjectError("user", "disabled"));
            throw new BadRequestException("login", bindingResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) this.userDetailsService.loadUserByUsername(userCredential.getUsername());
        user.setSecToken(tokenUtils.createToken(user));

        return user;
    }

    /**
     * Register user.
     *
     * @param userRegister
     *            user data for register
     *
     * @throws IOException
     *             the io exception
     */
    @RequestMapping(value = Endpoints.USER_REGISTER, method = RequestMethod.POST)
    public void register(@Valid @RequestBody UserRegister userRegister, BindingResult bindingResult)
            throws Exception
    {
        if (bindingResult.hasErrors())
        {
            // User input errors
            log.info("-- register user fail: username = \"" + userRegister.getUsername()
                    + "; email = \"" + userRegister.getEmail() + "; password=\"**********\";");

            throw new BadRequestException("register", bindingResult);
        }

        try
        {
            // Try to register user
            userManager.registerUser(
                    userRegister.getUsername(),
                    userRegister.getEmail(),
                    userRegister.getPassword());
        }
        catch (ManagerException ex)
        {
            switch (ex.getCode())
            {
            case AbstractBaseException.USER_USERNAME_EXISTS:
                bindingResult.addError(new ObjectError("email", messages
                        .getMessage("user.username_exists")));
                break;

            case AbstractBaseException.USER_EMAIL_EXISTS:
                bindingResult.addError(new ObjectError("email", messages
                        .getMessage("user.email_exists")));
                break;

            default:
                throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException("register", bindingResult);
        }
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
    @RequestMapping(value = Endpoints.USER_UPDATE, method = { RequestMethod.POST })
    @ResponseBody
    public void updateUser(@RequestBody User user) throws Exception {
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
        return userManager.findByName(name);
    }

}
