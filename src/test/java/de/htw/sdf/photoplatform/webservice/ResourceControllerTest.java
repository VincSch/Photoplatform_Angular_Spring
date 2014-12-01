package de.htw.sdf.photoplatform.webservice;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import de.htw.sdf.photoplatform.common.BaseAPITester;

public class ResourceControllerTest extends BaseAPITester {

    @Before
    public void setUp() throws Exception {
        initAPITest();
    }

    @After
    public void tearDown() throws Exception {
        cancel();
    }

    @Test
    public void testGetPhotographersImages() throws Exception {
        loginAsPhotograph();

        String request = Endpoints.API_PREFIX + Endpoints.IMAGES_PHOTOGRAPHERS;
        mockMvc.perform(
                get(request).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }

    @Test
    public void testGetPhotographersCollections() throws Exception {
        loginAsPhotograph();

        String request = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_PHOTOGRAPHERS;
        mockMvc.perform(
                get(request).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }
}
