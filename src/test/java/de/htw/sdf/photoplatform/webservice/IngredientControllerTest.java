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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.Ingredient;

public class IngredientControllerTest extends BaseTester
{

    @Autowired
    private IngredientController ingredientController;

    @Autowired
    private RecipeController recipeController;

    private MockMvc mockMvcRecipe;

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        mockMvcRecipe = MockMvcBuilders
                .standaloneSetup(recipeController)
                .build();
        insertDestData();
    }

    @After
    public void tearDown() throws Exception
    {
        clearTables();
    }

    @Test
    public void testGetIngredientByName() throws Exception
    {
        mockMvc
                .perform(
                        get("/api/ingredient/byname/Mehl").accept(
                                MediaType.APPLICATION_JSON).characterEncoding(
																	 "UTF-8"))
                .andExpect(status().isOk());
    }

    // @Test
    public void testCreateIngredient() throws Exception
    {
        Ingredient test = new Ingredient();
        test.setName("TestIngredient");
        this.mockMvc.perform(
                post("/api/ingredient/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(test))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isCreated());

        assertTrue(ingredientManager.findAll().size() == 5);

    }

    // @Test
    public void testDeleteIngredient() throws Exception
    {

        Ingredient test = new Ingredient();
        test.setName("TestIngredient");
        this.mockMvc.perform(
                post("/api/ingredient/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(test))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isCreated());

        mockMvc.perform(
                get("/api/ingredient/delete/TestIngredient").accept(
                        MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertTrue(ingredientManager.findAll().size() == 4);
    }
}
