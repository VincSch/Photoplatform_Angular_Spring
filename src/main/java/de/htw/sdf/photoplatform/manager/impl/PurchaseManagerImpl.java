/**
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.manager.PurchaseManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for purchase.
 *
 * @author Sergej Meister
 */
@Service
@Transactional
public class PurchaseManagerImpl extends DAOReferenceCollector implements PurchaseManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchaseItem addToShoppingCart(User user, Image image) {
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setUser(user);
        purchaseItem.setImage(image);
        purchaseItem.setPurchased(Boolean.FALSE);
        purchaseItemDAO.create(purchaseItem);
        return purchaseItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFromShoppingCart(User user, Image image) {
        PurchaseItem purchaseItem = purchaseItemDAO.findByUserAndImage(user, image);
        removeFromShoppingCart(purchaseItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFromShoppingCart(PurchaseItem item) {
        purchaseItemDAO.delete(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void purchase(List<PurchaseItem> items) {
        for (PurchaseItem item : items) {
            item.setPurchased(Boolean.TRUE);
            purchaseItemDAO.update(item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double calculatePrice(List<PurchaseItem> images) {
        Double result = 0.0;
        for (PurchaseItem item : images) {
            result = result + item.getImage().getPrice();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PurchaseItem> getItemsInShoppingCart(User user) {
        return purchaseItemDAO.findByUserAndPurchasedFilter(user, Boolean.FALSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Image> getUserImages(User user) {
        List<PurchaseItem> items = purchaseItemDAO.findByUserAndPurchasedFilter(user, Boolean.TRUE);
        List<Image> result = new ArrayList<>();
        for (PurchaseItem item : items) {
            result.add(item.getImage());
        }
        return result;
    }
}
