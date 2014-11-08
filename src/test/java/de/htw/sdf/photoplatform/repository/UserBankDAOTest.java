/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.common.Constants;
import de.htw.sdf.photoplatform.persistence.models.Role;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserBank;

public class UserBankDAOTest extends BaseTester
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

        String bic = "UCHTR";
        String iban = "DE49154578659403245";
        UserBank photographerBank = new UserBank();
        photographerBank.setUser(photographer);
        photographerBank.setReceiver(photographerTesterUsername);
        photographerBank.setBic(bic);
        photographerBank.setIban(iban);
        userBankDAO.create(photographerBank);

        UserBank userBankData = userBankDAO.findByUserId(photographer.getId());
        Assert.assertTrue(userBankData.getBic().equals(bic));
        Assert.assertTrue(userBankData.getIban().equals(iban));
        Assert.assertTrue(userBankData.getUser().getUsername().equals(photographerTesterUsername));
    }
}
