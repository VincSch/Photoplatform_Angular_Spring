package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Test full text search with elasticsearch.
 *
 * @author Sergej Meister.
 */
public class ElasticSearchTest extends BaseImageTester {

    //Test image 1
    private static final String OCEANBEACH_IMAGE_NAME = "OceanBeach";
    private static final String OCEANBEACH_IMAGE_DESCRIPTION = "Natur,Ocean,Beach";
    //Test image 2
    private static final String OCEANSTORM_IMAGE_NAME = "OceanStorm";
    private static final String OCEANSTORM_IMAGE_DESCRIPTION = "Natur,Ocean";

    //Test image 3
    private static final String MONTAIN_IMAGE_NAME = "BergNebel";
    private static final String MONTAINL_IMAGE_DESCRIPTION = "Natur,Berg,Nebel";
    //Test image 4
    private static final String MYFAMILY_IMAGE_NAME = "meineFamilie";
    private static final String MYFAMILY_IMAGE_DESCRIPTION = "Familie,Menschen";

    private List<Long> imageIdList;
    private List<Image> imageList;

    @Before
    public final void setUp() throws Exception {
        insertTestData();
        initImageTestData();
    }

    private void initImageTestData() {
        imageIdList = new ArrayList<>();
        imageList = new ArrayList<>();

        Image image1 = createDefaultImage(OCEANBEACH_IMAGE_NAME, OCEANBEACH_IMAGE_DESCRIPTION, "/upload/oceanBeach.jpeg");
        imageSearchManager.createIndex(image1);
        imageIdList.add(image1.getId());
        imageList.add(image1);

        Image image2 = createDefaultImage(OCEANSTORM_IMAGE_NAME, OCEANSTORM_IMAGE_DESCRIPTION, "/upload/OceanStorm.jpeg");
        imageSearchManager.createIndex(image2);
        imageIdList.add(image2.getId());
        imageList.add(image2);

        Image image3 = createDefaultImage(MONTAIN_IMAGE_NAME, MONTAINL_IMAGE_DESCRIPTION, "/upload/bergNebel.jpeg");
        imageSearchManager.createIndex(image3);
        imageIdList.add(image3.getId());
        imageList.add(image3);

        Image image4 = createDefaultImage(MYFAMILY_IMAGE_NAME, MYFAMILY_IMAGE_DESCRIPTION, "/upload/meineFamilie.jpeg");
        imageSearchManager.createIndex(image4);
        imageIdList.add(image4.getId());
        imageList.add(image4);
    }

    @Test
    public void testInitIndexes() {
        //clear data!
        imageSearchManager.deleteIndexes();

        imageSearchManager.initIndexes();

        //No index because images not added to collection.
        Page<Image> images = imageSearchManager.searchByNameAndDescription(OCEANBEACH_IMAGE_NAME);
        Assert.assertTrue(images.getTotalElements() == 0);
        images = imageSearchManager.searchByNameAndDescription(MYFAMILY_IMAGE_NAME);
        Assert.assertTrue(images.getTotalElements() == 0);

        //Add Images to User
        User photograph = userDAO.findByEmail("sergej@test.de");
        createUserImage(photograph, photograph, imageList);

        //Create Collection.
        Collection newCollection = createCollection(photograph, "TestCollection", Boolean.FALSE);

        try {
            photographerManager.addImagesToCollection(photograph.getId(), newCollection.getId(), imageIdList);
        } catch (ManagerException e) {
            Assert.assertNull("Should be no exception!", e);
        }

        imageSearchManager.initIndexes();
        //No index because collection is not public.
        images = imageSearchManager.searchByNameAndDescription(OCEANBEACH_IMAGE_NAME);
        Assert.assertTrue(images.getTotalElements() == 0);
        images = imageSearchManager.searchByNameAndDescription(MYFAMILY_IMAGE_NAME);
        Assert.assertTrue(images.getTotalElements() == 0);

        newCollection.setPublic(Boolean.TRUE);
        collectionDAO.update(newCollection);
        imageSearchManager.initIndexes();
        images = imageSearchManager.searchByNameAndDescription(OCEANBEACH_IMAGE_NAME);
        Assert.assertTrue(images.getTotalElements() == 1);
        images = imageSearchManager.searchByNameAndDescription(MYFAMILY_IMAGE_NAME);
        Assert.assertTrue(images.getTotalElements() == 1);

        //Rollback test data.
        collectionImageDAO.deleteAll();
        userImageDAO.deleteAll();
        collectionDAO.deleteAll();
    }

    @Test
    public void testSearchById() {
        int listIndex = 0;
        String firstId = imageIdList.get(listIndex).toString();
        Page<Image> images = imageSearchManager.searchById(firstId);
        Assert.assertTrue(images.getTotalElements() == 1);
        Image found = images.getContent().get(listIndex);
        Assert.assertTrue(found.getId().toString().equals(firstId));
        Assert.assertTrue(found.getName().equals(OCEANBEACH_IMAGE_NAME));
        Assert.assertTrue(found.getDescription().equals(OCEANBEACH_IMAGE_DESCRIPTION));
    }

    @Test
    public void testSearchByNameAndDescription() {
        Page<Image> images = imageSearchManager.searchByNameAndDescription("");
        Assert.assertTrue(images.getTotalElements() == 4);

        images = imageSearchManager.searchByNameAndDescription("OceanBeach");
        Assert.assertTrue(images.getTotalElements() == 1);
        Image found = images.getContent().get(0);
        Assert.assertTrue(found.getName().equals(OCEANBEACH_IMAGE_NAME));
        Assert.assertTrue(found.getDescription().equals(OCEANBEACH_IMAGE_DESCRIPTION));

        images = imageSearchManager.searchByNameAndDescription("OCEANBEACH");
        Assert.assertTrue(images.getTotalElements() == 1);
        found = images.getContent().get(0);
        Assert.assertTrue(found.getName().equals(OCEANBEACH_IMAGE_NAME));
        Assert.assertTrue(found.getDescription().equals(OCEANBEACH_IMAGE_DESCRIPTION));

        images = imageSearchManager.searchByNameAndDescription("Ocean");
        Assert.assertTrue(images.getTotalElements() == 2);

        images = imageSearchManager.searchByNameAndDescription("Natur");
        Assert.assertTrue(images.getTotalElements() == 3);

        images = imageSearchManager.searchByNameAndDescription("Ocea");
        Assert.assertTrue(images.getTotalElements() == 2);

        images = imageSearchManager.searchByNameAndDescription("meineFamilie");
        Assert.assertTrue(images.getTotalElements() == 1);

        images = imageSearchManager.searchByNameAndDescription("Famil");
        Assert.assertTrue(images.getTotalElements() == 1);

        images = imageSearchManager.searchByNameAndDescription("Storm");
        Assert.assertTrue(images.getTotalElements() == 1);
        found = images.getContent().get(0);
        Assert.assertTrue(found.getName().equals(OCEANSTORM_IMAGE_NAME));
        Assert.assertTrue(found.getDescription().equals(OCEANSTORM_IMAGE_DESCRIPTION));

        images = imageSearchManager.searchByNameAndDescription("O");
        Assert.assertTrue(images.getTotalElements() == 2);
    }

    @Test
    public void testGetAll() {
        Page<Image> images = imageSearchManager.getAll();
        Assert.assertTrue(images.getTotalElements() == 4);
    }

    @After
    public final void tearDown() throws Exception {
        clearImageTestData();
        clearTables();
    }

    private void clearImageTestData() {
        imageIdList.clear();
        imageList.clear();
        imageDAO.deleteAll();
        imageSearchManager.deleteIndexes();
    }
}
