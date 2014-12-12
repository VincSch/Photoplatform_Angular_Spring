package de.htw.sdf.photoplatform.common;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.ImageDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserImageDAO;

public abstract class BaseImageTester extends BaseTester {
    private static final int IMAGE_X_DIMENSION = 1920;
    private static final int IMAGE_Y_DIMENSION = 1080;
    private static final double IMAGE_X_RESOLUTION = 16;
    private static final double IMAGE_Y_RESOLUTION = 9;

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected CollectionDAO collectionDAO;

    @Autowired
    protected ImageDAO imageDAO;

    @Autowired
    protected UserImageDAO userImageDAO;

    @Autowired
    protected CollectionImageDAO collectionImageDAO;

    @Autowired
    protected PhotographerManager photographerManager;

    @Autowired
    protected ImageManager imageManager;

    @BeforeClass
    public static void initTest() {
        // init test data
    }

    @AfterClass
    public static void close() {
        // clear data
    }

    /**
     * Init simple image.
     * <p/>
     * NOTE: Path to image should be unique!
     *
     * @param name      image name.
     * @param isPublic  is public.
     * @param isEnabled is enabled.
     * @param path      path to image, should be unique!
     * @return image with id = null
     */
    protected final Image initDefaultImage(final String name,
                                           final boolean isPublic, final boolean isEnabled, final String path) {
        Image image = new Image();
        image.setName(name);
        image.setMetaData("jpg");
        image.setEnabled(isEnabled);
        image.setPublic(isPublic);
        // image no exist, path is wrong!
        image.setPath(path);
        image.setMetaData("");
//        image.setSmallThumbPath("");
//        image.setMobileThumbPath("");
//        image.setThumbPath("");
        final double price = 10.18;
        image.setPrice(price);

        return image;
    }

    /**
     * Init simple collection with Sergej as user :).
     *
     * @param name collection name
     * @return collection with id = null
     */
    protected final Collection initEmptyCollection(final String name) {
        User sergejTestUser = userDAO.findByEmail("sergej@test.de");
        return initEmptyCollection(name, sergejTestUser);
    }

    /**
     * Init simple collection
     *
     * @param name collection name
     * @param user owner
     * @return collection with id = null
     */
    protected final Collection initEmptyCollection(final String name,
                                                   final User user) {
        Collection collection = new Collection();
        collection.setName(name);
        collection.setUser(user);
        return collection;
    }

    protected final CollectionImage initCollectionImage(
            final Collection collection, final Image image) {
        CollectionImage collectionImage = new CollectionImage();
        collectionImage.setCollection(collection);
        collectionImage.setImage(image);
        return collectionImage;
    }
}
