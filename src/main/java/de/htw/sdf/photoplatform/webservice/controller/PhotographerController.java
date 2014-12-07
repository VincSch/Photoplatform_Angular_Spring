/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * This controller present the REST user services.
 * All photographer functionality.
 *
 * @author Sergej Meister
 * @author Daniil Tomilow
 */
@RestController
public class PhotographerController extends BaseAPIController {

    private final static String PARAM_COLLECTION_ID = "id";
    private final static String PARAM_IMAGE_IDS = "imageIds";
    private final static String PARAM_IS_PUBLIC = "isPublic";

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
     * <p>
     * Params: collectionId of Type <code>Long</code> required!
     * imageIds of Type <code>List<Long></code> required!
     *
     * @param jsonData      map of params.
     *                      collectionId of Type <code>Long</code> required!
     *                      imageIds of Type <code>List<Long></code> required!
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
                new TypeReference<List<Long>>() {
                });

        try {
            // Try to add images to collection.
            photographerManager.addImagesToCollection(authenticatedUser.getId(), collectionId, imageIds);
        } catch (ManagerException ex) {
            switch (ex.getCode()) {
                case AbstractBaseException.COLLECTION_ID_NOT_VALID:
                    String msgNotValid = messages.getMessage("Collection.notValid") +
                            messages.getMessage("Collection.addImages.failed");
                    bindingResult.addError(new FieldError(exceptionKey, "collectionId", msgNotValid));
                    break;
                case AbstractBaseException.NOT_FOUND:
                    String msgNotFount = messages.getMessage("Collection.Images.notFound") +
                            messages.getMessage("Collection.addImages.failed");
                    bindingResult.addError(new FieldError(exceptionKey, "collectionId", msgNotFount));
                    break;

                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionKey, bindingResult);
        }

        return messages.getMessage("Collection.addImages.success");
    }

    /**
     * Delete image or images from collection.
     * <p>
     * Params: collectionId of Type <code>Long</code> required!
     * imageIds of Type <code>List<Long></code> required!
     *
     * @param jsonData      map of params.
     *                      collectionId of Type <code>Long</code> required!
     *                      imageIds of Type <code>List<Long></code> required!
     * @param bindingResult binding validate exception.
     * @return success message.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_DELETE_IMAGE, method = RequestMethod.POST)
    @ResponseBody
    public String deleteImageFromCollection(@RequestBody String jsonData, BindingResult bindingResult)
            throws IOException, AbstractBaseException {
        String exceptionKey = "collectionDeleteImage";
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(exceptionKey, bindingResult);
        }

        User authenticatedUser = getAuthenticatedUser();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonData);
        Long collectionId = mapper.convertValue(node.get(PARAM_COLLECTION_ID),
                Long.class);
        List<Long> imageIds = mapper.convertValue(node.get(PARAM_IS_PUBLIC),
                new TypeReference<List<Long>>() {
                });

        try {
            // Try to add images to collection.
            photographerManager.deleteImagesFromCollection(authenticatedUser.getId(), collectionId, imageIds);
        } catch (ManagerException ex) {
            switch (ex.getCode()) {
                case AbstractBaseException.COLLECTION_ID_NOT_VALID:
                    String msgNotValid = messages.getMessage("Collection.notValid") +
                            messages.getMessage("Collection.deleteImages.failed");
                    bindingResult.addError(new FieldError(exceptionKey, "collectionId", msgNotValid));
                    break;
                case AbstractBaseException.NOT_FOUND:
                    String msgNotFount = messages.getMessage("Collection.Images.notFound") +
                            messages.getMessage("Collection.deleteImages.failed");
                    bindingResult.addError(new FieldError(exceptionKey, "collectionId", msgNotFount));
                    break;

                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionKey, bindingResult);
        }

        return messages.getMessage("Collection.deleteImages.success");
    }

    /**
     * Delete image or images from collection.
     * <p>
     * Params: collectionId of Type <code>Long</code> required!
     * imageIds of Type <code>List<Long></code> required!
     *
     * @param collectionId collection to delete.
     * @return success message.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_DELETE, method = RequestMethod.POST)
    @ResponseBody
    public String deleteCollection(@RequestParam(value = "collectionId", required = true) Long collectionId)
            throws IOException, AbstractBaseException {

        User authenticatedUser = getAuthenticatedUser();
        try {
            // Try to add images to collection.
            photographerManager.deleteCollection(authenticatedUser.getId(), collectionId);
        } catch (ManagerException ex) {
            String exceptionMsg;
            switch (ex.getCode()) {
                case AbstractBaseException.COLLECTION_ID_NOT_VALID:
                    exceptionMsg = messages.getMessage("Collection.notValid") +
                            messages.getMessage("Collection.delete.failed");
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }

        return messages.getMessage("Collection.delete.success");
    }

    /**
     * Add collection to showcase or delete from showcase.
     * <p>
     * Params: collectionId of Type <code>Long</code> required!
     * isPublic of Type <code>Boolean<Long></code> required!
     *
     * @return success message.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_SHOWCASE, method = RequestMethod.POST)
    @ResponseBody
    public String updateCollectionShowcase(@RequestBody String jsonData)
            throws IOException, AbstractBaseException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonData);
        Long collectionId = mapper.convertValue(node.get(PARAM_COLLECTION_ID),
                Long.class);
        Boolean isPublic = mapper.convertValue(node.get(PARAM_IS_PUBLIC),
                Boolean.class);

        User authenticatedUser = getAuthenticatedUser();
        try {
            photographerManager.updateCollectionsPublicValue(authenticatedUser.getId(), collectionId, isPublic);
        } catch (ManagerException ex) {
            String exceptionMsg;
            switch (ex.getCode()) {
                case AbstractBaseException.COLLECTION_ID_NOT_VALID:
                    exceptionMsg = messages.getMessage("Collection.notValid");
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }

        if (isPublic) {
            return messages.getMessage("Collection.showcase.add.success");
        } else {
            return messages.getMessage("Collection.showcase.remove.success");
        }
    }

    /**
     * Update collection.
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_PHOTOGRAPHERS, method = RequestMethod.PATCH)
    @ResponseBody
    public CollectionData updateCollection(@Valid @RequestBody CollectionData data,
                                           BindingResult bindingResult) throws IOException, AbstractBaseException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("updateCollection", bindingResult);
        }

        User user = getAuthenticatedUser();

        // FIXME this code belongs to service..not in a controller
        Collection collection = new Collection();
        collection.setId(data.getId());
        collection.setName(data.getName());
        collection.setDescription(data.getDescription());
        collection.setUser(user);
        collection.setPublic(data.getPublic());
        collection.setThumbnail(null);

        collection = photographerManager.update(collection);

        return ResourceUtility.convertToCollectionData(collection);
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
     * Returns list of collections.
     */
    @RequestMapping(value = Endpoints.SHOWCASE, method = RequestMethod.GET)
    @ResponseBody
    public List<CollectionData> getShowcase(@RequestParam(required = false, defaultValue = "-1") int start,
                                            @RequestParam(required = false, defaultValue = "-1") int count)
            throws IOException, AbstractBaseException {

        User authenticatedUser = getAuthenticatedUser();
        List<Collection> collections = photographerManager.getShowcaseByUser(authenticatedUser.getId(), start, count);

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
