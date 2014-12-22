package de.htw.sdf.photoplatform.webservice.controller;

import com.drew.imaging.ImageProcessingException;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
                        imageManager.storeImage(file, type, user);
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
        return messages.getMessage("Image.upload.success");
    }
}
