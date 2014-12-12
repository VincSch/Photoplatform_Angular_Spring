package de.htw.sdf.photoplatform.webservice.controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;
import de.htw.sdf.photoplatform.manager.HashManager;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import org.h2.store.fs.FileUtils;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
import java.security.NoSuchAlgorithmException;

/**
 * Created by patrick on 28.11.14.
 */
@RestController
public class ImageController extends BaseAPIController {
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

    @Autowired
    private ServletContext servletContext;

    @Resource
    private ImageManager imageManager;

    @Resource
    private UserManager userManager;

    @Resource
    private HashManager hashManager;

    @PostConstruct
    public void initIt() throws Exception {
        UPLOAD_THUMB_PREFIX = servletContext.getRealPath("/") + "/img/upload/";
    }

    //    @RequestMapping(value = "/image/{name}", method = RequestMethod.GET)
    //    public
    //    @ResponseBody ResponseEntity<byte[]> getImageAsByte(
    //        @RequestParam("name") String fileName) {
    //        try {
    //            InputStream imageStream = new FileInputStream(
    //                UPLOAD_THUMB_PREFIX + fileName);
    //            final HttpHeaders headers = new HttpHeaders();
    //            headers.setContentType(MediaType.IMAGE_PNG);
    //
    //            return new ResponseEntity<byte[]>(IOUtils.toByteArray(imageStream),
    //                headers, HttpStatus.CREATED);
    //        } catch (IOException e) {
    //            log.warn(e);
    //            throw new RuntimeException(e);
    //        }
    //    }

    @RequestMapping(value = "/photographer/upload", method = RequestMethod.POST)
    public
    @ResponseBody String handleImageUpload(
        @RequestParam("files") MultipartFile[] files
    ) {

        User user = this.getAuthenticatedUser();
        if (files.length != 0 &&
            userManager.isUserPhotographer(user)) {
            this.createDirectoryStructure();

            for (MultipartFile file : files) {
                String type = file.getContentType();
                if (type.startsWith("image")) {
                    type = type.split("/")[1];
                    try {
                        Image img = storeImage(file, type);
                        imageManager.addOwnerToImage(img, user);
                    } catch (NoSuchAlgorithmException | IOException e) {
                        log.error(e);
                        return "false";
                    } catch (ImageProcessingException e) {
                        log.error(e);
                    }
                } else {
                    log.warn("File isn't an image: media type not accepted");
                }
            }
        }
        return "image was uploaded";
    }

    private void extractExifData(Image img, InputStream fileStream)
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
                int tagType = tag.getTagType();
                JSONObject jsonTag = new JSONObject();
                jsonTag.put("tag", tagName);
                jsonTag.put("description", desc);
                jsonTag.put("directoryName", dirName);
                json.put(jsonTag);

            }
        }
        img.setMetaData(json.toString());

    }

    private void createDirectoryStructure() {
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

    private Image storeImage(MultipartFile file, String type)
        throws NoSuchAlgorithmException, IOException, ImageProcessingException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        imageManager.create(image);
        String path =
            PREFIX + hashManager.hash(String.valueOf(image.getId())) + "."
                + type;
        image.setEnabled(true);
        image.setPath(path);
        image.setMime(type);
        image.setCreatedBy(this.getAuthenticatedUser().getUsername());
        String originalPath = image.getPath();
        path = originalPath.split("/\\./")[0];
        BufferedImage imageBuffer = ImageIO
            .read(file.getInputStream());
        storeToDisk(imageBuffer, path, type);
        processThumbnails(imageBuffer, type, image);
        image = imageManager.update(image);
        this.extractExifData(image, file.getInputStream());
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
