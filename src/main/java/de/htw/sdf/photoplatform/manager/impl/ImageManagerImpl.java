/*
 *
 * Copyright (C) 2014
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.HashManager;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.ImageSearchManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import org.apache.log4j.Logger;
import org.h2.store.fs.FileUtils;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * business methods for images.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Service
@Transactional
public class ImageManagerImpl extends DAOReferenceCollector implements
        ImageManager {

    private static final int THUMBNAIL_HEIGHT = 1024;
    private static final int THUMBNAIL_WIDTH = 768;
    private static final String THUMBNAIL_NAME = "_thumbnail";
    private static final int MOBILE_THUMBNAIL_HEIGHT = 150;
    private static final int MOBILE_THUMBNAIL_WIDTH = 150;
    private static final String MOBILE_THUMBNAIL_NAME = "_thumbnail_mobile";
    private static final int SMALL_THUMBNAIL_HEIGHT = 250;
    private static final int SMALL_THUMBNAIL_WIDTH = 250;
    private static final String SMALL_THUMBNAIL_NAME = "_thumbnail_small";
    private static String PREFIX = "upload/";
    private static String UPLOAD_THUMB_PREFIX = null;
    private static String THUMB_PREFIX = "/img/upload/";
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private List<String> importantExifTags = new ArrayList<String>();

    @Autowired
    private ImageSearchManager imageSearchManager;

    @Resource
    private HashManager hashManager;

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void initIt() throws Exception {
        importantExifTags.add("Date/Time");
        importantExifTags.add("Data Precision");
        importantExifTags.add("Image Height");
        importantExifTags.add("Image Width");
        importantExifTags.add("Compression Type");
        importantExifTags.add("ISO Speed Ratings");
        importantExifTags.add("Resolution");
        UPLOAD_THUMB_PREFIX = servletContext.getRealPath("/") + "/img/upload/";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getPhotographImages(User owner, int start,
                                               int count) {
        if (start > 0 && count > 0) {
            return userImageDAO.getPhotographImages(owner, start, count);
        }

        return userImageDAO.getPhotographImages(owner);
    }

    @Override
    public void create(Image entity) {
        imageDAO.create(entity);
    }

    @Override
    public Image update(Image entity) {
        return imageDAO.update(entity);
    }

    @Override
    public void delete(Image entity) {
        imageDAO.delete(entity);
    }

    @Override
    public Image findById(long id) {
        return imageDAO.findOne(id);
    }

    @Override
    public List<Image> findAll() {
        return imageDAO.findAll();
    }

    @Override
    public void deleteAll() {
        imageDAO.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image update(Long imageId, String name, BigDecimal price,
                        String description, User owner) throws ManagerException {
        if (imageId == null) {
            throw new ManagerException(
                    AbstractBaseException.PARAM_IS_NOT_VALID);
        }

        UserImage imageToUpdate = userImageDAO
                .getPhotographImage(owner, imageId);
        if (imageToUpdate == null) {
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }
        imageToUpdate.getImage().setName(name);
        imageToUpdate.getImage().setPrice(price);
        imageToUpdate.getImage().setDescription(description);
        imageDAO.update(imageToUpdate.getImage());

        //update image search index.
        imageSearchManager.updateIndex(imageToUpdate.getImage());

        return imageToUpdate.getImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserImage addOwnerToImage(Image img, User owner) {
        UserImage entity = new UserImage();
        entity.setImage(img);
        entity.setOwner(owner);
        entity.setUser(owner);
        entity.setCreatedBy(owner.getUsername());
        userImageDAO.create(entity);
        return entity;
    }

    public void extractExifData(Image img, InputStream fileStream)
            throws IOException, ImageProcessingException {
        BufferedInputStream stream = new BufferedInputStream(fileStream);
        ExifReader exif = new ExifReader();
        Metadata metadata = ImageMetadataReader.readMetadata(stream, true);
        JSONArray json = new JSONArray();
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {

                String desc = tag.getDescription();
                String dirName = tag.getDirectoryName();
                String tagName = tag.getTagName();
                if (importantExifTags.contains(tagName)) {
                    JSONObject jsonTag = new JSONObject();
                    jsonTag.put("tag", tagName);
                    jsonTag.put("description", desc);
                    jsonTag.put("directoryName", dirName);
                    json.put(jsonTag);
                }
            }
        }
        img.setMetaData(json.toString());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createDirectoryStructure() {
        if (!FileUtils.isDirectory(UPLOAD_THUMB_PREFIX)) {
            if (FileUtils.exists(UPLOAD_THUMB_PREFIX)) {
                FileUtils.delete(UPLOAD_THUMB_PREFIX);
            }
            FileUtils.createDirectories(UPLOAD_THUMB_PREFIX);
        }
        if (!FileUtils.isDirectory(PREFIX)) {
            if (FileUtils.exists(PREFIX)) {
                FileUtils.delete(PREFIX);
            }
            FileUtils.createDirectories(PREFIX);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Image storeImage(MultipartFile file, String type, User user)
            throws NoSuchAlgorithmException, IOException, ImageProcessingException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());

        imageDAO.create(image);
        String path =
                PREFIX + hashManager.hash(String.valueOf(image.getId())) + "."
                        + type;
        image.setEnabled(true);
        image.setPath(path);
        image.setMime(type);
        image.setCreatedBy(user.getUsername());
        image.setPrice(new BigDecimal(0));
        String originalPath = image.getPath();
        path = originalPath.split("/\\./")[0];
        BufferedImage imageBuffer = ImageIO
                .read(file.getInputStream());
        storeToDisk(imageBuffer, path, type);
        processThumbnails(imageBuffer, type, image);
        this.extractExifData(image, file.getInputStream());
        image = imageDAO.update(image);
        addOwnerToImage(image, user);
        return image;
    }

    private void processThumbnails(BufferedImage imgBuffer,
                                   String type, Image image) throws IOException, NoSuchAlgorithmException {


        String mobileThumbnailHash = hashManager
                .hash(String.valueOf(image.getId()) + MOBILE_THUMBNAIL_NAME) + "."
                + type;
        image.setMobileThumbPath(THUMB_PREFIX + mobileThumbnailHash);
        String smallThumbnailHash = hashManager
                .hash(String.valueOf(image.getId()) + SMALL_THUMBNAIL_NAME) + "."
                + type;
        image.setSmallThumbPath(THUMB_PREFIX + smallThumbnailHash);
        String thumbnailHash =
                hashManager.hash(String.valueOf(image.getId()) + THUMBNAIL_NAME)
                        + "." + type;
        image.setThumbPath(THUMB_PREFIX + thumbnailHash);

        createThumbnail(imgBuffer, UPLOAD_THUMB_PREFIX + thumbnailHash,
                type,
                THUMBNAIL_WIDTH,
                THUMBNAIL_HEIGHT);
        createThumbnail(imgBuffer, UPLOAD_THUMB_PREFIX + mobileThumbnailHash,
                type,
                MOBILE_THUMBNAIL_WIDTH,
                MOBILE_THUMBNAIL_HEIGHT);
        createThumbnail(imgBuffer, UPLOAD_THUMB_PREFIX + smallThumbnailHash,
                type,
                SMALL_THUMBNAIL_WIDTH,
                SMALL_THUMBNAIL_HEIGHT);
    }

    private void createThumbnail(BufferedImage image, String path, String type,
                                 int width,
                                 int height)
            throws IOException {
        BufferedImage thumbnail =
                Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                        width, height);
        if (!this.storeToDisk(thumbnail, path, type))
            log.warn("Can't write Thumbnail. " + path);
    }

    private boolean storeToDisk(BufferedImage img, String path, String type)
            throws IOException {
        File outputFile = new File(path);
        return ImageIO.write(img, type, outputFile);
    }
}
