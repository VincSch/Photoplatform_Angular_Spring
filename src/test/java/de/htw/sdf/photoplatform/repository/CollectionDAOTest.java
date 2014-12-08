/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionDAOTest extends BaseImageTester {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private CollectionCategoryDAO collectionCategoryDAO;

    @Before
    public final void setUp() throws Exception {
        insertTestData();
    }

    @After
    public final void tearDown() throws Exception {
        collectionCategoryDAO.deleteAll();
        categoryDAO.deleteAll();
        clearTables();
    }

    @Test
    public final void testBasic() {
        // Test Create
        String collectionName = "TestCreateCollection";
        Collection testCollection = initEmptyCollection(collectionName);
        collectionDAO.create(testCollection);
        Assert.assertNotNull(testCollection.getId());

        // Test Find
        Collection createdCollection = collectionDAO.findById(testCollection
                .getId());
        Assert.assertTrue(createdCollection.getUser().getUsername()
                .equals("sergej@test.de"));
        Assert.assertTrue(createdCollection.getName().equals(collectionName));
        Assert.assertNull(createdCollection.getThumbnail());
        Assert.assertTrue(createdCollection.getCollectionImages().isEmpty());

        // Test Update
        String newCollectionName = "TestUpdateCollection";
        createdCollection.setName(newCollectionName);
        collectionDAO.update(createdCollection);
        Collection updatedCollection = collectionDAO.findById(testCollection
                .getId());
        Assert.assertTrue(updatedCollection.getId().equals(
                testCollection.getId()));
        Assert.assertTrue(createdCollection.getName().equals(newCollectionName));

        // Test Delete
        Long idToDelete = updatedCollection.getId();
        collectionDAO.deleteById(idToDelete);
        Assert.assertNull(collectionDAO.findById(idToDelete));
    }

    @Test
    public final void testGetImages() {
        // Init Test Data
        String collectionName = "CollectionWithTwoImages";
        Collection testCollection = initEmptyCollection(collectionName);
        collectionDAO.create(testCollection);

        String firstImageName = "FirstImage";
        Image firstImage = initDefaultImage(firstImageName, Boolean.TRUE,
                Boolean.FALSE, "C:/users/firstImage.jpg");
        imageDAO.create(firstImage);
        CollectionImage firstCollectionImage = initCollectionImage(
                testCollection, firstImage);
        collectionImageDAO.create(firstCollectionImage);

        String secondImageName = "SecondImage";
        Image secondImage = initDefaultImage(secondImageName, Boolean.TRUE,
                Boolean.FALSE, "C:/users/secondImage.jpg");
        imageDAO.create(secondImage);

        CollectionImage secondCollectionImage = initCollectionImage(
                testCollection, secondImage);
        collectionImageDAO.create(secondCollectionImage);

        // Do test
        Collection createdCollection = collectionDAO.findById(testCollection
                .getId());
        Assert.assertFalse("Should not be empty!", createdCollection
                .getCollectionImages().isEmpty());
        Assert.assertTrue("Should have 2 entry!", createdCollection
                .getCollectionImages().size() == 2);
        for (CollectionImage collectionImage : createdCollection
                .getCollectionImages()) {
            Assert.assertTrue(collectionImage.getImage().getName()
                    .equals(firstImageName)
                    || collectionImage.getImage().getName()
                    .equals(secondImageName));
        }
    }

    @Test
    public final void testGetByUser() {
        // INIT TEST DATA

        Role photographerRole = roleDAO.findByName(Role.PHOTOGRAPHER);
        User userOne = users.get(0);

        String collectionOneName = "MyFirstCollection";
        Collection collectionOne = initEmptyCollection(collectionOneName,
                userOne);
        collectionDAO.create(collectionOne);

        User userTwo = users.get(1);

        Collection collectionTwo = initEmptyCollection(collectionOneName,
                userTwo);
        collectionDAO.create(collectionTwo);
        String collectionTwoName = "MySecondCollection";
        Collection collectionThree = initEmptyCollection(collectionTwoName,
                userTwo);
        collectionDAO.create(collectionThree);

        // DO TEST
        List<Collection> collectionListUserOne = collectionDAO
                .findCollectionsByUser(userOne.getId(), 0, 10, null);
        Assert.assertTrue(collectionListUserOne.size() == 1);
        Assert.assertTrue(collectionListUserOne.get(0).getName()
                .equals(collectionOneName));
        Assert.assertTrue(collectionListUserOne.get(0).getUser().getId()
                .equals(userOne.getId()));

        List<Collection> collectionListUserTwo = collectionDAO
                .findCollectionsByUser(userTwo.getId(), 0, 10, null);
        Assert.assertTrue(collectionListUserTwo.size() == 2);
        Set<String> collectionsNameSet = new HashSet<>();
        collectionsNameSet.add(collectionOneName);
        collectionsNameSet.add(collectionTwoName);
        Assert.assertTrue(collectionListUserTwo.get(0).getUser().getId()
                .equals(userTwo.getId()));
        Assert.assertTrue(collectionsNameSet.contains(collectionListUserTwo
                .get(0).getName()));
        Assert.assertTrue(collectionListUserTwo.get(1).getUser().getId()
                .equals(userTwo.getId()));
        Assert.assertTrue(collectionsNameSet.contains(collectionListUserTwo
                .get(1).getName()));
    }

    @Test
    public final void testGetCategories() {
        // Init Test Data
        User userOne = userDAO.findByEmail("vincent@test.de");
        String collectionName = "CollectionNaturOcean";
        Collection testCollection = initEmptyCollection(collectionName, userOne);
        collectionDAO.create(testCollection);

        Category categoryNature = createCategory("Nature");
        Category categoryNight = createCategory("Night");
        Category categoryOcean = createCategory("Ocean");

        CollectionCategory testCollectionNature = new CollectionCategory();
        testCollectionNature.setCollection(testCollection);
        testCollectionNature.setCategory(categoryNature);
        collectionCategoryDAO.create(testCollectionNature);

        CollectionCategory testCollectionNight = new CollectionCategory();
        testCollectionNight.setCollection(testCollection);
        testCollectionNight.setCategory(categoryNight);
        collectionCategoryDAO.create(testCollectionNight);

        CollectionCategory testCollectionOcean = new CollectionCategory();
        testCollectionOcean.setCollection(testCollection);
        testCollectionOcean.setCategory(categoryOcean);
        collectionCategoryDAO.create(testCollectionOcean);

        List<Collection> userCollections = collectionDAO.findCollectionsByUser(userOne.getId(), 0, 10, null);
        Assert.assertTrue(userCollections.size() == 1);
        Collection collection = userCollections.get(0);
        Assert.assertTrue(collection.getCollectionCategories().size() == 3);
    }

    private Category createCategory(final String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryDAO.create(category);
        return category;
    }
}
