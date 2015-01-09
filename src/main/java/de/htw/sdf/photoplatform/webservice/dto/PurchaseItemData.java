/**
 *
 */
package de.htw.sdf.photoplatform.webservice.dto;

import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;

/**
 * Data transfer object represent Domain <code>PurchaseItem</code>.
 *
 * @author Sergej Meister
 */
public class PurchaseItemData {

    private Long id;
    private ImageData image;

    public PurchaseItemData(PurchaseItem item) {
        this.id = item.getId();
        this.image = new ImageData(item.getImage());
    }

    /**
     * Return purchaseItem id.
     *
     * @return PurchaseItem.id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets purchaseItem id.
     *
     * @param id PurchaseItem.id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns data transfer object imageData.
     *
     * @return data transfer object imageData.
     */
    public ImageData getImage() {
        return image;
    }

    /**
     * Sets data transfer object imageData.
     *
     * @param image data transfer object imageData.
     */
    public void setImage(ImageData image) {
        this.image = image;
    }
}
