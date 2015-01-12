/**
 *
 */
package de.htw.sdf.photoplatform.webservice;

import de.htw.sdf.photoplatform.common.BaseAPITester;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests only endpoints for purchase services.
 *
 * @author Sergej Meister
 */
public class PurchaseControllerTest extends BaseAPITester {

    private static final String ENDPOINT_PURCHASES = Endpoints.API_PREFIX + Endpoints.PURCHASES;
    private static final String ENDPOINT_USER_IMAGES = Endpoints.API_PREFIX + Endpoints.USERS_IMAGES;
    private static final String ENDPOINT_REMOVE_FROM_CART = Endpoints.API_PREFIX + Endpoints.PURCHASES_ID;
    private static final String PATHVARIABLE_PURCHASEITEM_ID = "{purchaseItemId}";

    private Image nature;
    private Image mountain;

    private User customer;

    @Before
    public void setUp() throws Exception {
        initAPITest();
        initTestImages();
    }

    @After
    public void tearDown() throws Exception {
        collectionImageDAO.deleteAll();
        collectionDAO.deleteAll();
        userImageDAO.deleteAll();
        purchaseItemDAO.deleteAll();
        imageDAO.deleteAll();
        cancel();
    }

    @Test
    public void basicEndpointTest() throws Exception {
        loginAsCustomer();

        mockMvc.perform(
                post(ENDPOINT_PURCHASES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("imageId", String.valueOf(nature.getId()))).andExpect(
                status().isOk());

        mockMvc.perform(
                get(ENDPOINT_PURCHASES).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());

        PurchaseItem natureItem = purchaseItemDAO.findByUserAndImage(customer, nature);
        Assert.assertNotNull(natureItem);
        String deleteUrl = ENDPOINT_REMOVE_FROM_CART.replace(PATHVARIABLE_PURCHASEITEM_ID, natureItem.getId().toString());
        mockMvc.perform(
                delete(deleteUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());

        mockMvc.perform(
                get(ENDPOINT_PURCHASES).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());

        mockMvc.perform(
                get(ENDPOINT_USER_IMAGES).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }

    private void initTestImages() {
        //Init test data.
        this.nature = createDefaultImage("Natur", "Natur", "natur.jpg");
        this.mountain = createDefaultImage("Berg", "Natur,Berg", "berg.jpg");
        User photographer = null;
        for (User user : users) {
            if (user.getUsername().equals(PHOTOGRAPH_EMAIL)) {
                photographer = user;
            } else if (user.getUsername().equals(CUSTOMER_EMAIL)) {
                this.customer = user;
            }
        }

        Collection natureCollection = createCollection(photographer, "NaturSammlung", Boolean.TRUE);
        try {
            createUserImage(photographer, photographer, nature);
            photographerManager.addImageToCollection(photographer.getId(), natureCollection.getId(), nature.getId());
            createUserImage(photographer, photographer, mountain);
            photographerManager.addImageToCollection(photographer.getId(), natureCollection.getId(), mountain.getId());
        } catch (ManagerException e) {
            Assert.fail();
        }
    }
}
