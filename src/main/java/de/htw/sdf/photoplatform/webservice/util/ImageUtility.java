/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.htw.sdf.photoplatform.persistence.model.UserImage;
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
public class ImageUtility {
    private static ImageUtility ourInstance = new ImageUtility();

    private ImageUtility() {
    }

    public static ImageUtility getInstance() {
        return ourInstance;
    }

    /**
     * Convert list of domain object user to list of transfer objects UserData.
     *
     * @param userImages list of user images.
     * @return list of image data.
     */
    public List<ImageData> convertToImageData(List<UserImage> userImages) {
        List<ImageData> result = new ArrayList<>();
        for (UserImage userImage : userImages) {
            ImageData imageData = new ImageData(userImage);
            result.add(imageData);
        }

        return result;
    }
}
