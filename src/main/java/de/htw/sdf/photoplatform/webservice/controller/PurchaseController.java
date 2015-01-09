/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.PurchaseManager;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
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

    /**
     * Add an image to shopping cart.
     */
    @RequestMapping(value = Endpoints.PURCHASES, method = RequestMethod.POST)
    @ResponseBody
    public void addToShoppingCart(@RequestParam Long imageId, BindingResult bindingResult) throws IOException, AbstractBaseException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("createCollection", bindingResult);
        }
        User user = getAuthenticatedUser();
        try {
            purchaseManager.addToShoppingCart(user, imageId);
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
    }

    /**
     * Add an image to shopping cart.
     */
    @RequestMapping(value = Endpoints.PURCHASES_ID, method = RequestMethod.DELETE)
    @ResponseBody
    public void removeFromShoppingCart(@PathVariable(value = "purchaseItemId") Long purchaseItemId) throws IOException, AbstractBaseException {
        User user = getAuthenticatedUser();
        List<PurchaseItem> restOfImagesInShoppingCart = new ArrayList<>();
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
    }
}
