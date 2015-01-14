/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager;

import com.drew.imaging.ImageProcessingException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Interface defining business methods for images.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
public interface ImageManager {

    /**
     * Returns all photograph's images, between start and count.
     * <p/>
     * If start and count lesser than 0, returns all photograph images.
     * <p/>
     * The image belong to photograph, when both the owner and the user is photograph.
     *
     * @param owner photograph.
     * @param start the start.
     * @param count the max.
     * @return Return List of user images.
     */
    List<UserImage> getPhotographImages(User owner, int start, int count);

    /**
     * persist a category.
     *
     * @param entity the category to create
     */
    void create(final Image entity);

    /**
     * update a category.
     *
     * @param entity category you want to update
     * @return the updated category
     */
    Image update(final Image entity);

    /**
     * delete a category.
     *
     * @param entity category to be deleted
     */
    void delete(final Image entity);

    /**
     * find category by its id.
     *
     * @param id category id
     * @return category class
     */
    Image findById(final long id);

    /**
     * find all categories.
     *
     * @return a list of all categories
     */
    List<Image> findAll();

    /**
     * delete all categories.
     */
    void deleteAll();

    /**
     * Update image.
     *
     * @param imageId     image id.
     * @param name        image name.
     * @param price       image price.
     * @param description image description.
     * @param owner       image owner.
     * @return updated image.
     * @throws ManagerException manager exception.
     */
    Image update(Long imageId, String name, BigDecimal price, String description,
        User owner) throws ManagerException;

    /**
     * Adds the owner of the image to the mapping.
     *
     * @param img
     * @param owner
     * @return UserImage
     */
    public UserImage addOwnerToImage(Image img, User owner);

    /**
     * store an file to the filesystem, create appropriate thumbnails and add data base reference
     *
     * @param file
     * @param type
     * @param user
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws ImageProcessingException
     */
    Image storeImage(MultipartFile file, String type, User user)
            throws NoSuchAlgorithmException, IOException, ImageProcessingException;

    /**
     * create the appropriate file structure /upload and webapp/img/upload for thumbnails
     */
    void createDirectoryStructure();
}
