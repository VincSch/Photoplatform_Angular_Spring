/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.ImageManager;
import de.htw.sdf.photoplatform.manager.PurchaseManager;
import de.htw.sdf.photoplatform.manager.common.PaypalService;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import de.htw.sdf.photoplatform.webservice.dto.ImageData;
import de.htw.sdf.photoplatform.webservice.dto.PurchaseData;
import de.htw.sdf.photoplatform.webservice.util.ResourceUtility;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Purchase web service controller.
 *
 * @author Sergej Meister
 */
@RestController
public class PurchaseController extends BaseAPIController {
	
    @Resource
    private PurchaseManager purchaseManager;  
    
    @Resource
	private ImageManager imageManager;
	

    /**
     * Add an image to shopping cart.
     */
    @RequestMapping(value = Endpoints.PURCHASES, method = RequestMethod.POST)
    @ResponseBody
    public PurchaseData addToShoppingCart(@RequestParam(value = "imageId") Long imageId) throws IOException, AbstractBaseException {
        User user = getAuthenticatedUser();
        try {
            purchaseManager.addToShoppingCart(user, imageId);
        } catch (ManagerException ex) {
            String exceptionMsg;
            switch (ex.getCode()) {
                case AbstractBaseException.NOT_FOUND:
                    exceptionMsg = messages.getMessage("SystemHack");
                    break;
                case AbstractBaseException.BAD_REQUEST:
                    exceptionMsg = messages.getMessage("Purchase.InShoppingCart");
                    break;
                case AbstractBaseException.ALREADY_PURCHASED:
                    exceptionMsg = messages.getMessage("Purchase.AlreadyPurchased");
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }

        return getShoppingCartData();
    }

    /**
     * Add an image to shopping cart.
     */
    @RequestMapping(value = Endpoints.PURCHASES_ID, method = RequestMethod.DELETE)
    @ResponseBody
    public PurchaseData removeFromShoppingCart(@PathVariable(value = "purchaseItemId") Long purchaseItemId) throws IOException, AbstractBaseException {
        User user = getAuthenticatedUser();
        List<PurchaseItem> restOfImagesInShoppingCart;
        try {
            restOfImagesInShoppingCart = purchaseManager.removeFromShoppingCart(user, purchaseItemId);
        } catch (ManagerException ex) {
            String exceptionMsg;
            switch (ex.getCode()) {
                case AbstractBaseException.NOT_FOUND:
                    exceptionMsg = messages.getMessage("SystemHack");
                    break;
                default:
                    throw new RuntimeException("Unhandled error");
            }

            throw new BadRequestException(exceptionMsg);
        }

        BigDecimal totalPriceAfterDelete = purchaseManager.calculatePrice(restOfImagesInShoppingCart);
        return ResourceUtility.convertToPurchaseData(restOfImagesInShoppingCart, totalPriceAfterDelete);
    }

    /**
     * Returns PurchaseData, that represent a shopping cart.
     * <p/>
     * PurchaseData include images, total price of all images
     * and count of all images in shopping cart.
     *
     * @return PurchaseData.
     */
    @RequestMapping(value = Endpoints.PURCHASES, method = RequestMethod.GET)
    @ResponseBody
    public PurchaseData getShoppingCartData() {

        User user = getAuthenticatedUser();
        List<PurchaseItem> itemInShoppingCart = purchaseManager.getItemsInShoppingCart(user);
        BigDecimal totalPrice = purchaseManager.calculatePrice(itemInShoppingCart);
        return ResourceUtility.convertToPurchaseData(itemInShoppingCart, totalPrice);
    }
    
    /**
     * Returns PurchaseData, that represent a shopping cart.
     * <p/>
     * PurchaseData include images, total price of all images
     * and count of all images in shopping cart.
     *
     * @return PurchaseData.
     */
    @RequestMapping(value = Endpoints.CART_IMAGE_NUMBER, method = RequestMethod.GET)
    @ResponseBody
    public int getItemsInShoppingCart() {
        User user = getAuthenticatedUser();
        List<PurchaseItem> itemInShoppingCart = purchaseManager.getItemsInShoppingCart(user);
        return itemInShoppingCart.size();
    }
    /**
     * Returns all images, which user has purchased.
     *
     * @return list of images belong to the user.
     */
    @RequestMapping(value = Endpoints.USERS_IMAGES, method = RequestMethod.GET)
    @ResponseBody
    public List<ImageData> getUserImages() {
        User user = getAuthenticatedUser();
        List<Image> userImages = purchaseManager.getUserImages(user);
        return ResourceUtility.convertListToImageData(userImages);
    }

    @RequestMapping(value = Endpoints.PURCHASE_PAYPAL, method = RequestMethod.GET)
    @ResponseBody
    public String StartPaypalPurchase(HttpServletRequest request) throws AbstractBaseException {
    	
    	if(!request.isSecure())
    	{
    		throw new BadRequestException(messages.getMessage("NotSecure"));
    	}

        User user = getAuthenticatedUser();
        List<PurchaseItem> itemsInShoppingCart = purchaseManager.getItemsInShoppingCart(user);
        
        try {
        	return purchaseManager.startPurchasePerPaypal(itemsInShoppingCart);
        } catch (ManagerException ex)
        {     
            throw new BadRequestException(messages.getMessage("Purchase.error"));
        }
    	
    }
    
    @RequestMapping(value = Endpoints.PURCHASE_PAYPAL_EXECUTE, method = RequestMethod.POST)
    @ResponseBody
    public void PaypalPurchaseApproved(
            @RequestParam(required = true, value  = "paymentId") String paymentId,
            @RequestParam(required = true, value = "payerId") String payerId,
            HttpServletRequest request) throws AbstractBaseException {

    	if(!request.isSecure())
    	{
    		throw new BadRequestException(messages.getMessage("NotSecure"));
    	}
    	
    	try{
    		purchaseManager.completePurchasePerPaypal(paymentId, payerId);
    	} catch (AbstractBaseException ex) {
            String exceptionMsg;
    		switch(ex.getCode()) {
    		case AbstractBaseException.CART_HAS_CHANGED: 
                exceptionMsg = messages.getMessage("Purchase.chartHasChanged");
                break;
            default:
            	exceptionMsg = messages.getMessage("Purchase.error");
                break;
    		}
            throw new BadRequestException(exceptionMsg);
    	}
    } 

}
