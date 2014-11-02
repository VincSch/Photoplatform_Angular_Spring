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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.sdf.photoplatform.Application;
import de.htw.sdf.photoplatform.DBUtil;
import de.htw.sdf.photoplatform.manager.IngredientManager;
import de.htw.sdf.photoplatform.manager.RecipeManager;
import de.htw.sdf.photoplatform.manager.UnitManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public abstract class BaseTester
{
    protected Logger log = Logger.getLogger(this.getClass().getName());

    protected ObjectMapper mapper = new ObjectMapper();

    protected MockMvc mockMvc;

    @Autowired
    protected IngredientManager ingredientManager;

    @Autowired
    protected UnitManager unitManager;

    @Autowired
    protected RecipeManager receiptManager;

    @Autowired
    protected DBUtil dbUtil;

    public void insertDestData()
    {
        dbUtil.insertTestData();
    }

    protected void clearTables()
    {
        dbUtil.clearTables();
    }
}
