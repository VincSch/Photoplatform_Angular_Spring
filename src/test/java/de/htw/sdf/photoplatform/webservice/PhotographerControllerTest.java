/**
 *
 */
package de.htw.sdf.photoplatform.webservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import de.htw.sdf.photoplatform.common.BaseAPITester;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.webservice.dto.CollectionData;

/**
 * Tests for users services.
 */
public class PhotographerControllerTest extends BaseAPITester {

    private final String ENDPOINT_ADD_IMAGE = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_ADD_IMAGE;
    private final String ENDPOINT_CREATE_COLLECTION = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_CREATE;
    private final String ENDPOINT_GET_COLLECTIONS = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_PHOTOGRAPHERS_START_COUNT;

    @Before
    public void setUp() throws Exception {
        initAPITest();
    }

    @After
    public void tearDown() throws Exception {
        cancel();
    }

    @Test
    @Ignore
    public void testGetPhotographersImages() throws Exception {
        loginAsPhotograph();

        String request = Endpoints.API_PREFIX + Endpoints.IMAGES_PHOTOGRAPHERS;
        mockMvc.perform(
                get(request).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }

    @Test
    @Ignore
    public void testGetPhotographersCollections() throws Exception {
        loginAsPhotograph();

        String request = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_PHOTOGRAPHERS_START_COUNT;
        mockMvc.perform(
                get(request).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }

    @Test
    public void testCreateCollection() throws Exception {
        loginAsPhotograph();

        CollectionData data = new CollectionData();
        data.setName("Winter");
        data.setDescription("Meine Winder fotos");

        mockMvc.perform(
                post(ENDPOINT_CREATE_COLLECTION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(data))
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());

        String start = "0";
        String count = "0" ;
        String requestUrl = ENDPOINT_GET_COLLECTIONS.replace("{start}",start).replace("{count}",count);
        mockMvc.perform(
                get(requestUrl).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }

    @Test
    @Ignore(value="not finished!")
    public void testAddImageToCollections() throws Exception {
        loginAsPhotograph();
        User photograph = userDAO.findByEmail("sergej@test.de");

        List<Collection> photographCollections = photographerManager.getCollectionByUser(photograph.getId(),0,0);
        int initCollectionSize =  photographCollections.size();

        CollectionData requestCollectionData = new CollectionData();
        requestCollectionData.setCollectionId(1000L);
        List<Long> imageIds = new ArrayList<>();
        imageIds.add(2000L);
        requestCollectionData.setImageIds(imageIds);
        //Add image to collection,
        // Sergej has no collection with id = 1000, should be a bad request.
        mockMvc.perform(
                post(ENDPOINT_ADD_IMAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestCollectionData))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isBadRequest());

        //Create Collection.
        CollectionData data = new CollectionData();
        data.setName("Winter");
        data.setDescription("Meine Winder fotos");

        mockMvc.perform(
                post(ENDPOINT_CREATE_COLLECTION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(data))
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
        photographCollections.clear();
        photographCollections = photographerManager.getCollectionByUser(photograph.getId(),0,0);
        Assert.assertTrue("One collection should be added!",initCollectionSize + 1 == photographCollections.size());

        //Add image to Collection.
        Long validCollectionId = photographCollections.get(0).getId();
        requestCollectionData.setCollectionId(validCollectionId);
        //Now Sergej, has one collection, but no images! It should be a bad request.
        mockMvc.perform(
                post(ENDPOINT_ADD_IMAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestCollectionData))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isBadRequest());

        //Create Images.
        List<Image> imagesToCreate = new ArrayList<>();
        String firstImageName = "myFirstImage";
        String firstImagePath = "store/" + firstImageName;
        Image firstImage = InitDefaultImage(firstImageName,firstImagePath);
        imagesToCreate.add(firstImage);
        List<UserImage> createdUserImages = photographerManager.createPhotographImage(photograph,imagesToCreate);
        Assert.assertTrue(createdUserImages.size() == 1);
        Assert.assertTrue(createdUserImages.get(0).getImage().getName().equals(firstImageName));

        //Add image to Collection.
        //Now sergej has one collection and one image, should be ok!
        Long validImageId = createdUserImages.get(0).getImage().getId();
        requestCollectionData.getImageIds().clear();
        requestCollectionData.getImageIds().add(validImageId);
        mockMvc.perform(
                post(ENDPOINT_ADD_IMAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestCollectionData))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());
        int test = 0 ;
    }
}
