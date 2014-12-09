package de.htw.sdf.photoplatform.webservice.controller;

import de.htw.sdf.photoplatform.manager.HashManager;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import org.apache.commons.io.IOUtils;
import org.h2.store.fs.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

/**
 * Created by patrick on 28.11.14.
 */
@RestController
public class ImageController extends BaseAPIController {
    @Autowired
    ServletContext servletContext;

    private static String PREFIX = null;
    private static String UPLOAD_THUMB_PREFIX = null;
    private static String THUMB_PREFIX = null;


    private static final int THUMBNAIL_HEIGHT = 1024;
    private static final int THUMBNAIL_WIDTH = 768;
    private static final String THUMBNAIL_NAME = "_thumbnail";

    private static final int MOBILE_THUMBNAIL_HEIGHT = 150;
    private static final int MOBILE_THUMBNAIL_WIDTH = 150;
    private static final String MOBILE_THUMBNAIL_NAME = "_thumbnail_mobile";

    private static final int SMALL_THUMBNAIL_HEIGHT = 250;
    private static final int SMALL_THUMBNAIL_WIDTH = 250;
    private static final String SMALL_THUMBNAIL_NAME = "_thumbnail_small";

    @Resource
    private ImageManager imageManager;

    @Resource
    private UserManager userManager;

    @Resource
    private HashManager hashManager;

    @PostConstruct
    public void initIt() throws Exception {
        UPLOAD_THUMB_PREFIX = servletContext.getRealPath("/") + "/img/upload/";
        THUMB_PREFIX = "/img/upload/";
        PREFIX = "upload/";
    }

    @RequestMapping(value = "/image/{name}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<byte[]> getImageAsByte(
            @RequestParam("name") String fileName) {
        try {
            InputStream imageStream = new FileInputStream(UPLOAD_THUMB_PREFIX + fileName);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<byte[]>(IOUtils.toByteArray(imageStream),
                    headers, HttpStatus.CREATED);
        } catch (IOException e) {
            log.warn(e);
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/photographer/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleImageUpload(
            @RequestParam("files") MultipartFile[] files
    ) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (files.length != 0 &&
                userManager.isUserPhotographer(user)) {
            this.createDirectoryStructure();

            for (MultipartFile file : files) {
                String type = file.getContentType();
                if (type.startsWith("image")) {
                    type = type.split("/")[1];
                    try {
                        storeImage(file, type, user);
                    } catch (NoSuchAlgorithmException | IOException e) {
                        log.debug(e);
                        return "false";
                    }
                } else {
                    log.warn("File isn't an image: media type not accepted");
                }
            }
        }
        return "image was uploaded";
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

    private boolean storeImage(MultipartFile file, String type, User user) throws NoSuchAlgorithmException, IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        imageManager.create(image);
        String path = PREFIX + hashManager.hash(String.valueOf(image.getId())) + "." + type;
        image.setEnabled(true);
        image.setPath(path);
        String originalPath = image.getPath();
        path = originalPath.split("/\\./")[0];
        BufferedImage imageBuffer = ImageIO
                .read(file.getInputStream());
        storeToDisk(imageBuffer, path, type);
        processThumbnails(imageBuffer, type, image);
        imageManager.update(image);
        return true;
    }

    private void processThumbnails(BufferedImage imgBuffer,
                                   String type, Image image) throws IOException, NoSuchAlgorithmException {

        String mobileThumbnailHash = hashManager.hash(String.valueOf(image.getId()) + MOBILE_THUMBNAIL_NAME) + "." + type;
        image.setMobileThumbPath(THUMB_PREFIX + mobileThumbnailHash);
        String smallThumbnailHash = hashManager.hash(String.valueOf(image.getId()) + SMALL_THUMBNAIL_NAME) + "." + type;
        image.setSmallThumbPath(THUMB_PREFIX + smallThumbnailHash);
        String thumbnailHash = hashManager.hash(String.valueOf(image.getId()) + THUMBNAIL_NAME) + "." + type;
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
