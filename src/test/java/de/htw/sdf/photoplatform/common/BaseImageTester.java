package de.htw.sdf.photoplatform.common;

import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.ImageSearchManager;
import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.manager.PurchaseManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.repository.CollectionDAO;
import de.htw.sdf.photoplatform.repository.CollectionImageDAO;
import de.htw.sdf.photoplatform.repository.ImageDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserImageDAO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseImageTester extends BaseTester {
//    private static final int IMAGE_X_DIMENSION = 1920;
//    private static final int IMAGE_Y_DIMENSION = 1080;
//    private static final double IMAGE_X_RESOLUTION = 16;
//    private static final double IMAGE_Y_RESOLUTION = 9;

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

    @Autowired
    protected PurchaseManager purchaseManager;

    @Autowired
    protected ImageSearchManager imageSearchManager;

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
        final BigDecimal price = new BigDecimal(10.18);
        image.setPrice(price);

        return image;
    }

    /**
     * Create default image by name, description and path.
     *
     * @param name image name.
     * @param description image description.
     * @param path image path.
     * @return created image.
     */
    protected Image createDefaultImage(final String name, final String description, final String path) {
        Image image = initDefaultImage(name, Boolean.TRUE, Boolean.TRUE, path);
        image.setDescription(description);
        imageDAO.create(image);
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

    protected void createUserImage(User user, User owner, List<Image> images) {
        for (Image image : images) {
            UserImage userImage = new UserImage();
            userImage.setOwner(owner);
            userImage.setUser(user);
            userImage.setImage(image);
            userImageDAO.create(userImage);
        }
    }

    protected void createUserImage(User user, User owner, Image image) {
        List<Image> images = new ArrayList<>();
        images.add(image);
        createUserImage(user, owner, images);
    }

    protected Collection createCollection(User owner, String name, Boolean isPublic) {
        Collection collection = initEmptyCollection(name, owner);
        collection.setPublic(isPublic);
        collectionDAO.create(collection);
        return collection;
    }
}
