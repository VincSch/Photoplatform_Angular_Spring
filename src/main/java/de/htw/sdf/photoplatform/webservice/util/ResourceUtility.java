/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;

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

    private ResourceUtility() {
    }

    /**
     * Convert domain object Image to transfer objects ImageData.
     *
     * @param image  image.
     * @return image data.
     */
    public static ImageData convertToImageData(Image image) {
        ImageData imageData = new ImageData(image);
        return imageData;
    }

    /**
     * Convert list of domain object UserImage to list of transfer objects ImageData.
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
     * Utility method to convert DTO to domain.
     *
     * Convert data transfer object CollectionData to domain object collection.
     *
     * @param data data transfer object collection.
     * @param owner collection owner.
     * @return
     */
    public static Collection convertToCollection(CollectionData data, User owner){
        Collection collection = new Collection();
        collection.setId(data.getId());
        collection.setName(data.getName());
        collection.setDescription(data.getDescription());
        collection.setUser(owner);
        collection.setPublic(data.getPublic());
        collection.setThumbnail(null);

        return collection;
    }

    /**
     * Returns collection without images.
     *
     * @param collection collection.
     *
     * @return collection data.
     */
    public static CollectionData convertToCollectionData(Collection collection) {
        return new CollectionData(collection);
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

    /**
     * Returns list of data transfer object  image data.
     *
     * @param collectionImages list of domain object collection image.
     * @return list of data transfer object ImageData.
     */
    public static List<ImageData> convertToCollectionData(Set<CollectionImage> collectionImages) {
        List<ImageData> result = new ArrayList<>();
        for (CollectionImage collectionImage : collectionImages) {
            ImageData imageData = new ImageData(collectionImage.getImage());
            result.add(imageData);
        }

        return result;
    }
}
