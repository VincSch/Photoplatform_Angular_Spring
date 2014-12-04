/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
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

/**
 * This controller present the REST user services.
 * All photographer functionality.
 *
 * @author Sergej Meister
 * @author Daniil Tomilow
 */
@RestController
public class PhotographerController extends BaseAPIController {

    private final static String PARAM_COLLECTION_ID = "collectionId";
    private final static String PARAM_IMAGE_IDS = "imageIds";

    @Resource
    private ImageManager imageManager;

    @Resource
    private PhotographerManager photographerManager;

    /**
     * Create new collection.
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_CREATE, method = RequestMethod.POST)
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
     * Add image or images to one collection.
     *
     * Params: collectionId of Type <code>Long</code> required!
     *         imageIds of Type <code>List<Long></code> required!
     *
     * @param jsonData map of params.
     *         collectionId of Type <code>Long</code> required!
     *         imageIds of Type <code>List<Long></code> required!
     * @param bindingResult binding validate exception.
     * @return success message.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_ADD_IMAGE, method = RequestMethod.POST)
    @ResponseBody
    public String addImageToCollection(@RequestBody String jsonData, BindingResult bindingResult)
            throws IOException, AbstractBaseException {

        String exceptionKey = "collectionAddImage";
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(exceptionKey, bindingResult);
        }

        User authenticatedUser = getAuthenticatedUser();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonData);
        Long collectionId = mapper.convertValue(node.get(PARAM_COLLECTION_ID),
                Long.class);
        List<Long> imageIds = mapper.convertValue(node.get(PARAM_IMAGE_IDS),
                new TypeReference<List<Long>>(){});

        if(imageIds == null){
            imageIds = new ArrayList<>();
        }

        try {
            // Try to add images to collection.
            photographerManager.addImagesToCollection(authenticatedUser.getId(),collectionId,imageIds);
        } catch (ManagerException ex) {
            switch (ex.getCode()) {
                case AbstractBaseException.COLLECTION_ID_NOT_VALID:
                    String msgNotValid = messages.getMessage("Collection.notValid") +
                            messages.getMessage("Collection.addImages.failed") ;
                    bindingResult.addError(new FieldError(exceptionKey, "collectionId",msgNotValid));
                    break;
                case AbstractBaseException.NOT_FOUND:
                    String msgNotFount = messages.getMessage("Collection.addImages.notFound") +
                            messages.getMessage("Collection.addImages.failed") ;
                    bindingResult.addError(new FieldError(exceptionKey, "collectionId",msgNotFount));
                    break;

                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionKey, bindingResult);
        }

        return messages.getMessage("Collection.addImages.success");
    }

    /**
     * Returns list of collections.
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_PHOTOGRAPHERS_START_COUNT, method = RequestMethod.GET)
    @ResponseBody
    public List<CollectionData> getCollections(@PathVariable int start, @PathVariable int count)
            throws IOException, AbstractBaseException {

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
