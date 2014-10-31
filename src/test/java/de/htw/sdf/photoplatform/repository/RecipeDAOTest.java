/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.models.Recipe;

public class RecipeDAOTest extends BaseTester
{

    @Autowired
    private RecipeDAO recipeDAO;

    @Before
    public void setUp() throws Exception
    {
        insertDestData();
    }

    @After
    public void tearDown() throws Exception
    {
        clearTables();
    }

    @Test
    public void testGetByName() throws Exception
    {
        Recipe receipt = recipeDAO.findByName("Eierkuchen");
        assertTrue(receipt.getName().equals("Eierkuchen"));
    }

    // @Test
    public void testDelete() throws Exception
    {
        assertTrue(recipeDAO.findAll().size() == 4);
        Recipe dbReceipt = recipeDAO.findByName("Roladen");
        recipeDAO.delete(dbReceipt);
        assertTrue(recipeDAO.findAll().size() == 3);
    }

    // @Test
    public void testfindByNameContaing() throws Exception
    {
        List<Recipe> receipes = recipeDAO.findByNameContaing("Eier");
        assertTrue(receipes.get(0).getName().equals("Eierkuchen"));
    }

    // @Test
    public void testfindOne() throws Exception
    {
        Recipe eierkuchen = recipeDAO.findByName("Eierkuchen");
        Recipe receipe = recipeDAO.findOne(eierkuchen.getId());
        assertTrue(receipe.getName().equals("Eierkuchen"));
    }

    // @Test
    public void testfindAll() throws Exception
    {
        List<Recipe> recipes = recipeDAO.findAll();

        assertTrue(recipes.size() == 4);
    }

    // @Test
    public void testCreate() throws Exception
    {
        Recipe recipe = new Recipe();
        recipe.setName("Neues Essen");
        recipeDAO.create(recipe);
        assertTrue(recipeDAO.findAll().size() == 5);
    }

    // @Test
    public void testDeleteById() throws Exception
    {
        assertTrue(recipeDAO.findAll().size() == 4);
        Recipe dbReceipt = recipeDAO.findByName("Roladen");
        recipeDAO.deleteById(dbReceipt.getId());
        assertTrue(recipeDAO.findAll().size() == 3);
    }

}
