/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.sdf.photoplatform.Application;
import de.htw.sdf.photoplatform.DBUtil;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public abstract class BaseTester {
    protected Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected UserRoleDAO userRoleDAO;

    @Autowired
    protected RoleDAO roleDAO;

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected DBUtil dbUtil;

    protected List<User> users;

    public final void insertTestData() {
        users = dbUtil.createUsers();
    }

    protected final void clearTables() {
        dbUtil.clearTables();
        users = null;
    }
}
