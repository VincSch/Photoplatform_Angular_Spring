
package de.htw.sdf.photoplatform.common;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.persistence.models.Collection;
import de.htw.sdf.photoplatform.persistence.models.CollectionImage;
import de.htw.sdf.photoplatform.persistence.models.Image;
import de.htw.sdf.photoplatform.persistence.models.User;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.ImageDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;

public abstract class BaseImageTester extends BaseTester
{
    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected CollectionDAO collectionDAO;

    @Autowired
    protected ImageDAO imageDAO;

    @Autowired
    protected CollectionImageDAO collectionImageDAO;

    @BeforeClass
    public static void initTest()
    {
        // init test data
    }

    @AfterClass
    public static void close()
    {
        // clear data
    }

    /**
     * Init simple image.
     *
     * NOTE: Path to image should be unique!
     * 
     * @param name
     *            image name.
     * @param isPublic
     *            is public.
     * @param isEnabled
     *            is enabled.
     * @param path
     *            path to image, should be unique!
     * @return image with id = null
     */
    protected Image initDefaultImage(String name, boolean isPublic, boolean isEnabled, String path)
    {
        Image image = new Image();
        image.setName(name);
        image.setCompression("jpg");
        image.setEnabled(isEnabled);
        image.setPublic(isPublic);
        // image no exist, path is wrong!
        image.setPath(path);
        double price = 10.18;
        image.setPrice(price);
        image.setXDemension(1920);
        image.setYDemension(1080);
        image.setXResolution(16);
        image.setYResolution(9);
        image.setResolutionUnit("Inch");

        return image;
    }

    /**
     * Init simple collect with Sergej as user :).
     * 
     * @param name
     *            collection name
     * @return collection with id = null
     */
    protected Collection initEmptyCollection(String name)
    {
        User sergejTestUser = userDAO.findByUserName("Sergej");
        Collection collection = new Collection();
        collection.setName(name);
        collection.setUser(sergejTestUser);
        return collection;
    }

    protected CollectionImage initCollectionImage(Collection collection, Image image)
    {
        CollectionImage collectionImage = new CollectionImage();
        collectionImage.setCollection(collection);
        collectionImage.setImage(image);
        return collectionImage;
    }
}
