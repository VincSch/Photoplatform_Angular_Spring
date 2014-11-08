
package de.htw.sdf.photoplatform.repository;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.models.Category;
import de.htw.sdf.photoplatform.persistence.models.Collection;
import de.htw.sdf.photoplatform.persistence.models.CollectionCategory;

public class CollectionCategoryDAOTest extends BaseImageTester
{
    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    CollectionCategoryDAO collectionCategoryDAO;

    @Before
    public void setUp() throws Exception
    {
        insertTestData();
    }

    @After
    public void tearDown() throws Exception
    {
        collectionCategoryDAO.deleteAll();
        categoryDAO.deleteAll();
        clearTables();
    }

    @Test
    public void testGetCollectionCategoryBy()
    {
        // INIT TEST DATA
        Collection myFamily = initEmptyCollection("MyFamily");
        collectionDAO.create(myFamily);
        Collection myHoliday = initEmptyCollection("MyHoliday");
        collectionDAO.create(myHoliday);

        Category categoryNature = createCategory("Nature");
        Category categoryNight = createCategory("Night");
        Category categoryOcean = createCategory("Ocean");

        CollectionCategory myFamilyNatur = new CollectionCategory();
        myFamilyNatur.setCollection(myFamily);
        myFamilyNatur.setCategory(categoryNature);
        collectionCategoryDAO.create(myFamilyNatur);

        CollectionCategory myFamilyOcean = new CollectionCategory();
        myFamilyOcean.setCollection(myFamily);
        myFamilyOcean.setCategory(categoryOcean);
        collectionCategoryDAO.create(myFamilyOcean);

        CollectionCategory myHolidayNight = new CollectionCategory();
        myHolidayNight.setCollection(myHoliday);
        myHolidayNight.setCategory(categoryNight);
        collectionCategoryDAO.create(myHolidayNight);

        CollectionCategory myHolidayOcean = new CollectionCategory();
        myHolidayOcean.setCollection(myHoliday);
        myHolidayOcean.setCategory(categoryOcean);
        collectionCategoryDAO.create(myHolidayOcean);

        // DO TEST
        List<CollectionCategory> natureCollections = collectionCategoryDAO
                .getCollectionCategoryBy(categoryNature);
        Assert.assertTrue(natureCollections.size() == 1);
        Assert.assertTrue(natureCollections
                .get(0)
                .getCategory()
                .getId()
                .equals(categoryNature.getId()));

        List<CollectionCategory> nightCollections = collectionCategoryDAO
                .getCollectionCategoryBy(categoryNight);
        Assert.assertTrue(nightCollections.size() == 1);
        Assert.assertTrue(nightCollections
                .get(0)
                .getCategory()
                .getId()
                .equals(categoryNight.getId()));

        List<CollectionCategory> oceanCollections = collectionCategoryDAO
                .getCollectionCategoryBy(categoryOcean);
        Assert.assertTrue(oceanCollections.size() == 2);
    }

    private Category createCategory(String categoryName)
    {
        Category category = new Category();
        category.setName(categoryName);
        categoryDAO.create(category);
        return category;
    }
}
