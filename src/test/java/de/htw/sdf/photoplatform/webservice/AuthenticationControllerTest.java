/*
 * Copyright (C) 2014 photoplatform-webservice-angular
 *
 */

package de.htw.sdf.photoplatform.webservice;

import de.htw.sdf.photoplatform.common.BaseAPITester;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.dto.request.UserCredential;
import de.htw.sdf.photoplatform.webservice.dto.request.UserRegister;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for user login register user
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class AuthenticationControllerTest extends BaseAPITester {//BaseTester {

    @Before
    public void setUp() throws Exception {
        initAPITest();
    }

    @Test
    public void testRegisterAndLoginUser() throws Exception {
        String username = "test";
        String email = "test@test.de";
        String password = "password";

        UserRegister userRegister = new UserRegister();
        userRegister.setUsername(username);
        userRegister.setEmail(email);
        userRegister.setPassword(password);
        userRegister.setPasswordConfirm(password);

        // Register User
        mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRegister))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());

        // Login user
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(username);
        userCredential.setPassword(password);

        mockMvc.perform(
                post("/api/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());
    }

    /**
     * creates a new user register it then login to check normal login behaviour
     * again. Afterwards the expected test to check the locked user can't login
     * after he is locked. Set the user Enabled and the login will work.
     *
     * @throws Exception
     */
    @Test
    public void testLockedUser() throws Exception {
        String username = "testLocked";
        String email = "testLocked@test.de";
        String password = "password";

        UserRegister userRegister = new UserRegister();
        userRegister.setUsername(username);
        userRegister.setEmail(email);
        userRegister.setPassword(password);
        userRegister.setPasswordConfirm(password);

        // Register User
        mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRegister))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());

        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(username);
        userCredential.setPassword(password);

        mockMvc.perform(
                post("/api/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());

        User user = userDAO.findByUserName(username);
        user.setAccountNonLocked(false);
        userDAO.update(user);

        mockMvc.perform(
                post("/api/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isBadRequest());

        user.setAccountNonLocked(true);
        userDAO.update(user);

        mockMvc.perform(
                post("/api/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());
    }

    @Test
    public void testRegisterUserWithInvalidEmail() throws Exception {
        UserRegister userRegister = new UserRegister();
        userRegister.setUsername("testInvalid");
        userRegister.setEmail("invalidemail.de");
        userRegister.setPassword("1234");
        userRegister.setPasswordConfirm("1234");

        mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRegister))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email", notNullValue()));
    }

    @Test
    public void testRegisterUserConfirmPasswordFail() throws Exception {
        UserRegister userRegister = new UserRegister();
        userRegister.setUsername("testInvalid");
        userRegister.setEmail("valid@mail.de");
        userRegister.setPassword("1234");
        userRegister.setPasswordConfirm("1wsd234");

        MvcResult result = mockMvc
                .perform(
                        post("/api/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        mapper.writeValueAsString(userRegister))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.passwordConfirm", notNullValue()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @After
    public void tearDown() throws Exception {
        cancel();
    }
}
