/**
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.PurchaseManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.manager.common.PaypalService;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

/**
 * Business logic for purchase.
 *
 * @author Sergej Meister
 */
@Service
@Transactional
public class PurchaseManagerImpl extends DAOReferenceCollector implements PurchaseManager {

	@Resource
	private PaypalService paypalService;
    
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
    public PurchaseItem addToShoppingCart(User user, Long imageId) throws ManagerException {

        List<Image> userImages = getUserImages(user);
        // check if already purchased
        for (Image item : userImages) {
            if (item.getId().equals(imageId)) {
                throw new ManagerException(ManagerException.ALREADY_PURCHASED);
            }
        }
    	
        List<PurchaseItem> itemsInShoppingCart = getItemsInShoppingCart(user);
        // check if already in shopping cart
        for (PurchaseItem item : itemsInShoppingCart) {
            if (item.getImage().getId().equals(imageId)) {
                throw new ManagerException(ManagerException.BAD_REQUEST);
            }
        }

        CollectionImage collectionImage = collectionImageDAO.findCollectionImagesBy(imageId);
        if (collectionImage == null || !collectionImage.getCollection().isPublic()) {
            //if you don't hack the system, should never be true :)
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }
        return addToShoppingCart(user, collectionImage.getImage());
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
    public List<PurchaseItem> removeFromShoppingCart(final User user, final Long purchaseItemId) throws ManagerException {
        Boolean deletedSuccess = Boolean.FALSE;
        List<PurchaseItem> itemsInShoppingCart = getItemsInShoppingCart(user);
        List<PurchaseItem> result = new ArrayList<>();
        for (PurchaseItem item : itemsInShoppingCart) {
            if (item.getId().equals(purchaseItemId)) {
                removeFromShoppingCart(item);
                deletedSuccess = Boolean.TRUE;
            } else {
                result.add(item);
            }
        }

        if (!deletedSuccess) {
            //That mean, the purchaseItem is not found in the list
            //if you don't hack the system, should never be true :)
            throw new ManagerException(AbstractBaseException.NOT_FOUND);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String startPurchasePerPaypal(List<PurchaseItem> items) throws ManagerException
    {
    	List<Image> ImageList = new ArrayList<Image>();
    	for(PurchaseItem item : items)
    	{
    		ImageList.add(item.getImage());
    	}
    	PaypalService.CreatePaymentResult Result;

        UriComponents ApprovedURIComponents = ServletUriComponentsBuilder.fromCurrentContextPath().path("/purchase/approved").build();
        UriComponents CancledURIComponents = ServletUriComponentsBuilder.fromCurrentContextPath().path("/cart").build();

        String ApprovedURL = ApprovedURIComponents.toUriString();
        String CanceledURL = CancledURIComponents.toUriString();
        
        //Create Payment
    	try {
    		Result = paypalService.CreatePayment(ImageList, ApprovedURL, CanceledURL);
    	} catch(AbstractBaseException ex) {
    		throw new ManagerException(ex.getCode());
    	}
    	
    	//Fill in PaymentID
    	for(PurchaseItem item : items)
    	{
    		item.setPaymentId(Result.GetPaymentID());
            purchaseItemDAO.update(item);
    	}
    	
    	//Return redirect url
    	return Result.GetRedirectURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completePurchasePerPaypal(String PaymentId, String PayerID) throws ManagerException
    {
    	List<PurchaseItem> ItemsFromCart = purchaseItemDAO.findByPaymentIdAndPurchasedFilter(PaymentId, Boolean.FALSE);
    	
    	//Check if Cart has not changed
    	try {
    		Locale l = null;
    		String PaypalTotal = paypalService.GetPaymentTotal(PaymentId);
    		String CartTotal = String.format(l, "%.2f" , calculatePrice(ItemsFromCart));
    		
    		if(PaypalTotal.compareToIgnoreCase(CartTotal) != 0) {
    			throw new ManagerException(AbstractBaseException.CART_HAS_CHANGED);
    		}
    	} catch(AbstractBaseException ex) {
    		throw new ManagerException(ex.getCode());
    	}
    	
    	//execute/complete payment
    	try {
        	paypalService.ExecutePayment(PaymentId, PayerID);
    	} catch(AbstractBaseException ex) {
    		throw new ManagerException(ex.getCode());
    	}
    	
    	//Set to purchased where paymentid
    	for(PurchaseItem item : ItemsFromCart)
    	{
    		item.setPurchased(Boolean.TRUE);
            purchaseItemDAO.update(item);
    	}
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
    public BigDecimal calculatePrice(List<PurchaseItem> images) {
        BigDecimal result =  new BigDecimal(0.0);
        for (PurchaseItem item : images) {
            result = result.add(item.getImage().getPrice());
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
