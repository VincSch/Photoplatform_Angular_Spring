
package de.htw.sdf.photoplatform.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.common.Constants;
import de.htw.sdf.photoplatform.persistence.models.Role;

public class RoleDAOTest extends BaseTester
{

    @Autowired
    protected RoleDAO roleDAO;

    @Before
    public void setUp() throws Exception
    {
        insertDestData();
    }

    @After
    public void tearDown() throws Exception
    {
        clearTables();
    }

    @Test
    public void findTest()
    {
        List<Role> allRoles = roleDAO.findAll();

        Assert.assertTrue("3. Rollen müssen immer vorhanden sein.", allRoles.size() == 3);
        Assert.assertTrue(
                "Die erste Rolle ist Admin.",
                allRoles.get(0).getId().equals(Constants.ROLE_ADMIN));
        Assert.assertTrue("Die erste Rolle ist Admin.", allRoles.get(0).getName().equals("ADMIN"));
        Assert.assertTrue(
                "Die zweite Rolle ist Customer.",
                allRoles.get(1).getId().equals(Constants.ROLE_CUSTOMER));
        Assert.assertTrue(
                "Die zweite Rolle ist Customer.",
                allRoles.get(1).getName().equals("CUSTOMER"));
        Assert.assertTrue(
                "Die dritte Rolle ist Photographer.",
                allRoles.get(2).getId().equals(Constants.ROLE_PHOTOGRAPHER));
        Assert.assertTrue(
                "Die dritte Rolle ist Photographer.",
                allRoles.get(2).getName().equals("PHOTOGRAPHER"));

        Role admin = roleDAO.getAdmin();
        Assert.assertTrue(admin.getId().equals(Constants.ROLE_ADMIN));
        Assert.assertTrue(admin.getName().equals("ADMIN"));

        allRoles.clear();
        allRoles = roleDAO.findAllNotAdminRoles();
        Assert.assertTrue("2. Rollen müssen vorhanden sein.", allRoles.size() == 2);
        Assert.assertTrue(allRoles.get(0).getId().equals(Constants.ROLE_CUSTOMER));
        Assert.assertTrue(allRoles.get(1).getName().equals("PHOTOGRAPHER"));
    }

    @Test
    public void createTest()
    {
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
    public void deleteTest()
    {
        // Init Test data
        Role newRole = new Role();
        newRole.setName("TestRole");
        roleDAO.create(newRole);
        Assert.assertTrue(roleDAO.findAll().size() == 4);

        // Do test
        roleDAO.deleteById(newRole.getId());
        List<Role> allRoles = roleDAO.findAll();
        Assert.assertTrue(allRoles.size() == 3);
        Assert.assertFalse(allRoles.get(0).getName().equals("TestRole"));
        Assert.assertFalse(allRoles.get(1).getName().equals("TestRole"));
        Assert.assertFalse(allRoles.get(2).getName().equals("TestRole"));
    }
}
