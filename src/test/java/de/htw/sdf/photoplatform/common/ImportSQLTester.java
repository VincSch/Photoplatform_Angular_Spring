package de.htw.sdf.photoplatform.common;

import de.htw.sdf.photoplatform.persistence.model.Role;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * @author Sergej Meister
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImportSQLTester extends BaseTester {

    @Before
    public final void setUp() throws Exception {
        insertTestData();
    }

    @After
    public final void tearDown() throws Exception {
        clearTables();
    }

    @Test
    public final void test1InitSqlData() {
        List<Role> roles = roleDAO.findAll();
        Assert.assertTrue(roles.size() == 3);
        Assert.assertTrue(roles.get(0).getId().equals(Role.ADMIN_ID));
        Assert.assertTrue(roles.get(0).getName().equals(Role.ADMIN));
        Assert.assertTrue(roles.get(1).getName().equals(Role.CUSTOMER));
        Assert.assertTrue(roles.get(1).getId().equals(Role.CUSTOMER_ID));
        Assert.assertTrue(roles.get(2).getId().equals(Role.PHOTOGRAPHER_ID));
        Assert.assertTrue(roles.get(2).getName().equals(Role.PHOTOGRAPHER));
    }

    @Test
    @Ignore
    public final void test2AfterDelete() {
        List<Role> roles = roleDAO.findAll();
        Assert.assertTrue(roles.size() == 3);
        Assert.assertTrue(roles.get(0).getId().equals(Role.ADMIN_ID));
        Assert.assertTrue(roles.get(0).getName().equals(Role.ADMIN));
        Assert.assertTrue(roles.get(1).getId().equals(Role.CUSTOMER_ID));
        Assert.assertTrue(roles.get(1).getName().equals(Role.CUSTOMER));
        Assert.assertTrue(roles.get(2).getId().equals(Role.PHOTOGRAPHER_ID));
        Assert.assertTrue(roles.get(2).getName().equals(Role.PHOTOGRAPHER));
    }
}
