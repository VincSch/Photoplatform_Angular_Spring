package de.htw.sdf.photoplatform.webservice.controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;
import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.manager.HashManager;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.ImageSearchManager;
import de.htw.sdf.photoplatform.manager.UserManager;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.security.TokenUtils;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;
import de.htw.sdf.photoplatform.webservice.dto.UserData;
import de.htw.sdf.photoplatform.webservice.util.ResourceUtility;
import org.apache.commons.io.IOUtils;
import org.h2.store.fs.FileUtils;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
    //Search params.
    private static final String SEARCH_PARAM_ALL = "*";
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
    private ImageSearchManager imageSearchManager;

    @Resource
    private HashManager hashManager;


    @PostConstruct
    public void initIt() throws Exception {
        UPLOAD_THUMB_PREFIX = servletContext.getRealPath("/") + THUMB_PREFIX;
    }

    /**
     * Returns list of founded images.
     * <p/>
     * Search images in elasticsearch context.
     */
    @RequestMapping(value = Endpoints.IMAGE_SEARCH, method = RequestMethod.GET)
    @ResponseBody
    public List<ImageData> searchImages(@RequestParam("searchData") String searchData)
            throws IOException, AbstractBaseException {
        Page<Image> foundedImages;
        switch (searchData) {
            case SEARCH_PARAM_ALL:
                foundedImages = imageSearchManager.getAll();
                break;
            default:
                foundedImages = imageSearchManager.searchByNameAndDescription(searchData);
        }

        //return ResourceUtility.convertToImageData(foundedImages);

        List<ImageData> imageDatas = new ArrayList<>();
        for (Image image : foundedImages) {

            //Quick'n'Dirty fix to get the collectionId, images returned by imageSearchManager return null for getCollectionImage
            Image imageWithCollectionId = imageManager.findById(image.getId());

            ImageData imageData = new ImageData(imageWithCollectionId);
            UserData allUserData = new UserData(userManager.findByName(image.getCreatedBy()));
            //for privacy reasons we dont want to return all of the users data
            UserData userDataToShow = new UserData();
            userDataToShow.setId(allUserData.getId());
            userDataToShow.setFirstName(allUserData.getFirstName());
            userDataToShow.setLastName(allUserData.getLastName());

            imageData.setUserData(userDataToShow);
            imageDatas.add(imageData);
        }

        return imageDatas;
    }

    @RequestMapping(value = Endpoints.IMAGE_AS_BYTE, method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<byte[]> getImageAsByte(
            @PathVariable String imageId, @PathVariable String userId, @PathVariable String secToken) throws AbstractBaseException {

        Image requestedImage = imageManager.findById(Long.valueOf(imageId));
        File file = new File(requestedImage.getPath());
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        User user = userManager.findById(Long.valueOf(userId));
        String secHash = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            if (user.getSecToken() != null && !TokenUtils.expired(user.getSecToken())) {
                secHash = new String(Hex.encode(messageDigest.digest(user.getSecToken().getBytes())));
            } else {
                throw new BadRequestException(
                        "This link expired!");
            }

            if (!secToken.equals(secHash)) {
                throw new BadRequestException(
                        "Bad Request!");
            } else {
                return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @RequestMapping(value = "/photographer/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleImageUpload(@RequestParam("files") MultipartFile[] files) {
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
                        return messages.getMessage("Image.upload.error");
                    }
                } else {
                    return messages.getMessage("Image.upload.error");
                }
            }
        }
        return messages.getMessage("Image.upload.success");
    }

}
