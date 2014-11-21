/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserBank;
import de.htw.sdf.photoplatform.persistence.models.UserProfile;
import de.htw.sdf.photoplatform.persistence.models.UserRole;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.ImageDAO;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserBankDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserProfileDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Service
public class DBUtil {

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected RoleDAO roleDAO;

    @Autowired
    protected UserRoleDAO userRoleDAO;

    @Autowired
    protected UserProfileDAO userProfileDAO;

    @Autowired
    protected UserBankDAO userBankDAO;

    @Autowired
    protected CollectionDAO collectionDAO;

    @Autowired
    protected ImageDAO imageDAO;

    @Autowired
    CollectionImageDAO collectionImageDAO;

    /**
     * Insert test data.
     */
    public void insertTestData() {
        // if (instanceCounter == 0)
        // {
        // createRoles();
        // }

        createUser();
        // instanceCounter++;
    }

    // private void createRoles()
    // {
    // Role admin = new Role();
    // admin.setName("ADMIN");
    // roleDAO.create(admin);
    //
    // Role customer = new Role();
    // customer.setName("CUSTOMER");
    // roleDAO.create(customer);
    //
    // Role photographer = new Role();
    // photographer.setName("PHOTOGRAPHER");
    // roleDAO.create(photographer);
    // }

    private void createRoles() {
        for (String defaultRole : Role.DEFAULT_ROLES){
            Role role = new Role();
            role.setName(defaultRole);
            roleDAO.create(role);
        }
    }

    /**
     * Create user.
     */
    private void createUser() {
        Role admin = roleDAO.findOne(Role.ADMIN_ID);
        createDefaultUser("Vincent", "123", "vincent@test.de", admin);

        Role customer = roleDAO.findOne(Role.CUSTOMER_ID);
        createDefaultUser("Peter", "123", "peter@test.de", customer);

        Role photographer = roleDAO.findOne(Role.PHOTOGRAPHER_ID);
        User sergej = createDefaultUser("Sergej", "123", "sergej@test.de",
            photographer);

        UserProfile sergejProfile = new UserProfile();
        sergejProfile.setFirstName("Sergej");
        sergejProfile.setSurname("Meister");
        sergejProfile.setAddress("Berlinerstraße 12,12207 Berlin");
        sergejProfile.setCompany("Burg");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        sergejProfile.setBirthday(df.format(new Date()));
        sergejProfile.setPhone("016568334");
        sergejProfile.setUser(sergej);
        userProfileDAO.create(sergejProfile);

        UserBank sergejBank = new UserBank();
        sergejBank.setUser(sergej);
        sergejBank.setReceiver("Sergej Meister");
        sergejBank.setIban("DE50344342342424");
        sergejBank.setBic("DEFGHKZ");
        userBankDAO.create(sergejBank);
    }

    private User createDefaultUser(String username, String password,
        String email, Role role) {
        User defaultUser = new User();
        defaultUser.setUserName(username);
        defaultUser.setPassword(password);
        defaultUser.setEnabled(true);
        defaultUser.setAccountNonLocked(true);
        defaultUser.setEmail(email);
        userDAO.create(defaultUser);

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(defaultUser);
        userRoleDAO.create(userRole);

        return defaultUser;
    }

    /**
     * Clear tables.
     */
    public void clearTables() {
        collectionImageDAO.deleteAll();
        imageDAO.deleteAll();
        collectionDAO.deleteAll();

        // delete users
        userBankDAO.deleteAll();
        userProfileDAO.deleteAll();
        userRoleDAO.deleteAll();
        userDAO.deleteAll();
    }
}
