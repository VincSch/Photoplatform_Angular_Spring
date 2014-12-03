/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;
import de.htw.sdf.photoplatform.webservice.util.ResourceUtility;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * This controller present the REST user services.
 * All photographer functionality.
 *
 * @author Sergej Meister
 * @author Daniil Tomilow
 */
@RestController
public class PhotographerController extends BaseAPIController {

    @Resource
    private ImageManager imageManager;

    @Resource
    private PhotographerManager photographerManager;

    /**
     * Create new collection
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_PHOTOGRAPHERS, method = RequestMethod.POST)
    @ResponseBody
    public Collection createCollection(@RequestBody CollectionData data,
                                       BindingResult bindingResult) throws IOException, AbstractBaseException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("createCollection", bindingResult);
        }
        User user = getAuthenticatedUser();

        return photographerManager.createCollection(user.getId(), data.getName(), data.getDescription());
    }

    /**
     * Create new collection
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_PHOTOGRAPHERS, method = RequestMethod.GET)
    @ResponseBody
    public List<CollectionData> getCollections(@RequestParam int start,
                                               @RequestParam int count) throws IOException, AbstractBaseException {

        User authenticatedUser = getAuthenticatedUser();
        List<Collection> collections = photographerManager.getCollectionByUser(authenticatedUser.getId(), start, count);

        return ResourceUtility.convertToCollectionData(collections);
    }

    /**
     * Return list of all images belong to photograph.
     *
     * @return list of all images belong to photograph.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.IMAGES_PHOTOGRAPHERS, method = RequestMethod.GET)
    @ResponseBody
    public List<ImageData> getPhotographersImages()
            throws IOException, AbstractBaseException {
        User authenticatedUser = getAuthenticatedUser();
        List<UserImage> userImages = imageManager.getPhotographImages(authenticatedUser);

        return ResourceUtility.convertToImageData(userImages);
    }
}
