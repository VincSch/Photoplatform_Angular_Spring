/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a help class for image controller.
 * <p/>
 * Convert domain objects UserImage to corresponded data transfer ImageData.
 *
 *
 * @author Sergej Meister.
 */
@Component
public class ResourceUtility {
    private static ResourceUtility ourInstance = new ResourceUtility();

    private ResourceUtility() {
    }

    public static ResourceUtility getInstance() {
        return ourInstance;
    }

    /**
     * Convert list of domain object user to list of transfer objects UserData.
     *
     * @param userImages list of user images.
     * @return list of image data.
     */
    public static List<ImageData> convertToImageData(List<UserImage> userImages) {
        List<ImageData> result = new ArrayList<>();
        for (UserImage userImage : userImages) {
            ImageData imageData = new ImageData(userImage);
            result.add(imageData);
        }

        return result;
    }

    /**
     * Returns collections without images.
     *
     * @param collections collections.
     *
     * @return list of collection data.
     */
    public static List<CollectionData> convertToCollectionData(List<Collection> collections) {
        return convertToCollectionData(collections,Boolean.FALSE);
    }

    /**
     * Returns collections.
     *
     * If includeImages is true, than collection with all images,
     * otherwise only collection data.
     *
     * @param collections  collections
     * @param includeImages include image.
     * @return
     */
    public static List<CollectionData> convertToCollectionData(List<Collection> collections,Boolean includeImages) {
        List<CollectionData> result = new ArrayList<>();
        for (Collection collection : collections) {
            CollectionData collectionData = new CollectionData(collection,includeImages);
            result.add(collectionData);
        }

        return result;
    }
}
