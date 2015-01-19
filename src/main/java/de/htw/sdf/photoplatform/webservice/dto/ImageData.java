/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.UserImage;

import java.io.Serializable;
import java.math.BigDecimal;

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

    private BigDecimal price;

    private String priceAsString;

    private String mime;

    private String metadata;

    private String path;

    private String smallPath;

    private String thumbnailPath;

    private String mobilePath;

    private UserData userData;

    private Long collectionId;

    private Boolean isAdded;

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
        setPrice(image.getPrice());
        this.mime = image.getMime();
        this.metadata = image.getMetaData();
        this.thumbnailPath = image.getThumbPath();
        this.smallPath = image.getSmallThumbPath();
        this.mobilePath = image.getMobileThumbPath();
        this.collectionId = -1L;
        if (!image.getCollectionImages().isEmpty()) {
            this.collectionId = image.getCollectionImages().get(0).getCollection().getId();
        }
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
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets image price.
     *
     * @param price image price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
        this.priceAsString = PurchaseData.NUMBER_FORMAT.format(price);
    }

    /**
     * Returns price of image.
     *
     * @return image price.
     */
    public String getPriceAsString() {
        return priceAsString;
    }

    /**
     * Sets image price.
     * Only needed for JSON conversion
     * 
     * @param price image price.
     */
    public void setPriceAsString(String price) {
        this.priceAsString = price;
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

    /**
     * Returns origin image path.
     *
     * @return path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets origin image path.
     *
     * @param path image path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns thumbnail path - enhanced desktop view.
     * <p/>
     * by image click.
     *
     * @return thumbnail path.
     */
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    /**
     * Sets thumbnail path for enhanced desktop view.
     *
     * @param thumbnailPath thumbnail path.
     */
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    /**
     * Returns default thumbnail view for mobile and desktop.
     *
     * @return default thumbnail.
     */
    public String getSmallPath() {
        return smallPath;
    }

    /**
     * Sets default thumbnail.
     *
     * @param smallPath default thumbnail.
     */
    public void setSmallPath(String smallPath) {
        this.smallPath = smallPath;
    }

    /**
     * Returns thumbnail path for mobile devices.
     *
     * @return mobile thumbnail path.
     */
    public String getMobilePath() {
        return mobilePath;
    }

    /**
     * Sets mobile thumbnail path.
     *
     * @param mobilePath mobile thumbnail path.
     */
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

    /**
     * Returns the collection id.
     *
     * @return collection id.
     */
    public Long getCollectionId() {
        return collectionId;
    }

    /**
     * Sets collection id.
     *
     * @param id collection id.
     */
    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    /**
     * Is added to collection.
     * <p/>
     * True if added.
     *
     * @return added status.
     */
    public Boolean getAdded() {
        return isAdded;
    }

    /**
     * Sets added value.
     *
     * @param isAdded added value.
     */
    public void setAdded(Boolean isAdded) {
        this.isAdded = isAdded;
    }
}
