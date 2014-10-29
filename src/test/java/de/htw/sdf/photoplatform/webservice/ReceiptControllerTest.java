/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.webservice;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.Recipe;

public class ReceiptControllerTest extends BaseTester
{

    @Autowired
    private RecipeController receiptController;

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.standaloneSetup(receiptController).build();
        insertDestData();
    }

    @After
    public void tearDown() throws Exception
    {
        clearTables();
    }

    @Test
    public void testGetReceiptByName() throws Exception
    {
        mockMvc.perform(
                get("/api/recipe/byname/Eierkuchen").accept(
                        MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    // @Test
    public void testCreateReceipt() throws Exception
    {
        Recipe test = new Recipe();
        test.setNotes("Moep");
        test.setName("Moep Suppe");
        this.mockMvc.perform(
                post("/api/recipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(test))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isCreated());

        assertTrue(receiptManager.findAll().size() == 5);

    }

    // @Test
    public void testDeleteReceipt() throws Exception
    {

        Recipe test = new Recipe();
        test.setNotes("Moep");
        test.setName("Moep Suppe");
        this.mockMvc.perform(
                post("/api/recipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(test))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isCreated());

        mockMvc.perform(
                get("/api/recipe/delete/Moep Suppe").accept(
                        MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertTrue(receiptManager.findAll().size() == 4);

        mockMvc.perform(
                get("/api/recipe/delete/Eintopf").accept(
                        MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertTrue(receiptManager.findAll().size() == 3);
        assertTrue(ingredientManager.findAll().size() == 4);

    }

    // @Test
    public void testfindAll() throws Exception
    {

        this.mockMvc.perform(
                get("/api/recipe/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());

    }

}
