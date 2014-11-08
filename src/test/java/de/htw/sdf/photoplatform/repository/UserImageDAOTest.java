
package de.htw.sdf.photoplatform.repository;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.models.Image;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.persistence.models.UserImage;

public class UserImageDAOTest extends BaseImageTester
{
    @Autowired
    protected UserImageDAO userImageDAO;

    @Before
    public void setUp() throws Exception
    {
        insertTestData();
    }

    @After
    public void tearDown() throws Exception
    {
        userImageDAO.deleteAll();
        clearTables();
    }

    @Test
    public void testGetUserImagesBy() throws Exception
    {
        // Init test data
        User sergej = userDAO.findByUserName("Sergej");
        String sergejImageName = "sergejImage";
        Image sergejImage = initDefaultImage(
                sergejImageName,
                Boolean.FALSE,
                Boolean.TRUE,
                "c:/users/sergej/images");
        imageDAO.create(sergejImage);
        UserImage sergejUserImage = new UserImage();
        sergejUserImage.setUser(sergej);
        sergejUserImage.setImage(sergejImage);
        userImageDAO.create(sergejUserImage);

        User vincent = userDAO.findByUserName("Vincent");
        String vincentImageName = "vincentImage";
        Image vincentImage = initDefaultImage(
                vincentImageName,
                Boolean.FALSE,
                Boolean.TRUE,
                "c:/users/vincent/images");
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
