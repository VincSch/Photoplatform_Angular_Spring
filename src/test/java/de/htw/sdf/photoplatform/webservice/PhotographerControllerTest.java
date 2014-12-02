/**
 *
 */
package de.htw.sdf.photoplatform.webservice;

import de.htw.sdf.photoplatform.common.BaseAPITester;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.controller.PhotographerController;
import de.htw.sdf.photoplatform.webservice.controller.UserController;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for users services.
 */
@Ignore
public class PhotographerControllerTest extends BaseAPITester {

    @Autowired
    private PhotographerController photographerController;

    @Before
    public void setUp() throws Exception {
        initAPITest();
    }

    @After
    public void tearDown() throws Exception {
        cancel();
    }
}
