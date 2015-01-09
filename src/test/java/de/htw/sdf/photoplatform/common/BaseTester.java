/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.sdf.photoplatform.Application;
import de.htw.sdf.photoplatform.DBUtil;
import de.htw.sdf.photoplatform.manager.common.MailService;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.PurchaseItemDAO;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public abstract class BaseTester {

    protected static final String PHOTOGRAPH_EMAIL = "sergej@test.de";
    protected static final String CUSTOMER_EMAIL = "peter@test.de";

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

    @Autowired
    protected MailService mailService;

    @Autowired
    protected PurchaseItemDAO purchaseItemDAO;

    protected List<User> users;

    public final void insertTestData() {
        users = dbUtil.createUsers();
    }

    protected final void clearTables() {
        dbUtil.clearTables();
        users = null;
    }
}
