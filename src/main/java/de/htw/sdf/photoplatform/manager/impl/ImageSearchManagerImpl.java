/**
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.manager.ImageSearchManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Image;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * business methods for search images.
 *
 * @author Sergej Meister
 */
@Service
public class ImageSearchManagerImpl extends DAOReferenceCollector implements ImageSearchManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initIndexes() {
        List<CollectionImage> collectionImages = collectionImageDAO.findCollectionImagesBy(Boolean.TRUE);
        initIndexTypeIfNotExist();
        for (CollectionImage collectionImage : collectionImages) {
            if (collectionImage.getCollection().isPublic()) {
                //index only image's, which are added to collection,
                // and collection is added to showcase!
                initIndex(collectionImage.getImage());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createIndex(Image image) {
        initIndexTypeIfNotExist();
        initIndex(image);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateIndex(Image image) {
//        IndexRequest indexRequest = new IndexRequest();
//        indexRequest.source("NAME", image.getName(),"DESCRIPTION",image.getDescription());
//        UpdateQuery updateQuery = new UpdateQueryBuilder().withId(image.getId().toString())
//                .withDoUpsert(true).withClass(Image.class)
//                .withIndexRequest(indexRequest).build();
//        //when
//        elasticSearchTemplate.update(updateQuery);

        //Should be only one image!
        Page<Image> foundImage = searchById(image.getId().toString());
        for (Image imageToUpdate : foundImage) {
            deleteIndex(image);
            createIndex(image);
        }
    }

    private void initIndexTypeIfNotExist() {
        if (!elasticSearchTemplate.indexExists(Image.class)) {
            elasticSearchTemplate.createIndex(Image.class);
            elasticSearchTemplate.putMapping(Image.class);
            elasticSearchTemplate.refresh(Image.class, true);
        }
    }

    private void initIndex(Image image) {
        IndexQuery indexQuery = new IndexQueryBuilder().withId(image.getId().toString()).withObject(image).build();
        elasticSearchTemplate.index(indexQuery);
        elasticSearchTemplate.refresh(Image.class, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteIndex(Image image) {
        elasticSearchTemplate.delete(Image.class, image.getId().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteIndexes() {
        elasticSearchTemplate.deleteIndex(Image.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Image> searchById(String searchId) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("id", searchId);
        return executeNativeSearchQuery(queryBuilder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Image> getAll() {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        return executeNativeSearchQuery(queryBuilder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Image> searchByNameAndDescription(String searchData) {
        QueryBuilder queryBuilder = QueryBuilders.queryString("*" + searchData + "*").
                field(Image.COLUMN_NAME.toLowerCase()).field(Image.COLUMN_DESCRIPTION.toLowerCase());
        return executeNativeSearchQuery(queryBuilder);
    }

    private Page<Image> executeNativeSearchQuery(QueryBuilder queryBuilder) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        //dirty bug fix to show all images, without new request on server.
        searchQuery.setPageable(new PageRequest(0, 1000));
        return elasticSearchTemplate.queryForPage(searchQuery, Image.class);
    }
}
