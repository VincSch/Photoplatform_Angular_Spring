package de.htw.sdf.photoplatform.webservice.controller;

import de.htw.sdf.photoplatform.manager.HashManager;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import org.h2.store.fs.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by patrick on 28.11.14.
 */
@RestController
public class ImageController extends BaseAPIController {
    public static final String IMG_THUMBNAIL_URI = "/image/thumbnail.jpg";
    public static final String THUMBNAIL_REQ_URI = "/thumbnail";
    private static final String PREFIX = "uploads/";

    private static final int THUMBNAIL_HEIGHT = 1024;
    private static final int THUMBNAIL_WIDTH = 768;

    private static final int MOBILE_THUMBNAIL_HEIGHT = 150;
    private static final int MOBILE_THUMBNAIL_WIDTH = 150;

    private static final int SMALL_THUMBNAIL_HEIGHT = 250;
    private static final int SMALL_THUMBNAIL_WIDTH = 250;

    @Resource
    private ImageManager imageManager;

    @Resource
    private UserManager userManager;

    @Resource
    private HashManager hashManager;

    @RequestMapping(value = "/photographer/upload", method = RequestMethod.POST)
    public @ResponseBody String handleImageUpload(
        @RequestParam("files") MultipartFile[] files
    ) {
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (files.length != 0 &&
            userManager.isUserPhotographer(user)) {
            this.createFilesystemStruture();

            for (MultipartFile file : files) {
                String type = file.getContentType();
                if (type.startsWith("image")) {
                    type = type.split("/")[1];
                    try {
                        String path = "";
                        Image img = this.persistEntity(file, type);
                        String originalPath = img.getPath();
                        path = originalPath.split("/\\./")[0];
                        BufferedImage image = ImageIO
                            .read(file.getInputStream());
                        this.saveImage(image, originalPath, type);
                        this.processThumbnails(image, path,
                            type);
                    } catch (NoSuchAlgorithmException | IOException e) {
                        log.debug(e);
                        return "false";
                    }
                } else {
                    log.warn("File isn't an image: unallowed type");
                }
            }
        }
        return "true";
    }

    private void processThumbnails(BufferedImage img, String path,
        String type) throws IOException {

        String mobileThumbnailPath =
            path + "_thumbnail_mobile" + "." + type;
        String smallThumbnailPath =
            path + "_thumbnail_small" + "." + type;
        String thumbnailPath = path + "_thumbnail" + "." + type;
        createThumbnail(img, thumbnailPath,
            type,
            THUMBNAIL_WIDTH,
            THUMBNAIL_HEIGHT);
        createThumbnail(img, mobileThumbnailPath,
            type,
            MOBILE_THUMBNAIL_WIDTH,
            MOBILE_THUMBNAIL_HEIGHT);
        createThumbnail(img, smallThumbnailPath,
            type,
            SMALL_THUMBNAIL_WIDTH,
            SMALL_THUMBNAIL_HEIGHT);
    }

    private void createFilesystemStruture() {
        if (!FileUtils.isDirectory(PREFIX)) {
            if (FileUtils.exists(PREFIX)) {
                FileUtils.delete(PREFIX);
            }
            FileUtils.createDirectories(PREFIX);
        }
    }

    private Image persistEntity(MultipartFile file, String type)
        throws NoSuchAlgorithmException {
        Image img = new Image();
        imageManager.create(img);
        String hash = hashManager.hash(String.valueOf(img.getId()));
        String path = PREFIX + hash;
        String originalPath = path + "." + type;

        img.setEnabled(true);
        img.setName(file.getOriginalFilename());
        img.setPath(path);
        return imageManager.update(img);
    }

    private boolean saveImage(BufferedImage img, String path, String type)
        throws IOException {
        File outputfile = new File(path);
        return ImageIO.write(img, type, outputfile);
    }

    private void createThumbnail(BufferedImage image, String path, String type,
        int width,
        int height)
        throws IOException {
        BufferedImage thumbnail =
            Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                width, height);
        if (!this.saveImage(thumbnail, path, type))
            log.warn("Can't write Thumbnail. " + path);
    }

}
