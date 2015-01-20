/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;
import de.htw.sdf.photoplatform.webservice.util.ResourceUtility;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.io.IOException;
import java.math.RoundingMode;
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

    private final static String PARAM_COLLECTION_ID = "id";
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
     * @param collectionId collectionId of Type <code>Long</code> required!     map of params.
     * @param imageIds imageIds of Type <code>List<Long></code> required!
     * @return success message.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_ID_IMAGES, method = RequestMethod.PUT)
    @ResponseBody
    public String addImageToCollection(@PathVariable Long collectionId, @RequestParam(required = true) List<Long> imageIds)
            throws IOException, AbstractBaseException {

        User authenticatedUser = getAuthenticatedUser();

        try {
            // Try to add images to collection.
            photographerManager.addImagesToCollection(authenticatedUser.getId(), collectionId, imageIds);
        } catch (ManagerException ex) {
            String exceptionMsg ;
            switch (ex.getCode()) {
                case AbstractBaseException.COLLECTION_ID_NOT_VALID:
                    exceptionMsg = messages.getMessage("Collection.notValid") +
                            messages.getMessage("Collection.addImages.failed");
                    break;
                case AbstractBaseException.NOT_FOUND:
                    exceptionMsg = messages.getMessage("Collection.Images.notFound") +
                            messages.getMessage("Collection.addImages.failed");
                    break;
                case AbstractBaseException.IMAGE_PRICE_EMPTY:
                    exceptionMsg = messages.getMessage("Image.price.empty") +
                            messages.getMessage("Collection.addImages.failed");
                    break;
                case AbstractBaseException.IMAGE_NAME_EMPTY:
                    exceptionMsg = messages.getMessage("Image.name.empty") +
                            messages.getMessage("Collection.addImages.failed");
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }

        return messages.getMessage("Collection.addImages.success");
    }

    /**
     * Delete image or images from collection.
     * <p>
     * Params: collectionId of Type <code>Long</code> required!
     * imageIds of Type <code>List<Long></code> required!
     *
     * @param collectionId  collection id.
     * @param imageId  image id.
     *
     * @return success message.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_DELETE_IMAGE, method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteImageFromCollection(@PathVariable Long collectionId, @PathVariable Long imageId)
            throws IOException, AbstractBaseException {

        User authenticatedUser = getAuthenticatedUser();

        try {
            // Try to add images to collection.
            photographerManager.deleteImagesFromCollection(authenticatedUser.getId(), collectionId, imageId);
        } catch (ManagerException ex) {
            String exceptionMsg ;
            switch (ex.getCode()) {
                case AbstractBaseException.COLLECTION_ID_NOT_VALID:
                    exceptionMsg = messages.getMessage("Collection.notValid") +
                            messages.getMessage("Collection.deleteImage.failed");
                    break;
                case AbstractBaseException.NOT_FOUND:
                    exceptionMsg = messages.getMessage("Collection.Images.notFound") +
                            messages.getMessage("Collection.deleteImage.failed");
                    break;

                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }

        return messages.getMessage("Collection.deleteImage.success");
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
    @RequestMapping(value = Endpoints.COLLECTIONS, method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCollection(@PathVariable(value = "collectionId") Long collectionId)
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

        User authenticatedUser = getAuthenticatedUser();
        Collection collection = ResourceUtility.convertToCollection(data,authenticatedUser);
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
    @RequestMapping(value = Endpoints.PHOTOGRAPHERS_IMAGES, method = RequestMethod.GET)
    @ResponseBody
    public List<ImageData> getPhotographersImages(@RequestParam(required = false) Boolean isAdded,
                                                  @RequestParam(required = false, defaultValue = "-1") int start,
                                                  @RequestParam(required = false, defaultValue = "-1") int count)
            throws IOException, AbstractBaseException {
        User authenticatedUser = getAuthenticatedUser();
        if(isAdded == null){
            List<UserImage> userImages = imageManager.getPhotographImages(authenticatedUser,start,count);
            return ResourceUtility.convertToImageData(userImages);
        }else{
            if(!isAdded) {
                List<UserImage> userImages = photographerManager.getImagesWithoutCollection(authenticatedUser);
                return ResourceUtility.convertToImageData(userImages);
            }else{
                throw new NotImplementedException();
            }
        }
    }


    /**
     * Delete a photograph image.
     *
     * @return success message if ok, otherwise exception.
     * @throws java.io.IOException   input output exception.
     * @throws AbstractBaseException the exception
     */
    @RequestMapping(value = Endpoints.PHOTOGRAPHERS_IMAGES_ID, method = RequestMethod.DELETE)
    @ResponseBody
    public String deletePhotographersImage(@PathVariable(value = "imageId") Long imageId)
            throws IOException, AbstractBaseException {
        User authenticatedUser = getAuthenticatedUser();
        try {
            photographerManager.deleteImage(authenticatedUser.getId(),imageId);
        } catch (ManagerException ex) {
            String exceptionMsg;
            switch (ex.getCode()) {
                case AbstractBaseException.PARAM_IS_NOT_VALID:
                    exceptionMsg = messages.getMessage("Image.notValid").replace("(id)",imageId.toString()) +
                            messages.getMessage("Image.delete.failed");
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }

        return messages.getMessage("Image.delete.success");
    }

    /**
     * Update collection.
     */
    @RequestMapping(value = Endpoints.PHOTOGRAPHERS_IMAGES, method = RequestMethod.PATCH)
    @ResponseBody
    public ImageData updateImage(@Valid @RequestBody ImageData data,
                                           BindingResult bindingResult) throws IOException, AbstractBaseException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("updateCollection", bindingResult);
        }

        User authenticatedUser = getAuthenticatedUser();
        ImageData response;
        Image updatedImage;
        
        //Round Price to only two decimal points and force positive price
        data.setPrice(data.getPrice().setScale(2, RoundingMode.DOWN).abs());
        
        try {
            updatedImage = imageManager.update(data.getId(), data.getName(), data.getPrice(), data.getDescription(), authenticatedUser);
        }catch (ManagerException ex) {
            String exceptionMsg;
            switch (ex.getCode()) {
                case AbstractBaseException.PARAM_IS_NOT_VALID:
                    exceptionMsg = messages.getMessage("Image.notValid").replace("(id)",data.getId().toString()) +
                            messages.getMessage("Image.update.failed");
                    break;
                case AbstractBaseException.NOT_FOUND:
                    exceptionMsg = messages.getMessage("Image.notValid").replace("(id)",data.getId().toString()) +
                            messages.getMessage("Image.update.failed");
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }
        response = ResourceUtility.convertToImageData(updatedImage);
        response.setMessageSuccess(messages.getMessage("Image.update.success"));
        return response;
    }
}
