/**
 *
 */
package de.htw.sdf.photoplatform.webservice;

import de.htw.sdf.photoplatform.common.BaseAPITester;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.controller.UserController;
import de.htw.sdf.photoplatform.webservice.dto.UserCredential;
import de.htw.sdf.photoplatform.webservice.dto.UserPasswordChange;
import de.htw.sdf.photoplatform.webservice.dto.UserRegister;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for users services.
 */
public class UserControllerTest extends BaseAPITester {

    @Autowired
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        initAPITest();
    }

    @Test
    public void testGetEnabledUsers() throws Exception {

        mockMvc.perform(
                get("/api/users/admin/0/100").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isOk());
        mockMvc.perform(
                get("/api/users/admin/0/").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isNotFound());
    }

    @Test
    @Ignore
    public void testGetDisabledUsersByRole() throws Exception {
        mockMvc.perform(
                get("/api/users/disabled/" + Role.PHOTOGRAPHER).accept(
                        MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/api/users/disabled/" + Role.ADMIN).accept(
                        MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                get("/api/users/disabled/" + Role.CUSTOMER).accept(
                        MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Ignore
    public void testGetUserProfileData() throws Exception {
        String notExistId = "0";
        String requestNotExistId = Endpoints.API_PREFIX + Endpoints.USERS_PROFILE_BY_USER_ID.replace("{userId}", notExistId);

        loginAsAdmin();

        mockMvc.perform(
                get(requestNotExistId).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isNotFound());

        User sergejUser = userDAO.findByEmail("Sergej");
        String requestSergej = Endpoints.API_PREFIX
                + Endpoints.USERS_PROFILE_BY_USER_ID.replace("{userId}",
                sergejUser.getId().toString());
        mockMvc.perform(
                get(requestSergej).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(status().isOk());

        String characterId = "a";
        String requestWithCharacter = Endpoints.API_PREFIX
                + Endpoints.USERS_PROFILE_BY_USER_ID.replace("{userId}",
                characterId);
        mockMvc.perform(
                get(requestWithCharacter).contentType(
                        MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testChangeUserPassword() throws Exception {
        String email = "test@test.de";
        String password = "password";

        UserRegister userRegister = new UserRegister();

        userRegister.setEmail(email);
        userRegister.setFirstName("valid");
        userRegister.setLastName("user");
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
        userCredential.setEmail(email);
        userCredential.setPassword(password);

        mockMvc.perform(
                post("/api/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());

        String newPassword = "newPassword";
        String passwordConfirm = "newPassword";

        UserPasswordChange userPWChange = new UserPasswordChange();

        userPWChange.setPassword(password);
        userPWChange.setNewPassword(newPassword);
        userPWChange.setPasswordConfirm(passwordConfirm);

        // Register User
        mockMvc.perform(
                post("/api/users/changepassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userPWChange))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());
    }

    @Test
    @Ignore
    public void testUpdateUserProfileData() throws Exception {
        //Init test data
        loginAsAdmin();

//        User sergejUser = userDAO.findByUserName("Sergej");
//        String sergejId = sergejUser.getId().toString();
//        UserProfileData sergejProfileData = userController
//                .getUserProfileData(sergejId);
//        String newUserName = "sergejNew";
//        String newEmail = "sergejnew@tst.de";
//        String newAddress = "russian today";
//        String newReceiver = "SergejNew Meister";
//
//        // Do Test
//        sergejProfileData.setUsername(newUserName);
//        sergejProfileData.setEmail(newEmail);
//        sergejProfileData.setAddress(newAddress);
//        sergejProfileData.setReceiver(newReceiver);
//
//        String apiUpdateUrl = Endpoints.API_PREFIX + Endpoints.USERS_UPDATE;
//        mockMvc.perform(
//                post(apiUpdateUrl).contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(sergejProfileData))
//                        .accept(MediaType.APPLICATION_JSON)).andExpect(
//                status().isOk());
//
//        UserProfileData updatedProfileData = userController
//                .getUserProfileData(sergejId);
//        Assert.assertTrue(updatedProfileData.getUsername().equals(newUserName));
//        Assert.assertTrue(updatedProfileData.getEmail().equals(newEmail));
//        Assert.assertTrue(updatedProfileData.getAddress().equals(newAddress));
//        Assert.assertTrue(updatedProfileData.getReceiver().equals(newReceiver));
//
//        User peterUser = userDAO.findByUserName("Peter");
//        String peterId = peterUser.getId().toString();
//        UserProfileData peterProfileData = userController
//                .getUserProfileData(peterId);
//        Assert.assertTrue("Profile data should be null",
//                peterUser.getUserProfile() == null);
//        Assert.assertTrue("Bank data should be null",
//                peterUser.getUserBank() == null);
//        String peterEmail = "peternew@tst.de";
//        String peterAdddress = "russian today";
//        String peterPhone = "3456789";
//        String peterReceiver = "Peter Konto";
//        String peterBic = "AVGFDHT";
//        String peterIban = "DE554567535345353";
//        peterProfileData.setEmail(peterEmail);
//        peterProfileData.setAddress(peterAdddress);
//        peterProfileData.setPhone(peterPhone);
//        peterProfileData.setReceiver(peterReceiver);
//        peterProfileData.setBic(peterBic);
//        peterProfileData.setIban(peterIban);
//        mockMvc.perform(
//                post(apiUpdateUrl).contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(peterProfileData))
//                        .accept(MediaType.APPLICATION_JSON)).andExpect(
//                status().isOk());
//        UserProfileData updatedPeterData = userController
//                .getUserProfileData(peterId);
//        Assert.assertTrue(updatedPeterData.getEmail().equals(peterEmail));
//        Assert.assertTrue("Profile data should be not null.",
//                updatedPeterData.getProfileId() != null);
//        Assert.assertTrue(updatedPeterData.getAddress().equals(peterAdddress));
//        Assert.assertTrue(updatedPeterData.getPhone().equals(peterPhone));
//        // TODO hier fliegt er raus weil die Bank entity nicht gesetzt ist.
//        // Es ist mit den Profilen und user convertierung krum (fehlt
//        // swahrscheinlich nur)
//        //     Assert.assertTrue("Bank data should be not null.",
//        //             updatedPeterData.getBankId() != null);
//        Assert.assertTrue(updatedPeterData.getReceiver().equals(peterReceiver));
//        Assert.assertTrue(updatedPeterData.getBic().equals(peterBic));
//        Assert.assertTrue(updatedPeterData.getIban().equals(peterIban));
//        Assert.assertTrue("Bank data should be null, because Peter is not photographer!",
//                updatedPeterData.getBankId() == null);
    }

    @After
    public void tearDown() throws Exception {
        cancel();
    }
}
