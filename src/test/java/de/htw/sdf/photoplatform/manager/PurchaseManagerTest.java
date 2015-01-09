package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.common.BaseImageTester;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for purchases business logic.
 *
 * @author Sergej Meister
 */
public class PurchaseManagerTest extends BaseImageTester {

    private static final String PHOTOGRAPH_EMAIL = "sergej@test.de";
    private static final String CUSTOMER_EMAIL = "peter@test.de";
    private User photographer;
    private User customer;

    @Before
    public void setUp() throws Exception {
        insertTestData();
        for (User user : users) {
            if (user.getUsername().equals(PHOTOGRAPH_EMAIL)) {
                this.photographer = user;
            } else if (user.getUsername().equals(CUSTOMER_EMAIL)) {
                this.customer = user;
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        purchaseItemDAO.deleteAll();
        imageDAO.deleteAll();
        clearTables();
    }

    @Test
    public void testAddImageToShoppingCart() {
        List<PurchaseItem> cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.isEmpty());
        List<Image> customerImages = purchaseManager.getUserImages(customer);
        Assert.assertTrue(customerImages.isEmpty());
        Image nature = createDefaultImage("Natur", "Natur", "natur.jpg");
        PurchaseItem natureItem = purchaseManager.addToShoppingCart(customer, nature);
        cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.size() == 1);
        customerImages = purchaseManager.getUserImages(customer);
        Assert.assertTrue(customerImages.isEmpty());
        Assert.assertTrue(natureItem.getImage().getId().equals(nature.getId()));
        Assert.assertTrue(natureItem.getUser().getId().equals(customer.getId()));
        Assert.assertFalse(natureItem.isPurchased());

        Image mountain = createDefaultImage("Berg", "Natur,Berg", "berg.jpg");
        purchaseManager.addToShoppingCart(customer, mountain);
        cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.size() == 2);
        customerImages = purchaseManager.getUserImages(customer);
        Assert.assertTrue(customerImages.isEmpty());

        //test with other user
        cartItems = purchaseManager.getItemsInShoppingCart(photographer);
        Assert.assertTrue(cartItems.isEmpty());
        customerImages = purchaseManager.getUserImages(photographer);
        Assert.assertTrue(customerImages.isEmpty());
    }

    @Test
    public void testRemoveImageFromShoppingCart() {
        //Init test data.
        Image nature = createDefaultImage("Natur", "Natur", "natur.jpg");
        purchaseManager.addToShoppingCart(customer, nature);
        Image mountain = createDefaultImage("Berg", "Natur,Berg", "berg.jpg");
        purchaseManager.addToShoppingCart(customer, mountain);
        List<PurchaseItem> cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.size() == 2);

        //Do test.
        purchaseManager.removeFromShoppingCart(customer, nature);
        cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.size() == 1);
        Assert.assertFalse(cartItems.get(0).isPurchased());
        Assert.assertTrue(cartItems.get(0).getUser().getId().equals(customer.getId()));
        Assert.assertTrue(cartItems.get(0).getImage().getId().equals(mountain.getId()));

        purchaseManager.removeFromShoppingCart(customer, mountain);
        cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.isEmpty());
    }

    @Test
    public void testPurchase() {
        //Init test data.
        Image nature = initDefaultImage("Natur", Boolean.TRUE, Boolean.TRUE, "natur.jpg");
        Double naturePrice = 5.8;
        nature.setPrice(naturePrice);
        imageDAO.create(nature);
        purchaseManager.addToShoppingCart(customer, nature);
        Image mountain = initDefaultImage("Berg", Boolean.TRUE, Boolean.TRUE, "berg.jpg");
        Double mountainPrice = 3.32;
        mountain.setPrice(mountainPrice);
        imageDAO.create(mountain);
        purchaseManager.addToShoppingCart(customer, mountain);
        List<PurchaseItem> cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.size() == 2);

        Double totalPrice = purchaseManager.calculatePrice(cartItems);
        Assert.assertTrue(totalPrice == naturePrice + mountainPrice);

        purchaseManager.purchase(cartItems);
        cartItems = purchaseManager.getItemsInShoppingCart(customer);
        Assert.assertTrue(cartItems.isEmpty());

        List<Image> customerImages = purchaseManager.getUserImages(customer);
        Assert.assertTrue(customerImages.size() == 2);
        List<Long> imageIds = new ArrayList<>();
        imageIds.add(nature.getId());
        imageIds.add(mountain.getId());
        Assert.assertTrue(imageIds.contains(customerImages.get(0).getId()));
        Assert.assertTrue(imageIds.contains(customerImages.get(1).getId()));

        //test with other user
        cartItems = purchaseManager.getItemsInShoppingCart(photographer);
        Assert.assertTrue(cartItems.isEmpty());
        customerImages = purchaseManager.getUserImages(photographer);
        Assert.assertTrue(customerImages.isEmpty());
    }
}
