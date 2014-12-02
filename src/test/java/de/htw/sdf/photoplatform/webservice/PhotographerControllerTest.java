/**
 *
 */
package de.htw.sdf.photoplatform.webservice;

import de.htw.sdf.photoplatform.common.BaseAPITester;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for users services.
 */
@Ignore
public class PhotographerControllerTest extends BaseAPITester {

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

        String request = Endpoints.API_PREFIX + Endpoints.COLLECTIONS;
        mockMvc.perform(
                get(request).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }
}
