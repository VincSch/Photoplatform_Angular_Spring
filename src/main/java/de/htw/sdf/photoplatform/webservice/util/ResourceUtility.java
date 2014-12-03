/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.htw.sdf.photoplatform.persistence.model.Collection;
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
    public List<ImageData> convertToImageData(List<UserImage> userImages) {
        /**
         * Lassen Sie ,bitte, getInstance drin! Danke!
         *
         * 1) Singleton class can be extended. Polymorphism can save a lot of repetition.
         * 2) A Singleton class can implement an interface, which can come in handy when you want to separate implementation from API.
         * 3)Singleton can be extended. Static not.
         * 4)Singleton creation may not be threadsafe if it isn't implemented properly. Static not.
         * 5)Singleton can be passed around as an object. Static not.
         * 6)Singleton can be garbage collected. Static not.
         * 7)Singleton object stores in Heap but, static object stores in stack
         * 8)We can clone the object of Singleton but, we can not clone the static class object
         * 9)Singleton class follow the OOP(object oriented principles) but not static class
         * 10)Another advantage of a singleton is that it can easily be serialized, which may be necessary if you need to save its state to disc, or send it somewhere remotely.
         *
         * The big difference between a singleton and a bunch of static methods is that singletons can implement interfaces
         * (or derive from useful base classes, although that's less common IME), so you can pass around the singleton as
         * if it were "just another" implementation.
         *
         * Read more: http://javarevisited.blogspot.com/2013/03/difference-between-singleton-pattern-vs-static-class-java.html#ixzz3KpawX7FI
         */
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
    public List<CollectionData> convertToCollectionData(List<Collection> collections) {
        return convertToCollectionData(collections,Boolean.FALSE);
    }

    /**
     * Returns collection without images.
     *
     * @param collections collections.
     *
     * @return list of collection data.
     */
    public CollectionData convertToCollectionData(Collection collection) {
        List<Collection> collections = new ArrayList<>();
        collections.add(collection);

        return convertToCollectionData(collections,Boolean.FALSE).get(0);
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
    public List<CollectionData> convertToCollectionData(List<Collection> collections,Boolean includeImages) {
        List<CollectionData> result = new ArrayList<>();
        for (Collection collection : collections) {
            CollectionData collectionData = new CollectionData(collection,includeImages);
            result.add(collectionData);
        }

        return result;
    }
}
