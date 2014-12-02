/**
 *
 */
package de.htw.sdf.photoplatform.manager;

import java.util.List;

import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;

/**
 * Interface defining business methods for images.
 *
 * @author Sergej Meister
 */
public interface ImageManager {

    /**
     * Returns all photograph's images.
     *
     * The image belong to photograph, when both the owner and the user is photograph.
     *
     * @param owner photograph.
     * @return Return List of user images.
     */
    List<UserImage> getPhotographImages(User owner);
}
