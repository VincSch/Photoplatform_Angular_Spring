/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.manager.CollectionManager;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;
import de.htw.sdf.photoplatform.webservice.util.ResourceUtility;

/**
 * This controller present the REST services for images and collections.
 *
 * @author Sergej Meister
 */
@RestController
public class ResourceController extends BaseAPIController {

    @Resource
    private AuthorizationController authorizationController;

    @Resource
    private ImageManager imageManager;

    @Resource
    private CollectionManager collectionManager;

    /**
     * Return list of all images belong to photograph.
     *
     * @return list of all images belong to photograph.
     * @throws java.io.IOException           input output exception.
     * @throws de.htw.sdf.photoplatform.exception.common.AbstractBaseException abstract exception.
     */
    @RequestMapping(value = Endpoints.IMAGES_PHOTOGRAPHERS, method = RequestMethod.GET)
    @ResponseBody
    public List<ImageData> getPhotographersImages()
            throws IOException, AbstractBaseException {
        User authenticatedUser = authorizationController.getAuthenticatedUser();
        List<UserImage> userImages = imageManager.getPhotographImages(authenticatedUser);

        return ResourceUtility.getInstance().convertToImageData(userImages);
    }

    /**
     * Return list of all collections belong to photograph.
     *
     * @return list of all images belong to photograph.
     * @throws java.io.IOException           input output exception.
     * @throws de.htw.sdf.photoplatform.exception.common.AbstractBaseException abstract exception.
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_PHOTOGRAPHERS, method = RequestMethod.GET)
    @ResponseBody
    public List<CollectionData> getPhotographersCollection()
            throws IOException, AbstractBaseException {
        User authenticatedUser = authorizationController.getAuthenticatedUser();
        List<Collection> collections = collectionManager.findByUser(authenticatedUser);

        return ResourceUtility.getInstance().convertToCollectionData(collections);
    }
}
