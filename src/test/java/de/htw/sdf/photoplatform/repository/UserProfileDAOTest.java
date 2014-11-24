package de.htw.sdf.photoplatform.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserProfile;

public class UserProfileDAOTest extends BaseTester {

    @Before
    public final void setUp() throws Exception {
        insertTestData();
    }

    @After
    public final void tearDown() throws Exception {
        clearTables();
    }

    @Test
    public final void testFindByUserId() throws Exception {
        Role rolePhotographer = roleDAO.findOne(Role.PHOTOGRAPHER_ID);
        String photographerTesterUsername = "PhotographerBankTester";
        String photographerTesterPassword = "photographerBankTestPass";
        String photographerTesterMail = "photographerbank@web.de";
        User photographer = createDefaultUser(photographerTesterUsername,
                photographerTesterPassword, photographerTesterMail,
                rolePhotographer, Boolean.TRUE, Boolean.TRUE);

        String firstName = "TestFirstname";
        String lastname = "TestSurname";
        String address = "мой адрес не дом и не улица мой адрес советский союз";
        String phone = "018765032";
        String company = "Photo AG";
        String homepage = "photo.de";
        UserProfile photographerProfile = new UserProfile();
        photographerProfile.setFirstName(firstName);
        photographerProfile.setLastName(lastname);
        photographerProfile.setUser(photographer);
        photographerProfile.setAddress(address);
        photographerProfile.setPhone(phone);
        photographerProfile.setCompany(company);
        photographerProfile.setHomepage(homepage);
        userProfileDAO.create(photographerProfile);

        // Do TEST
        UserProfile createdProfile = userProfileDAO.findByUserId(photographer
                .getId());
        Assert.assertTrue(createdProfile.getUser().getId()
                .equals(photographer.getId()));
        Assert.assertTrue(createdProfile.getAddress().equals(address));
        Assert.assertTrue(createdProfile.getFirstName().equals(firstName));
        Assert.assertTrue(createdProfile.getLastName().equals(lastname));
    }
}
