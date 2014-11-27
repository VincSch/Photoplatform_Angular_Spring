/**
 *
 */
package de.htw.sdf.photoplatform.common;


import de.htw.sdf.photoplatform.webservice.dto.request.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class BaseAPITester extends BaseTester {

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
    protected void login() throws Exception {
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername("Vincent");
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
