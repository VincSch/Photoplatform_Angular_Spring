package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserRole;
import org.junit.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Ignore
public class UserDAOTest extends BaseTester {
    @Before
    public final void setUp() throws Exception {
        insertTestData();
    }

    @After
    public final void tearDown() throws Exception {
        clearTables();
    }

    @Test
    public final void createAndUpdateTest() {
        Role rolePhotographer = roleDAO.findOne(Role.PHOTOGRAPHER_ID);
        String photographerTesterUsername = "PhotographerTester";
        String photographerTesterPassword = "photographerTestPass";
        String photographerTesterMail = "photographer@web.de";
        User photographer = createDefaultUser(photographerTesterUsername,
                photographerTesterPassword, photographerTesterMail,
                rolePhotographer, Boolean.FALSE, Boolean.FALSE);

        // Ein bisschen russisch stoert doch nicht :)
        String firstname = "TestFirstname";
        String lastname = "TestSurname";
        String address = "мой адрес не дом и не улица мой адрес советский союз";
        String phone = "018765032";
        String company = "Photo AG";
        String homepage = "photo.de";

//        UserProfile photographerProfile = new UserProfile();
//        photographerProfile.setFirstName(firstname);
//        photographerProfile.setLastName(lastname);
//        photographerProfile.setUser(photographer);
//        photographerProfile.setAddress(address);
//        photographerProfile.setPhone(phone);
//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
//        photographerProfile.setBirthday(df.format(new Date()));
//        photographerProfile.setCompany(company);
//        photographerProfile.setHomepage(homepage);
//        userProfileDAO.create(photographerProfile);
//
//        String bic = "ABCDEF";
//        String iban = "DE49178659403245";
//        UserBank photographerBank = new UserBank();
//        photographerBank.setUser(photographer);
//        photographerBank.setReceiver(photographerTesterUsername);
//        photographerBank.setBic(bic);
//        photographerBank.setIban(iban);
//        userBankDAO.create(photographerBank);
//
//        // now test
//        List<User> photographerList = userDAO
//                .findByRoleId(Role.PHOTOGRAPHER_ID);
//        Assert.assertTrue("Genau zwei photographers",
//                photographerList.size() == 2);
//        User createdPhotographer = photographerList.get(1);
//        Assert.assertNotNull(createdPhotographer.getId());
//        Assert.assertTrue(createdPhotographer.getUsername().equals(
//                photographerTesterUsername));
//        Assert.assertTrue(createdPhotographer.getPassword().equals(
//                photographerTesterPassword));
//        Assert.assertTrue(createdPhotographer.getEmail().equals(
//                photographerTesterMail));
//        Assert.assertFalse(createdPhotographer.isEnabled());
//        Assert.assertFalse(createdPhotographer.isAccountNonLocked());
//        Assert.assertTrue(createdPhotographer.getEmail().equals(
//                photographerTesterMail));
//        Assert.assertTrue(createdPhotographer.getUserRoles().size() == 1);
//        Assert.assertTrue(createdPhotographer.getUserRoles().get(0).getRole()
//                .getId().equals(Role.PHOTOGRAPHER_ID));
//
//        UserProfile createdPhotographerProfile = createdPhotographer
//                .getUserProfile();
//        Assert.assertNotNull(createdPhotographerProfile.getId());
//        Assert.assertTrue(createdPhotographerProfile.getAddress().equals(
//                address));
//        Assert.assertTrue(createdPhotographerProfile.getPhone().equals(phone));
//        Assert.assertTrue(createdPhotographerProfile.getBirthday().equals(
//                df.format(new Date())));
//        Assert.assertTrue(createdPhotographerProfile.getCompany().equals(
//                company));
//        Assert.assertTrue(createdPhotographerProfile.getHomepage().equals(
//                homepage));
//
//        UserBank createdPhotographerBank = createdPhotographer.getUserBank();
//        Assert.assertNotNull(createdPhotographerBank.getId());
//        Assert.assertTrue(createdPhotographerBank.getReceiver().equals(
//                photographerTesterUsername));
//        Assert.assertTrue(createdPhotographerBank.getBic().equals(bic));
//        Assert.assertTrue(createdPhotographerBank.getIban().equals(iban));
//
//        // now update test
//        photographer.setEnabled(Boolean.TRUE);
//        photographer.setAccountNonLocked(Boolean.TRUE);
//        userDAO.update(photographer);
//
//        User updatedPhotographer = userDAO.findOne(photographer.getId());
//        Assert.assertTrue(updatedPhotographer.isEnabled());
//        Assert.assertTrue(updatedPhotographer.isAccountNonLocked());
//
//        String newPhone = "987653234";
//        createdPhotographer.getUserProfile().setPhone(newPhone);
//        userProfileDAO.update(createdPhotographer.getUserProfile());
//        UserProfile updatedUserProfile = userProfileDAO
//                .findOne(createdPhotographer.getUserProfile().getId());
//        Assert.assertTrue(updatedUserProfile.getPhone().equals(newPhone));
//
//        String newBic = "RUSBANK";
//        createdPhotographer.getUserBank().setBic(newBic);
//        userBankDAO.update(createdPhotographer.getUserBank());
//        UserBank updatedUserBank = userBankDAO.findOne(createdPhotographer
//                .getUserBank().getId());
//        Assert.assertTrue(updatedUserBank.getBic().equals(newBic));
    }

    @Test
    public final void findTest() {
        // Init test Data
        Role admin = roleDAO.findOne(Role.ADMIN_ID);
        String adminTesterUsername = "AdminTester";
        String adminTesterPassword = "testPass";
        String adminTesterMail = "admintester@web.de";
        createDefaultUser(adminTesterUsername, adminTesterPassword,
                adminTesterMail, admin, Boolean.TRUE, Boolean.TRUE);

        // Do test findByRole
        List<User> adminUsers = userDAO.findByRole(admin);
        Assert.assertTrue(
                "Ein Admin exestierte bereits und noch einer haben wir hinzugefügt.",
                adminUsers.size() == 2);
        Assert.assertTrue(adminUsers.get(1).getUsername()
                .equals(adminTesterUsername));
        Assert.assertTrue(adminUsers.get(1).getPassword()
                .equals(adminTesterPassword));
        Assert.assertTrue(adminUsers.get(1).getEmail().equals(adminTesterMail));
        Assert.assertTrue("Admin hat genau eine Rolle!", adminUsers.get(1)
                .getUserRoles().size() == 1);
        Assert.assertTrue(adminUsers.get(1).getUserRoles().get(0).getRole()
                .getId().equals(Role.ADMIN_ID));

        // Do test findAllNotAdminUsers
        Role rolePhotographer = roleDAO.findOne(Role.PHOTOGRAPHER_ID);
        String photographerTesterUsername = "PhotographerTester";
        String photographerTesterPassword = "photographerTestPass";
        String photographerTesterMail = "photographer@web.de";
        createDefaultUser(photographerTesterUsername,
                photographerTesterPassword, photographerTesterMail,
                rolePhotographer, Boolean.FALSE, Boolean.FALSE);
        List<User> notAdminUsers = userDAO.findAllNotAdminUsers();
        Assert.assertTrue(
                "Drei Users: Peter-Customer; Sergej-Photographer: "
                        + "PhotographerTester-Photographer",
                notAdminUsers.size() == 3);
        Set<Long> existedRoles = new HashSet<Long>();
        for (User notAdminUser : notAdminUsers) {
            for (UserRole notAdminRole : notAdminUser.getUserRoles()) {
                existedRoles.add(notAdminRole.getRole().getId());
            }
        }
        Assert.assertFalse("Fehler, wenn role admin vorhanden ist.",
                existedRoles.contains(Role.ADMIN_ID));

        // Do test findByEnabled
        List<User> enabledUsers = userDAO.findByEnabled(Boolean.TRUE);
        Assert.assertTrue("Der PhotographerTester darf nicht dabei sein.",
                enabledUsers.size() == 4);
        for (User enabledUser : enabledUsers) {
            if (enabledUser.getUsername().equals(photographerTesterUsername)) {
                Assert.fail("Der PhotographerTester darf nicht dabei sein.");
            }
        }

        List<User> notEnabledUsers = userDAO.findByEnabled(Boolean.FALSE);
        Assert.assertTrue("Nur Der PhotographerTester darf dabei sein.",
                notEnabledUsers.size() == 1);
        Assert.assertTrue(notEnabledUsers.get(0).getUsername()
                .equals(photographerTesterUsername));

        // Do test findByAccountLocked
        List<User> lockedUsers = userDAO.findByAccountLocked(Boolean.TRUE);
        Assert.assertTrue("Der PhotographerTester darf nicht dabei sein.",
                lockedUsers.size() == 4);
        for (User lockedUser : lockedUsers) {
            if (lockedUser.getUsername().equals(photographerTesterUsername)) {
                Assert.fail("Der PhotographerTester darf nicht dabei sein.");
            }
        }

        List<User> notLockedUsers = userDAO.findByAccountLocked(Boolean.FALSE);
        Assert.assertTrue("Nur Der PhotographerTester darf dabei sein.",
                notLockedUsers.size() == 1);
        Assert.assertTrue(notLockedUsers.get(0).getUsername()
                .equals(photographerTesterUsername));
    }

    @Test
    public void testFindStartCount() {
        List<User> usersBetween0And0 = userDAO.find(0, 0);
        Assert.assertTrue(usersBetween0And0.size() == 1);
        Assert.assertTrue(usersBetween0And0.get(0).getUsername().equals("Peter"));

        List<User> usersBetween0And1 = userDAO.find(0, 1);
        Assert.assertTrue(usersBetween0And1.size() == 1);
        Assert.assertTrue(usersBetween0And0.get(0).getUsername().equals("Peter"));

        List<User> usersBetween0And2 = userDAO.find(0, 2);
        Assert.assertTrue(usersBetween0And2.size() == 2);
        Assert.assertTrue(usersBetween0And2.get(0).getUsername().equals("Peter"));

        List<User> usersBetween0And3 = userDAO.find(0, 3);
        Assert.assertTrue(usersBetween0And3.size() == 3);
        Assert.assertTrue(usersBetween0And3.get(1).getUsername().equals("Sergej"));

        List<User> usersBetween1And0 = userDAO.find(1, 0);
        Assert.assertTrue(usersBetween1And0.size() == 1);
        Assert.assertTrue(usersBetween1And0.get(0).getUsername().equals("Sergej"));

        List<User> usersBetween1And1 = userDAO.find(1, 1);
        Assert.assertTrue(usersBetween1And1.size() == 1);
        Assert.assertTrue(usersBetween1And1.get(0).getUsername().equals("Sergej"));

        List<User> usersBetween1And2 = userDAO.find(1, 2);
        Assert.assertTrue(usersBetween1And2.size() == 2);
        Assert.assertTrue(usersBetween1And2.get(1).getUsername().equals("Vincent"));

        List<User> usersBetween4And1 = userDAO.find(4, 1);
        Assert.assertTrue(usersBetween4And1.isEmpty());
    }

    @Test
    public void testFindByRoleAndEnabledFilter() {
        List<User> defaultDisabledPhotographs = userDAO.findByRoleAndEnabledFilter(Role.PHOTOGRAPHER_ID, false);
        Assert.assertTrue(defaultDisabledPhotographs.isEmpty());
        List<User> defaultEnabledPhotographs = userDAO.findByRoleAndEnabledFilter(Role.PHOTOGRAPHER_ID, true);
        Assert.assertTrue(defaultEnabledPhotographs.size() == 1);
        List<User> defaultDisabledAdmins = userDAO.findByRoleAndEnabledFilter(Role.ADMIN_ID, false);
        Assert.assertTrue(defaultDisabledAdmins.isEmpty());
        List<User> defaultEnabledAdmins = userDAO.findByRoleAndEnabledFilter(Role.ADMIN_ID, true);
        Assert.assertTrue(defaultEnabledAdmins.size() == 1);
        List<User> defaultDisabledCustomers = userDAO.findByRoleAndEnabledFilter(Role.CUSTOMER_ID, false);
        Assert.assertTrue(defaultDisabledCustomers.isEmpty());
        List<User> defaultEnabledCustomers = userDAO.findByRoleAndEnabledFilter(Role.CUSTOMER_ID, true);
        Assert.assertTrue(defaultEnabledCustomers.size() == 1);

        // Init test Data
        Role photographer = roleDAO.findOne(Role.PHOTOGRAPHER_ID);
        String photographerUsername = "PhotographerTester";
        String aphotographerPassword = "testPass";
        String photographerMail = "photographertester@web.de";
        createDefaultUser(photographerUsername, aphotographerPassword,
                photographerMail, photographer, Boolean.FALSE, Boolean.TRUE);

        //Do Test
        List<User> disabledPhotographs = userDAO.findByRoleAndEnabledFilter(Role.PHOTOGRAPHER_ID, false);
        Assert.assertTrue(disabledPhotographs.size() == 1);
        User createdDisabledPhotograph = disabledPhotographs.get(0);
        createdDisabledPhotograph.setEnabled(Boolean.TRUE);
        userDAO.update(createdDisabledPhotograph);
        List<User> newDisabledPhotographs = userDAO.findByRoleAndEnabledFilter(Role.PHOTOGRAPHER_ID, false);
        Assert.assertTrue(newDisabledPhotographs.isEmpty());
        List<User> newEnabledPhotographs = userDAO.findByRoleAndEnabledFilter(Role.PHOTOGRAPHER_ID, true);
        Assert.assertTrue(newEnabledPhotographs.size() == 2);
    }

    @Test
    public void testFindUserById() {
        User sergejByName = userDAO.findByUserName("Sergej");
        User sergejById = userDAO.findById(sergejByName.getId());
        Assert.assertTrue(sergejById.getUsername().equals("Sergej"));
        Assert.assertTrue(sergejById.getEmail().equals("sergej@test.de"));
//        Assert.assertTrue(sergejById.getUserProfile().getFirstName().equals("Sergej"));
//        Assert.assertTrue(sergejById.getUserBank().getReceiver().equals("Sergej Meister"));
    }
}
