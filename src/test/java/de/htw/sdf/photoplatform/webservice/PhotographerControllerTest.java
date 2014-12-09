/**
 *
 */
package de.htw.sdf.photoplatform.webservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
    private final String ENDPOINT_DELETE_IMAGE = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_DELETE_IMAGE;
    private final String ENDPOINT_CREATE_COLLECTION = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_CREATE;
    private final String ENDPOINT_DELETE_COLLECTION = Endpoints.API_PREFIX + Endpoints.COLLECTIONS;
    private final String ENDPOINT_GET_COLLECTIONS =
            Endpoints.API_PREFIX + Endpoints.COLLECTIONS_PHOTOGRAPHERS_START_COUNT;
    private final String ENDPOINT_COLLECTIONS_SHOWCASE = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_SHOWCASE;
    private final String ENDPOINT_COLLECTIONS_UPDATE = Endpoints.API_PREFIX + Endpoints.COLLECTIONS_PHOTOGRAPHERS;


    @Before
    public void setUp() throws Exception {
        initAPITest();
    }

    @After
    public void tearDown() throws Exception {
        cancel();
    }

    @Test
    public void testGetPhotographersImages() throws Exception {
        loginAsPhotograph();

        String request = Endpoints.API_PREFIX + Endpoints.PHOTOGRAPHERS_IMAGES;
        mockMvc.perform(
                get(request).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());
    }

    @Test
    public void testCreateCollection() throws Exception {
        loginAsPhotograph();
        User photograph = userDAO.findByEmail("sergej@test.de");

        CollectionData data = new CollectionData();
        data.setName("Winter");
        data.setDescription("Meine Winder fotos");
        data.setPublic(Boolean.FALSE);

        mockMvc.perform(
                post(ENDPOINT_CREATE_COLLECTION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(data))
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());

        List<Collection> collectionList = photographerManager.getCollectionByUser(photograph.getId(), 0, 0);
        Collection createdCollection =
                findCollectionInList(collectionList, data.getName(), data.getDescription(), data.getPublic());
        Assert.assertNotNull(createdCollection);
        Assert.assertTrue(createdCollection.getUser().getId().equals(photograph.getId()));

        String requestUrl = ENDPOINT_GET_COLLECTIONS.replace("{start}", "0").replace("{count}", "0");
        mockMvc.perform(
                get(requestUrl).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());

        //add to showcase
        Map<String, String> map = new HashMap<>();
        map.put("id", createdCollection.getId().toString());
        map.put("isPublic", Boolean.TRUE.toString());

        mockMvc.perform(
                post(ENDPOINT_COLLECTIONS_SHOWCASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(map))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        collectionList.clear();
        collectionList = photographerManager.getCollectionByUser(photograph.getId(), 0, 0);
        Collection updateCollection =
                findCollectionInList(collectionList, data.getName(), data.getDescription(), Boolean.TRUE);
        Assert.assertNotNull(updateCollection);

        //update collection
        CollectionData dataToUpdate = new CollectionData();
        dataToUpdate.setName("WinterUpdate");
        dataToUpdate.setDescription("Meine neue Winter fotos");
        dataToUpdate.setPublic(Boolean.FALSE);
        mockMvc.perform(
                patch(ENDPOINT_COLLECTIONS_UPDATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dataToUpdate))
                        .characterEncoding("UTF-8")).andExpect(
                status().isOk());

        collectionList.clear();
        collectionList = photographerManager.getCollectionByUser(photograph.getId(), 0, 0);
        Collection updatedCollectionData = findCollectionInList(collectionList, dataToUpdate.getName(),
                dataToUpdate.getDescription(), Boolean.FALSE);
        Assert.assertNotNull(updatedCollectionData);

        //Remove created test data.
        collectionDAO.deleteAll();
    }

    private Collection findCollectionInList(List<Collection> collectionList, String name, String description,
                                            Boolean publicValue) {
        for (Collection collection : collectionList) {
            if (collection.getName().equals(name) &&
                    collection.getDescription().equals(description) &&
                    collection.isPublic() == publicValue) {
                return collection;
            }
        }
        return null;
    }

    /**
     * This method tests the basic collection services:
     * createCollection, addImage  to collection,
     * deleteImage from collection, delete collection.
     *
     * @throws Exception exception.
     */
    @Test
    public void testBasicCollectionsServices() throws Exception {
        loginAsPhotograph();
        User photograph = userDAO.findByEmail("sergej@test.de");

        List<Collection> photographCollections = photographerManager.getCollectionByUser(photograph.getId(), 0, 0);
        int initCollectionSize = photographCollections.size();

        CollectionData requestCollectionData = new CollectionData();
        requestCollectionData.setId(1000L);
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
        photographCollections = photographerManager.getCollectionByUser(photograph.getId(), 0, 0);
        Assert.assertTrue("One collection should be added!", initCollectionSize + 1 == photographCollections.size());

        //Add image to Collection.
        Long validCollectionId = photographCollections.get(0).getId();
        requestCollectionData.setId(validCollectionId);
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
        Image firstImage = initDefaultImage(firstImageName, Boolean.FALSE, Boolean.TRUE, firstImagePath);
        imagesToCreate.add(firstImage);
        List<UserImage> createdUserImages = photographerManager.createPhotographImage(photograph, imagesToCreate);
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

        //Delete Image From Collection
        String deleteUrl =ENDPOINT_DELETE_IMAGE.replace("{collectionId}",String.valueOf(requestCollectionData.getId()))
                .replace("{imageId}",validImageId.toString()) ;

        mockMvc.perform(
                delete(deleteUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());

        //Delete collection.
        String collectionIdToDelete = String.valueOf(requestCollectionData.getId());
        Map<String, String> map = new HashMap<>();
        map.put("collectionId", collectionIdToDelete);

        final String deleteEndpoint = ENDPOINT_DELETE_COLLECTION.replace("{collectionId}", collectionIdToDelete);
        mockMvc.perform(
                delete(deleteEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //RollBack rest test data!
        userImageDAO.deleteAll();
        imageDAO.deleteAll();
    }
}
