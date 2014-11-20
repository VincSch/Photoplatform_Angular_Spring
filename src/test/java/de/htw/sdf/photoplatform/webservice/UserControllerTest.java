/**
 *
 */
package de.htw.sdf.photoplatform.webservice;


import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.htw.sdf.photoplatform.common.BaseTester;

/**
 * Tests for users services.
 */
public class UserControllerTest extends BaseTester {

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        insertTestData();
    }

    @After
    public void tearDown() throws Exception {
        clearTables();
    }

    @Test
    @Ignore
    public void testGetEnabledUsers(){
        //TODO. coming soon :)!
    }

    @Test
    @Ignore
    public void testGetDisabledUsersByRole(){
        //TODO. coming soon :)!
    }
}
