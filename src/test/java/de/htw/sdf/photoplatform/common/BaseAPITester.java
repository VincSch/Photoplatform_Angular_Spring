/**
 *
 */
package de.htw.sdf.photoplatform.common;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.htw.sdf.photoplatform.webservice.dto.UserCredential;

public abstract class BaseAPITester extends BaseImageTester {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;


    protected void initAPITest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        insertTestData();
    }

    /**
     * Default log in with admin permissions.
     */
    protected void loginAsAdmin() throws Exception {
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail("vincent@test.de");
        userCredential.setPassword("123");

        mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    /**
     * Default log in with admin permissions.
     */
    protected void loginAsPhotograph() throws Exception {
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail("sergej@test.de");
        userCredential.setPassword("123");

        mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCredential))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    protected void cancel() {
        clearTables();
    }

}
