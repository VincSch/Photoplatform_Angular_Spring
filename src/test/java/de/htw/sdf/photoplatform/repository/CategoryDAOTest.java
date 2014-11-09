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
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.models.Category;

public class CategoryDAOTest extends BaseImageTester {
    @Autowired
    CategoryDAO categoryDAO;

    @Before
    public void setUp() throws Exception {
        insertTestData();
    }

    @After
    public void tearDown() throws Exception {
        categoryDAO.deleteAll();
        clearTables();
    }

    @Test
    public void testBasic() {
        // Test create
        Category category = new Category();
        String categoryNature = "Nature";
        category.setName(categoryNature);
        categoryDAO.create(category);
        Assert.assertNotNull(category.getId());
        Category createdCategory = categoryDAO.findByName(categoryNature);
        Assert.assertTrue(createdCategory.getName().equals(categoryNature));

        // Test update
        String categoryNight = "Night";
        createdCategory.setName(categoryNight);
        categoryDAO.update(createdCategory);
        Category updatedCategory = categoryDAO.findOne(createdCategory.getId());
        Assert.assertTrue(updatedCategory.getName().equals(categoryNight));

        // Test delete
        Long deletedId = createdCategory.getId();
        categoryDAO.delete(createdCategory);

        Assert.assertNull(categoryDAO.findOne(deletedId));
        Assert.assertNull(categoryDAO.findByName(categoryNight));
    }
}
