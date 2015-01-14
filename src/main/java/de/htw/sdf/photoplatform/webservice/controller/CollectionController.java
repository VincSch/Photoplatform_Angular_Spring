/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;
import de.htw.sdf.photoplatform.webservice.dto.UserData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.CollectionManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;
import de.htw.sdf.photoplatform.webservice.util.ResourceUtility;

/**
 * This controller present the REST collection services.
 * User and Photograph collection functionality.
 *
 * @author Sergej Meister
 */
@RestController
public class CollectionController extends BaseAPIController {

    @Resource
    CollectionManager collectionManager;
    
    @Resource
    private PhotographerManager photographerManager;
    
    /**
     * Returns the collection name and description
     */
    @RequestMapping(value = Endpoints.VIEW_COLLECTION, method = RequestMethod.GET)
    @ResponseBody
    public CollectionData getCollectionData(@PathVariable Long collectionId) throws IOException, AbstractBaseException {
    	Collection collectionData = null;
    	
        try {
        	collectionData = collectionManager.getCollection(collectionId);
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
        
        if(collectionData == null)
        {
        	throw new RuntimeException(messages.getMessage("Collection.notValid"));
        }
        
        //Check if Collection is public or i am the owner
        //it is not public and i have an logged in user and he is not the owner
        User authenticatedUser = getAuthenticatedUser();
        if((!collectionData.isPublic()) && ((authenticatedUser != null) && (authenticatedUser.getId() != collectionData.getUser().getId()))){
            throw new BadRequestException(messages.getMessage("Collection.notPublic"));
        }
        
        return ResourceUtility.convertToCollectionDataIncludingImages(collectionData);
    }

    /**
     * Returns list of collections.
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_ID_IMAGES, method = RequestMethod.GET)
    @ResponseBody
    public List<ImageData> getCollectionsImages(@PathVariable Long collectionId,
                                                @RequestParam(required = false, defaultValue = "-1") int start,
                                                @RequestParam(required = false, defaultValue = "-1") int count) throws IOException, AbstractBaseException {

        //Im not sure, that is ok.
        //Gast should see the collection images too, right?
        User authenticatedUser = getAuthenticatedUser();
        if(authenticatedUser == null){
            throw new BadRequestException(messages.getMessage("User.login.required"));
        }

        Set<CollectionImage> collectionImages = new HashSet<>();

        try {

            collectionImages = collectionManager.getCollectionImages(collectionId,start,count);
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

        return ResourceUtility.convertToCollectionData(collectionImages);
    }

    /**
     * Returns the showcase as a list of collections from specified user.
     */
    @RequestMapping(value = Endpoints.VIEW_SHOWCASE, method = RequestMethod.GET)
    @ResponseBody
    public List<CollectionData> getShowcaseFrom(@RequestParam(required = false, defaultValue = "-1") int start,
                                                @RequestParam(required = false, defaultValue = "-1") int count,
                                                @RequestParam(required = true) String requestUserId)
            throws IOException, AbstractBaseException {

       // User user = findUserById(requestUserId);
        Long userId = Long.valueOf(requestUserId);
        List<Collection> collections = photographerManager.getShowcaseByUser(userId, start, count);


        List<CollectionData> collectionDatas = ResourceUtility.convertToCollectionData(collections);
        for (CollectionData collection : collectionDatas) {
            UserData userToReturn = new UserData();
            userToReturn.setId( collection.getUserdata().getId());
            userToReturn.setFirstName( collection.getUserdata().getFirstName());
            userToReturn.setLastName( collection.getUserdata().getLastName());
            collection.setUserdata(userToReturn);
        }

        return collectionDatas;
    }

    /**
     * Set Thumbnail of Collection.
     *
     * @param collectionId  collection id.
     * @param imageId  image id.
     *
     * @return success message.
     */
    @RequestMapping(value = Endpoints.COLLECTIONS_SET_AS_THUMB_IMAGE, method = RequestMethod.PATCH)
    @ResponseBody
    public String setAsThumbnail(@PathVariable Long collectionId, @PathVariable Long imageId) {

        Collection col = photographerManager.getCollectionById(collectionId);
        photographerManager.updateThumbnail(col, imageId);

        return messages.getMessage("Collection.Thumbnail.set.success");
    }

}
