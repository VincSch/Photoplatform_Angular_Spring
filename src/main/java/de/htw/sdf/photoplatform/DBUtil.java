/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import de.htw.sdf.photoplatform.persistence.model.*;
import de.htw.sdf.photoplatform.repository.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;

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
        createUser();
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
        createDefaultUser("Vincent", "$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG", "vincent@test.de", admin);

        Role customer = roleDAO.findOne(Role.CUSTOMER_ID);
        createDefaultUser("Peter", "$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG", "peter@test.de", customer);

        Role photographer = roleDAO.findOne(Role.PHOTOGRAPHER_ID);
        User sergej = createDefaultUser("Sergej", "123", "sergej@test.de",
                photographer);

        UserProfile sergejProfile = new UserProfile();
        sergejProfile.setFirstName("Sergej");
        sergejProfile.setLastName("Meister");
        sergejProfile.setAddress("Berlinerstraße 12,12207 Berlin");
        sergejProfile.setCompany("Burg");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sergejProfile.setBirthday(dateFormat.format(new Date()));
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
