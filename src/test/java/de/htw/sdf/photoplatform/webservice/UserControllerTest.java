/**
 *
 */
package de.htw.sdf.photoplatform.webservice;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.webservice.controller.UserController;
import de.htw.sdf.photoplatform.webservice.dto.UserProfileData;

/**
 * Tests for users services.
 */
public class UserControllerTest extends BaseTester {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserController userController;

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

    @Test
    @Ignore("Fail by travis-ci!")
    public void testUpdateUserProfileData() throws Exception {
        //Init test data
        User sergejUser = userDAO.findByUserName("Sergej");
        String sergejId = sergejUser.getId().toString();
        UserProfileData sergejProfileData = userController.getUserProfileData(sergejId);
        String newUserName="sergejNew";
        String newEmail = "sergejnew@tst.de";
        String newAddress = "russian today";
        String newReceiver = "SergejNew Meister";

        //Do Test
        sergejProfileData.setUsername(newUserName);
        sergejProfileData.setEmail(newEmail);
        sergejProfileData.setAddress(newAddress);
        sergejProfileData.setReceiver(newReceiver);

        String apiUpdateUrl = Endpoints.API_PREFIX + Endpoints.USERS_UPDATE;
        mockMvc.perform(
                post(apiUpdateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sergejProfileData))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        UserProfileData updatedProfileData = userController.getUserProfileData(sergejId);
        Assert.assertTrue(updatedProfileData.getUsername().equals(newUserName));
        Assert.assertTrue(updatedProfileData.getEmail().equals(newEmail));
        Assert.assertTrue(updatedProfileData.getAddress().equals(newAddress));
        Assert.assertTrue(updatedProfileData.getReceiver().equals(newReceiver));


        User peterUser = userDAO.findByUserName("Peter");
        String peterId = peterUser.getId().toString();
        UserProfileData peterProfileData = userController.getUserProfileData(peterId);
        Assert.assertTrue("Profile data should be null",peterUser.getUserProfile()== null);
        Assert.assertTrue("Bank data should be null",peterUser.getUserBank() == null);
        String peterEmail = "peternew@tst.de";
        String peterAdddress = "russian today";
        String peterPhone = "3456789";
        String peterReceiver = "Peter Konto";
        String peterBic = "AVGFDHT";
        String peterIban = "DE554567535345353";
        peterProfileData.setEmail(peterEmail);
        peterProfileData.setAddress(peterAdddress);
        peterProfileData.setPhone(peterPhone);
        peterProfileData.setReceiver(peterReceiver);
        peterProfileData.setBic(peterBic);
        peterProfileData.setIban(peterIban);
        mockMvc.perform(
                post(apiUpdateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(peterProfileData))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        UserProfileData updatedPeterData = userController.getUserProfileData(peterId);
        Assert.assertTrue(updatedPeterData.getEmail().equals(peterEmail));
        Assert.assertTrue("Profile data should be not null.",updatedPeterData.getProfileId() != null);
        Assert.assertTrue(updatedPeterData.getAddress().equals(peterAdddress));
        Assert.assertTrue(updatedPeterData.getPhone().equals(peterPhone));
        Assert.assertTrue("Bank data should be not null.",updatedPeterData.getBankId() != null);
        Assert.assertTrue(updatedPeterData.getReceiver().equals(peterReceiver));
        Assert.assertTrue(updatedPeterData.getBic().equals(peterBic));
        Assert.assertTrue(updatedPeterData.getIban().equals(peterIban));
    }
}
