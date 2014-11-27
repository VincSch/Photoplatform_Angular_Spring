/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserRole;
import de.htw.sdf.photoplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private void createRoles() {
        for (String defaultRole : Role.DEFAULT_ROLES) {
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
        createDefaultUser("Vincent", "$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG", "vincent@test.de", "EGCompany", "030555555", "wwww.mypage.com", null, null, admin);

        Role customer = roleDAO.findOne(Role.CUSTOMER_ID);
        createDefaultUser("Peter", "$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG", "peter@test.de", "EGCompany", "030555555", "wwww.mypage.com", null, null, customer);

        Role photographer = roleDAO.findOne(Role.PHOTOGRAPHER_ID);
        User sergej = createDefaultUser("Sergej", "123", "sergej@test.de", "EGCompany", "030555555", "wwww.mypage.com", "000003822992929292DE", "S928002",
                photographer);
    }

    private User createDefaultUser(String username, String password,
                                   String email, String company, String phone, String homepage, String iban, String swift, Role role) {
        User defaultUser = new User();
        defaultUser.setUserName(username);
        defaultUser.setPassword(password);
        defaultUser.setEnabled(true);
        defaultUser.setAccountNonLocked(true);
        defaultUser.setEmail(email);
        defaultUser.setCompany(company);
        defaultUser.setPhone(phone);
        defaultUser.setHomepage(homepage);
        defaultUser.setIban(iban);
        defaultUser.setSwift(swift);
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
        userRoleDAO.deleteAll();
        userDAO.deleteAll();
    }
}
