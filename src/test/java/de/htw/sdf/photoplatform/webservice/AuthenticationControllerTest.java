/*
 * Copyright (C) 2014 photoplatform-webservice-angular
 *
 */

package de.htw.sdf.photoplatform.webservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

import de.htw.sdf.photoplatform.common.*;
import de.htw.sdf.photoplatform.persistence.models.user.*;
import de.htw.sdf.photoplatform.webservice.controller.*;

/**
 * Test for user login register user
 *
 * @author <a href="mailto:s0531603@htw-berlin.de">Daniil Tomilow</a>
 */
public class AuthenticationControllerTest extends BaseTester
{

    @Autowired
    private AuthenticationController authenticationController;

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        insertDestData();
    }

    @Test
    public void testRegisterUser() throws Exception
    {
        User user = new User();
        user.setUserName("admin");
        user.setPassword("password");
        user.setEmail("admin@photo.de");

        // HMMM
        // mockMvc.perform(
        // post("/api/user/register")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(mapper.writeValueAsString(user))
        // .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testLoginUser() throws Exception
    {
        Map<String, String> login = new HashMap<>();
        login.put("username", "Vincent");
        login.put("password", "123");

        mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(login))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @After
    public void tearDown() throws Exception
    {
        clearTables();
    }
}
