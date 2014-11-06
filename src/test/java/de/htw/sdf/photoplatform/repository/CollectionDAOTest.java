/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository;

import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.models.image.Collection;
import de.htw.sdf.photoplatform.persistence.models.image.CollectionImage;
import de.htw.sdf.photoplatform.persistence.models.image.Image;

public class CollectionDAOTest extends BaseImageTester
{

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
    public void testBasic()
    {
        // Test Create
        String collectionName = "TestCreateCollection";
        Collection testCollection = initEmptyCollection(collectionName);
        collectionDAO.create(testCollection);
        Assert.assertNotNull(testCollection.getId());

        // Test Find
        Collection createdCollection = collectionDAO.findById(testCollection.getId());
        Assert.assertTrue(createdCollection.getUser().getUsername().equals("Sergej"));
        Assert.assertTrue(createdCollection.getName().equals(collectionName));
        Assert.assertNull(createdCollection.getThumbnail());
        Assert.assertTrue(createdCollection.getCollectionImages().isEmpty());

        // Test Update
        String newCollectionName = "TestUpdateCollection";
        createdCollection.setName(newCollectionName);
        collectionDAO.update(createdCollection);
        Collection updatedCollection = collectionDAO.findById(testCollection.getId());
        Assert.assertTrue(updatedCollection.getId().equals(testCollection.getId()));
        Assert.assertTrue(createdCollection.getName().equals(newCollectionName));

        // Test Delete
        Long idToDelete = updatedCollection.getId();
        collectionDAO.deleteById(idToDelete);
        try
        {
            collectionDAO.findById(idToDelete);
            Assert.fail("Should be an exception!");
        }
        catch (NoResultException nre)
        {
            Assert.assertNotNull(nre);
        }
    }

    @Test
    public void testGetImages()
    {
        // Init Test Data
        String collectionName = "CollectionWithTwoImages";
        Collection testCollection = initEmptyCollection(collectionName);
        collectionDAO.create(testCollection);

        String firstImageName = "FirstImage";
        Image firstImage = initDefaultImage(
                firstImageName,
                Boolean.TRUE,
                Boolean.FALSE,
                "C:/users/firstImage.jpg");
        imageDAO.create(firstImage);
        CollectionImage firstCollectionImage = initCollectionImage(testCollection, firstImage);
        collectionImageDAO.create(firstCollectionImage);

        String secondImageName = "SecondImage";
        Image secondImage = initDefaultImage(
                secondImageName,
                Boolean.TRUE,
                Boolean.FALSE,
                "C:/users/secondImage.jpg");
        imageDAO.create(secondImage);

        CollectionImage secondCollectionImage = initCollectionImage(testCollection, secondImage);
        collectionImageDAO.create(secondCollectionImage);

        // Do test
        Collection createdCollection = collectionDAO.findById(testCollection.getId());
        Assert.assertFalse("Should not be empty!", createdCollection
                .getCollectionImages()
                .isEmpty());
        Assert.assertTrue(
                "Should have 2 entry!",
                createdCollection.getCollectionImages().size() == 2);
        for (CollectionImage collectionImage : createdCollection.getCollectionImages())
        {
            Assert.assertTrue(collectionImage.getImage().getName().equals(firstImageName)
                    || collectionImage.getImage().getName().equals(secondImageName));
        }

    }

}
