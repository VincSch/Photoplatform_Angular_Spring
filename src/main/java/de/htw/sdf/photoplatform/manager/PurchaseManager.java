/**
 *
 */
package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface defining business methods for purchaseItem and represent ShoppingCart.
 *
 * @author Sergej Meister
 */
public interface PurchaseManager {

    /**
     * Add image to shopping cart.
     *
     * @param user  user.
     * @param image image.
     * @return created PurchaseItem.
     */
    PurchaseItem addToShoppingCart(final User user, final Image image);

    /**
     * Add image to shopping cart.
     * <p/>
     * Find an collectionImage by image id and check if collection is public.
     *
     * @param user    user.
     * @param imageId id of image.
     * @return created PurchaseItem.
     * @throws ManagerException if image not found or collection is not public.
     */
    PurchaseItem addToShoppingCart(final User user, final Long imageId) throws ManagerException;

    /**
     * Remove image from shopping cart.
     *
     * @param user  user
     * @param image image
     */
    void removeFromShoppingCart(final User user, final Image image);

    /**
     * Remove image from shopping cart.
     *
     * @param item purchaseItem.
     */
    void removeFromShoppingCart(final PurchaseItem item);

    /**
     * Remove image from shopping cart.
     * <p/>
     * Remove image from shopping cart and return the rest of items in shopping cart.
     *
     * @param user           affected user.
     * @param purchaseItemId id of purchaseItem.
     * @return list of rest items in shopping cart.
     * @throws ManagerException if purchaseItem not found.
     */
    List<PurchaseItem> removeFromShoppingCart(final User user, final Long purchaseItemId) throws ManagerException;

    /**
     * Starts the purchase process through paypal
     * 
     * @param items list of items to be purchased
     * @param BaseURL the BaseURL for redirect including protocol and port
     * @return redirect url to paypal
     */
    String startPurchasePerPaypal(List<PurchaseItem> items) throws ManagerException;
    
    /**
     * completes the purchase process through paypal
     */
    void completePurchasePerPaypal(String PaymentId, String PayerID) throws ManagerException;
    
    /**
     * Purchase images.
     * <p/>
     * Update attribute purchased to true.
     *
     * @param items images to buy.
     * @return true, if ok.
     */
    void purchase(List<PurchaseItem> items);

    /**
     * Calculate total price of all images.
     *
     * @param images list of images to buy.
     * @return total price of all images.
     */
    BigDecimal calculatePrice(List<PurchaseItem> images);


    /**
     * Returns all images, that user would like to buy.
     *
     * @param user affected user.
     * @return list of images in shopping cart.
     */
    List<PurchaseItem> getItemsInShoppingCart(User user);

    /**
     * Returns all images, which user has bought.
     *
     * @param user affected user.
     * @return list of images
     */
    List<Image> getUserImages(User user);

}
