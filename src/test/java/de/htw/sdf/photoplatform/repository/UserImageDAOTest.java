package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserImageDAOTest extends BaseImageTester {
    @Autowired
    protected UserImageDAO userImageDAO;

    @Before
    public final void setUp() throws Exception {
        insertTestData();
    }

    @After
    public final void tearDown() throws Exception {
        userImageDAO.deleteAll();
        clearTables();
    }

    @Test
    public final void testGetUserImagesBy() throws Exception {
        // Init test data
        User sergej = userDAO.findByEmail("sergej@test.de");
        String sergejImageName = "sergejImage";
        Image sergejImage = initDefaultImage(sergejImageName, Boolean.FALSE,
                Boolean.TRUE, "c:/users/sergej/images");
        imageDAO.create(sergejImage);
        UserImage sergejUserImage = new UserImage();
        sergejUserImage.setUser(sergej);
        sergejUserImage.setImage(sergejImage);
        userImageDAO.create(sergejUserImage);

        User vincent = userDAO.findByEmail("vincent@test.de");
        String vincentImageName = "vincentImage";
        Image vincentImage = initDefaultImage(vincentImageName, Boolean.FALSE,
                Boolean.TRUE, "c:/users/vincent/images");
        imageDAO.create(vincentImage);
        UserImage vincentUserImage = new UserImage();
        vincentUserImage.setUser(vincent);
        vincentUserImage.setImage(vincentImage);
        userImageDAO.create(vincentUserImage);

        // DO TEst
        List<UserImage> sergejImages = userImageDAO.getUserImagesBy(sergej);
        Assert.assertTrue(sergejImages.size() == 1);

        List<UserImage> vincentImages = userImageDAO.getUserImagesBy(vincent);
        Assert.assertTrue(vincentImages.size() == 1);
    }
}
