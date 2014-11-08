
package de.htw.sdf.photoplatform.repository;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.common.Constants;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserProfile;

public class UserProfileDAOTest extends BaseTester
{

    @Before
    public void setUp() throws Exception
    {
        insertTestData();
    }

    @After
    public void tearDown() throws Exception
    {
        clearTables();
    }

    @Test
    public void testFindByUserId() throws Exception
    {
        Role rolePhotographer = roleDAO.findOne(Constants.ROLE_PHOTOGRAPHER);
        String photographerTesterUsername = "PhotographerBankTester";
        String photographerTesterPassword = "photographerBankTestPass";
        String photographerTesterMail = "photographerbank@web.de";
        User photographer = createDefaultUser(
                photographerTesterUsername,
                photographerTesterPassword,
                photographerTesterMail,
                rolePhotographer,
                Boolean.TRUE,
                Boolean.TRUE);

        String firstName = "TestFirstname";
        String surname = "TestSurname";
        String address = "мой адрес не дом и не улица мой адрес советский союз";
        String phone = "018765032";
        String company = "Photo AG";
        String homepage = "photo.de";
        UserProfile photographerProfile = new UserProfile();
        photographerProfile.setFirstName(firstName);
        photographerProfile.setSurname(surname);
        photographerProfile.setUser(photographer);
        photographerProfile.setAddress(address);
        photographerProfile.setPhone(phone);
        photographerProfile.setCompany(company);
        photographerProfile.setHomepage(homepage);
        userProfileDAO.create(photographerProfile);

        // Do TEST
        UserProfile createdProfile = userProfileDAO.findByUserId(photographer.getId());
        Assert.assertTrue(createdProfile.getUser().getId().equals(photographer.getId()));
        Assert.assertTrue(createdProfile.getAddress().equals(address));
        Assert.assertTrue(createdProfile.getFirstName().equals(firstName));
        Assert.assertTrue(createdProfile.getSurname().equals(surname));
    }
}
