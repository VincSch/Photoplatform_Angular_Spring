/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

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
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
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

}
