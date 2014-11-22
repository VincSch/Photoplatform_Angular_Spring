/**
 *
 */
package de.htw.sdf.photoplatform.webservice;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;

/**
 * Tests for users services.
 */
public class UserControllerTest extends BaseTester {

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        insertTestData();
    }

    @After
    public void tearDown() throws Exception {
        clearTables();
    }

    @Test
    public void testGetEnabledUsers() throws Exception {

        mockMvc.perform(
                get("/api/users/0/100")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isOk());
        mockMvc.perform(
                get("/api/users/0/")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isNotFound());
    }

    @Test
    public void testGetDisabledUsersByRole() throws Exception {
        mockMvc.perform(
                get("/api/users/disabled/" + Role.PHOTOGRAPHER)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isOk());

        mockMvc.perform(
                get("/api/users/disabled/" + Role.ADMIN)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isBadRequest());

        mockMvc.perform(
                get("/api/users/disabled/" + Role.CUSTOMER)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isBadRequest());
    }

    @Test
    public void testUserProfileData() throws Exception {
        String notExistId = "0" ;
        String requestNotExistId= Endpoints.API_PREFIX + Endpoints.USERS_PROFILE_BY_USER_ID.replace("{userId}",notExistId);

        mockMvc.perform(
                get(requestNotExistId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isNotFound());

        User sergejUser = userDAO.findByUserName("Sergej");
        String requestSergej = Endpoints.API_PREFIX + Endpoints.USERS_PROFILE_BY_USER_ID.replace("{userId}",sergejUser.getId().toString());
        mockMvc.perform(
                get(requestSergej)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isOk());

        String characterId = "a" ;
        String requestWithCharacter = Endpoints.API_PREFIX + Endpoints.USERS_PROFILE_BY_USER_ID.replace("{userId}",characterId);
        mockMvc.perform(
                get(requestWithCharacter)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isBadRequest());

    }
}
