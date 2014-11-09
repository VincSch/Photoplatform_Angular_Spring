/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.common;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.sdf.photoplatform.Application;
import de.htw.sdf.photoplatform.DBUtil;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserRole;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserBankDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserProfileDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public abstract class BaseTester
{
    protected Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected UserRoleDAO userRoleDAO;

    @Autowired
    protected RoleDAO roleDAO;

    @Autowired
    protected UserProfileDAO userProfileDAO;

    @Autowired
    protected UserBankDAO userBankDAO;

    protected ObjectMapper mapper = new ObjectMapper();

    protected MockMvc mockMvc;

    @Autowired
    protected DBUtil dbUtil;

    public final void insertTestData() {
        dbUtil.insertTestData();
    }

    protected final void clearTables() {
        dbUtil.clearTables();
    }

    protected final User createDefaultUser(final String username,
            final String password, final String email, final Role role,
            final Boolean enabled, final Boolean locked) {
        User defaultUser = new User();
        defaultUser.setUserName(username);
        defaultUser.setPassword(password);
        defaultUser.setEnabled(enabled);
        defaultUser.setAccountNonLocked(locked);
        defaultUser.setEmail(email);
        userDAO.create(defaultUser);

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(defaultUser);
        userRoleDAO.create(userRole);

        return defaultUser;
    }
}
