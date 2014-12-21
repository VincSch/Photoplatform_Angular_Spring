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

    @Resource
    private ImageManager imageManager;

    @Resource
    private UserManager userManager;

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
    @ResponseBody
    String handleImageUpload(
            @RequestParam("files") MultipartFile[] files
    ) {

        User user = this.getAuthenticatedUser();
        if (files.length != 0 &&
                userManager.isUserPhotographer(user)) {
            imageManager.createDirectoryStructure();

            for (MultipartFile file : files) {
                String type = file.getContentType();
                if (type.startsWith("image")) {
                    type = type.split("/")[1];
                    try {
                        Image img = imageManager.storeImage(file, type, user);
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
}
