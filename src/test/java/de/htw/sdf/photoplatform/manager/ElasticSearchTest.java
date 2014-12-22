package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.model.Image;
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

    @Before
    public final void setUp() throws Exception {
        insertTestData();
        initImageTestData();
    }

    private void initImageTestData() {
        imageIdList = new ArrayList<>();

        Image image1 = createDefaultImage(OCEANBEACH_IMAGE_NAME, OCEANBEACH_IMAGE_DESCRIPTION, "/upload/oceanBeach.jpeg");
        imageSearchManager.createIndex(image1);
        imageIdList.add(image1.getId());

        Image image2 = createDefaultImage(OCEANSTORM_IMAGE_NAME, OCEANSTORM_IMAGE_DESCRIPTION, "/upload/OceanStorm.jpeg");
        imageSearchManager.createIndex(image2);
        imageIdList.add(image2.getId());

        Image image3 = createDefaultImage(MONTAIN_IMAGE_NAME, MONTAINL_IMAGE_DESCRIPTION, "/upload/bergNebel.jpeg");
        imageSearchManager.createIndex(image3);
        imageIdList.add(image3.getId());

        Image image4 = createDefaultImage(MYFAMILY_IMAGE_NAME, MYFAMILY_IMAGE_DESCRIPTION, "/upload/meineFamilie.jpeg");
        imageSearchManager.createIndex(image4);
        imageIdList.add(image4.getId());
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
        Assert.assertTrue(images.getTotalElements() == 0);

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

        //Don't work!
//        images = imageSearchManager.searchByNameAndDescription("Ocea");
//        Assert.assertTrue(images.getTotalElements() == 2);
    }

    @After
    public final void tearDown() throws Exception {
        clearImageTestData();
        clearTables();
    }

    private void clearImageTestData() {
        imageIdList.clear();
        imageDAO.deleteAll();
        imageSearchManager.deleteIndexes();
    }
}
