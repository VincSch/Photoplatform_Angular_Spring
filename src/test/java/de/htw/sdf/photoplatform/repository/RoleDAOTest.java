package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.model.Role;
import org.junit.*;

import java.util.List;

public class RoleDAOTest extends BaseTester {
    private static final int EXPECTED_ROLE_COUNT = 3;

    @Before
    public final void setUp() throws Exception {
        insertTestData();
    }

    @After
    public final void tearDown() throws Exception {
        clearTables();
    }

    @Test
    public final void findTest() {
        List<Role> allRoles = roleDAO.findAll();

        Assert.assertTrue(Role.DEFAULT_ROLES.size() + ". Rollen müssen immer vorhanden sein.",
                allRoles.size() == Role.DEFAULT_ROLES.size());
/**
 * FIXME: Bitte begründen warum wir diese Tests hier brauchen.
 *
 Assert.assertTrue("Die erste Rolle hat die ID 1.", allRoles.get(0)
 .getId().equals(Role.ADMIN_ID));
 Assert.assertTrue("Die erste Rolle hat den Namen Admin.("
 + allRoles.get(0).getName() + ")", allRoles.get(0).getName()
 .equals(Role.ADMIN));
 Assert.assertTrue("Die zweite Rolle hat die ID 2.", allRoles.get(1)
 .getId().equals(Role.CUSTOMER_ID));
 Assert.assertTrue("Die zweite Rolle hat den Namen Customer.", allRoles
 .get(1).getName().equals(Role.CUSTOMER));
 Assert.assertTrue("Die dritte Rolle hat die ID 3.", allRoles.get(2)
 .getId().equals(Role.PHOTOGRAPHER_ID));
 Assert.assertTrue("Die dritte Rolle hat den Namen Photographer.",
 allRoles.get(2).getName().equals(Role.PHOTOGRAPHER));

 Role admin = roleDAO.getAdmin();
 Assert.assertTrue(admin.getId().equals(Role.ADMIN_ID));
 Assert.assertTrue(admin.getName().equals(Role.ADMIN));

 allRoles.clear();
 allRoles = roleDAO.findAllNotAdminRoles();
 Assert.assertTrue("2. Rollen müssen vorhanden sein.",
 allRoles.size() == 2);
 Assert.assertTrue(allRoles.get(0).getId().equals(Role.CUSTOMER_ID));
 Assert.assertTrue(allRoles.get(1).getName().equals(Role.PHOTOGRAPHER)); */
    }

    @Test
    @Ignore
    public final void createTest() {
        Role newRole = new Role();
        newRole.setName("TestRole");
        roleDAO.create(newRole);
        Assert.assertNotNull(newRole.getId());
        List<Role> allRoles = roleDAO.findAll();
        Assert.assertTrue(allRoles.size() == 4);
        Assert.assertTrue(allRoles.get(3).getName().equals("TestRole"));

        // Roolback
        roleDAO.deleteById(newRole.getId());
    }

    @Test
    @Ignore
    public final void deleteTest() {
        // Init Test data
        Role newRole = new Role();
        newRole.setName("TestRole");
        roleDAO.create(newRole);
        Assert.assertTrue(roleDAO.findAll().size() == 4);

        // Do test
        roleDAO.deleteById(newRole.getId());
        List<Role> allRoles = roleDAO.findAll();
        Assert.assertTrue(allRoles.size() == EXPECTED_ROLE_COUNT);
        Assert.assertFalse(allRoles.get(0).getName().equals("TestRole"));
        Assert.assertFalse(allRoles.get(1).getName().equals("TestRole"));
        Assert.assertFalse(allRoles.get(2).getName().equals("TestRole"));
    }
}
