/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.UserImage;

import java.io.Serializable;

/**
 * Data transfer object to get and update image data.
 * Represents the domain object Image.
 *
 * @author Sergej Meister
 */
public class ImageData extends ResponseMessageData implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String mime;

    private String path;

    private String metadata;

    private String thumbnailPath;

    private String smallPath;

    private String mobilePath;

    private UserData userData;

    /**
     * Default empty constructor.
     */
    public ImageData() {
        //Default empty constructor.
    }

    /**
     * Constructor for UserImage.
     *
     * @param userImage user image.
     */
    public ImageData(UserImage userImage) {
        this(userImage.getImage());
        this.userData = new UserData(userImage.getUser());
    }

    /**
     * Constructor for Image.
     *
     * @param image user image.
     */
    public ImageData(Image image) {
        this.id = image.getId();
        this.name = image.getName();
        this.description = image.getDescription();
        this.price = image.getPrice();
        this.mime = image.getMime();
        this.metadata = image.getMetaData();
        this.thumbnailPath = image.getThumbPath();
        this.smallPath = image.getSmallThumbPath();
        this.mobilePath = image.getMobileThumbPath();
    }

    /**
     * Returns image id.
     *
     * @return image id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets image id.
     *
     * @param id image id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns image name.
     *
     * @return name of image.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets image name.
     *
     * @param name name of image.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns image description.
     *
     * @return description of image.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description image.
     *
     * @param description image description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns price of image.
     *
     * @return image price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets image price.
     *
     * @param price image price.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns path.
     *
     * @return path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets image path.
     *
     * @param path image path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getSmallPath() {
        return smallPath;
    }

    public void setSmallPath(String smallPath) {
        this.smallPath = smallPath;
    }

    public String getMobilePath() {
        return mobilePath;
    }

    public void setMobilePath(String mobilePath) {
        this.mobilePath = mobilePath;
    }

    /**
     * Returns user data.
     *
     * @return user data.
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * Sets user data.
     *
     * @param userData user data.
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
