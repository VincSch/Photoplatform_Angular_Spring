/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import de.htw.sdf.photoplatform.persistence.model.PhotographerData;
import de.htw.sdf.photoplatform.persistence.model.Role;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserRole;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.ImageDAO;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ServletContext servletContext;

    /**
     * Insert test data.
     */
    public void insertTestData() {
        createUsers();
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
    public final List<User> createUsers() {
        List<User> users = new ArrayList<>();

        // Password : 123
        final String password123 = "$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG";

        Role admin = roleDAO.findByName(Role.ADMIN);
        users.add(createDefaultUser("vincent@test.de", "Vincent", "Test", password123, "EGCompany", "030555555", "wwww.mypage.com","mypaypalID", null, null, admin));

        Role customer = roleDAO.findByName(Role.CUSTOMER);
        users.add(createDefaultUser("peter@test.de", "Peter", "Test", password123, "EGCompany", "030555555", "wwww.mypage.com","mypaypalID", null, null, customer));

        Role photographer = roleDAO.findByName(Role.PHOTOGRAPHER);
        users.add(createDefaultUser("sergej@test.de", "Sergej", "Test", password123, "EGCompany", "030555555", "www.google.de","mypaypalID", "iban", "swift", photographer));

        Role becomePhotographer = roleDAO.findByName(Role.BECOME_PHOTOGRAPHER);
        users.add(createDefaultUser("become@photographer.de", "Become", "Photographer", password123, "EGCompany", "030555555", "www.google.de","mypaypalID", "iban", "swift", becomePhotographer));

        return users;
    }

    private User createDefaultUser(String email, String firstName, String lastName, String password, String company, String phone, String homepage, String paypalID, String iban, String swift, Role role) {
        User defaultUser = new User();
        defaultUser.setFirstName(firstName);
        defaultUser.setLastName(lastName);
        defaultUser.setUsername(email);
        defaultUser.setPassword(password);
        defaultUser.setEnabled(true);
        defaultUser.setAccountNonLocked(true);

        PhotographerData data = new PhotographerData();
        data.setCompany(company);
        data.setPhone(phone);
        data.setHomepage(homepage);
        data.setPaypalID(paypalID);
        data.setIban(iban);
        data.setSwift(swift);
        defaultUser.setPhotographerData(data);
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
