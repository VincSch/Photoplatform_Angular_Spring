/*
 * Copyright (C) 2014 photoplatform-webservice-angular
 *
 */

package de.htw.sdf.photoplatform.webservice;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.webservice.dto.UserCredential;
import de.htw.sdf.photoplatform.webservice.dto.UserRegister;

/**
 * Test for user login register user
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class AuthenticationControllerTest extends BaseTester {

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        insertTestData();
    }

    @Test
    @Ignore
    public void testRegisterAndLoginUser() throws Exception {
        String username = "test";
        String email = "test@test.de";
        String password = "password";

        UserRegister userRegister = new UserRegister();
        userRegister.setUsername(username);
        userRegister.setEmail(email);
        userRegister.setPassword(password);

        // Register User
        mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRegister))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(username);
        userCredential.setPassword(password);

        mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
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

        // Register User
        mockMvc.perform(
            post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRegister))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(username);
        userCredential.setPassword(password);

        mockMvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userCredential))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        User user = userDAO.findByUserName(username);
        user.setAccountNonLocked(false);
        userDAO.update(user);

        mockMvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userCredential))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        user.setAccountNonLocked(true);
        userDAO.update(user);

        mockMvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userCredential))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void testRegisterUserWithInvalidEmail() throws Exception {
        UserRegister userRegister = new UserRegister();
        userRegister.setUsername("testInvalid");
        userRegister.setEmail("invalidemail.de");
        userRegister.setPassword("1234");

        is(2);

        mockMvc
            .perform(
                post("/api/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(userRegister))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @After
    public void tearDown() throws Exception {
        clearTables();
    }
}
