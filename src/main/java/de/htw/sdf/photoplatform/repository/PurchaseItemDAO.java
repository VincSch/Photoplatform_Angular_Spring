/**
 *
 */
package de.htw.sdf.photoplatform.repository;

import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.common.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for PurchaseItem.
 *
 * @author Sergej Meister
 */
public interface PurchaseItemDAO extends GenericDAO<PurchaseItem> {

    /**
     * Find PurchaseItem bei user and purchased filter.
     * <p/>
     * If purchased true, returns all images, which the user has purchased!
     * If purchased false, return all images, which the user want to buy!
     *
     * @param user      user
     * @param purchased is purchased!
     * @return list of shopping cart's.
     */
    List<PurchaseItem> findByUserAndPurchasedFilter(User user, Boolean purchased);

    /**
     * Find PurchaseItem bei user and purchased filter.
     * <p/>
     * If purchased true, returns all images, which the user has purchased!
     * If purchased false, return all images, which the user want to buy!
     *
     * @param user  user
     * @param image image.
     * @return list of shopping cart's.
     */
    PurchaseItem findByUserAndImage(User user, Image image);
    
    /**
     * Find PurchaseItem by payment id and purchased filter
     *
     * @param PaymentId  PaymentId
     * @param purchased is purchased!
     * @return list of items with that payment id
     */
    List<PurchaseItem> findByPaymentIdAndPurchasedFilter(String PaymentId, Boolean purchased);

    /**
     * Check, is image purchased!
     *
     * @param image image to check.
     *
     * @return true, if image once purchased.
     */
    Boolean isPurchased(Image image);
}
