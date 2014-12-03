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
            if (!FileUtils.isDirectory(PREFIX)) {
                if (FileUtils.exists(PREFIX)) {
                    FileUtils.delete(PREFIX);
                }
                FileUtils.createDirectories(PREFIX);
            }
            for (MultipartFile file : files) {
                Image img = new Image();
                imageManager.create(img);
                Long id = img.getId();
                String type = file.getContentType();
                if (type.startsWith("image")) {
                    type = type.split("/")[1];
                    try {
                        String hash = hashManager.hash(String.valueOf(id));
                        String path = PREFIX + hash;
                        String originalPath = path + "." + type;
                        String mobileThumbnailPath =
                            path + "_thumbnail_mobile" + "." + type;
                        String smallThumbnailPath =
                            path + "_thumbnail_small" + "." + type;
                        String thumbnailPath = path + "_thumbnail" + "." + type;
                        img.setEnabled(true);
                        img.setName(file.getOriginalFilename());
                        img.setPath(path);
                        img = imageManager.update(img);

                        FileUtils.createFile(originalPath);
                        createThumbnail(file, thumbnailPath, THUMBNAIL_WIDTH,
                            THUMBNAIL_HEIGHT);
                        createThumbnail(file, thumbnailPath,
                            MOBILE_THUMBNAIL_WIDTH,
                            MOBILE_THUMBNAIL_HEIGHT);
                        createThumbnail(file, thumbnailPath,
                            SMALL_THUMBNAIL_WIDTH,
                            SMALL_THUMBNAIL_HEIGHT);

                    } catch (NoSuchAlgorithmException | IOException e) {
                        log.debug(e);
                        return "false";
                    }
                }
            }
        }
        return "true";
    }

    private void createThumbnail(MultipartFile file, String path, int width,
        int height)
        throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        BufferedImage thumbnail =
            Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                width, height, Scalr.OP_ANTIALIAS);
        File outputfile = new File(path);
        ImageIO.write(thumbnail, "jpg", outputfile);
    }

}
