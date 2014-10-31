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
import de.htw.sdf.photoplatform.persistence.Ingredient;

public class IngredientDAOTest extends BaseTester
{

    @Autowired
    private IngredientDAO ingredientDAO;

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
        Ingredient ingredient = ingredientDAO.findByName("Pfeffer");
        assertTrue(ingredient.getName().equals("Pfeffer"));
    }

    // @Test
    public void testDelete() throws Exception
    {
        assertTrue(ingredientDAO.findAll().size() == 4);
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName("new ingredient");
        ingredientDAO.create(newIngredient);
        Ingredient ingredient = ingredientDAO.findByName("new ingredient");
        ingredientDAO.delete(ingredient);
        assertTrue(ingredientDAO.findAll().size() == 4);
    }

    // @Test
    public void testfindOne() throws Exception
    {
        Ingredient ingredient = ingredientDAO.findByName("Pfeffer");
        Ingredient ingredient2 = ingredientDAO.findOne(ingredient.getId());
        assertTrue(ingredient2.getName().equals("Pfeffer"));
    }

    // @Test
    public void testfindAll() throws Exception
    {
        List<Ingredient> ingredients = ingredientDAO.findAll();
        assertTrue(ingredients.size() == 4);
    }

    // @Test
    public void testCreate() throws Exception
    {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("new ingredient");
        ingredientDAO.create(ingredient);
        assertTrue(ingredientDAO.findAll().size() == 5);
    }

    // @Test
    public void testDeleteById() throws Exception
    {
        assertTrue(ingredientDAO.findAll().size() == 4);
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName("new ingredient");
        ingredientDAO.create(newIngredient);
        Ingredient ingredient = ingredientDAO.findByName("new ingredient");
        ingredientDAO.deleteById(ingredient.getId());
        assertTrue(ingredientDAO.findAll().size() == 4);
    }
}
