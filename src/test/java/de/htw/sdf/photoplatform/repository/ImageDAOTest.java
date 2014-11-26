package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.model.Image;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class ImageDAOTest extends BaseImageTester {
    @Before
    public final void setUp() throws Exception {
        insertTestData();
    }

    @After
    public final void tearDown() throws Exception {
        clearTables();
    }

    @Test
    public final void testBasic() {
        // Test create
        String testImageName = "TestPhoto";
        String path = "srs/test/testphoto.jpg";
        Image testImage = initDefaultImage(testImageName, Boolean.TRUE,
                Boolean.TRUE, path);
        imageDAO.create(testImage);
        Assert.assertNotNull(testImage.getId());

        // Test find
        Image createdImage = imageDAO.findOne(testImage.getId());
        Assert.assertTrue(createdImage.getId().equals(testImage.getId()));
        Assert.assertTrue(createdImage.getName().equals(testImageName));

        // Test Update
        String oldPath = createdImage.getPath();
        String newPath = "c:/users/test";
        createdImage.setPath(newPath);
        createdImage.setPublic(Boolean.FALSE);
        createdImage.setEnabled(Boolean.FALSE);
        imageDAO.update(createdImage);

        Image updatedImage = imageDAO.findOne(createdImage.getId());
        Assert.assertFalse(updatedImage.getPath().equals(oldPath));
        Assert.assertFalse(updatedImage.isEnabled());
        Assert.assertFalse(updatedImage.isPublic());

        // Test Delete
        Long deletedId = updatedImage.getId();
        imageDAO.deleteById(deletedId);
        Assert.assertNull(imageDAO.findOne(deletedId));
    }

    @Test
    public final void testGetPublicImages() throws Exception {
        // Init test data
        String privateImageName = "TestPrivatePhoto";
        String pathPrivate = "srs/test/privateimage.jpg";

        Image privateImage = initDefaultImage(privateImageName, Boolean.FALSE,
                Boolean.TRUE, pathPrivate);
        imageDAO.create(privateImage);

        String publicImageName = "TestPublicPhoto";
        String pathPublic = "srs/test/publicimage.jpg";
        Image publicImage = initDefaultImage(publicImageName, Boolean.TRUE,
                Boolean.TRUE, pathPublic);
        imageDAO.create(publicImage);

        List<Image> publicImages = imageDAO.getPublicImages();
        boolean isTestImageExistAndPublic = false;
        for (Image image : publicImages) {
            Assert.assertTrue(image.isPublic());
            if (image.getName().equals(publicImageName)) {
                isTestImageExistAndPublic = true;
            }
        }

        Assert.assertTrue(isTestImageExistAndPublic);
    }

}
