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
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        Set<CollectionImage> collectionImages = collectionDAO.findCollectionImagesBy(Boolean.TRUE);
        initIndexTypeIfNotExist();
        for (CollectionImage collectionImage : collectionImages) {
            initIndex(collectionImage.getImage());
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
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        Page<Image> searchResult = elasticSearchTemplate.queryForPage(searchQuery, Image.class);
        return searchResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Image> searchByNameAndDescription(String searchData) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchData, "name", "description");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        Page<Image> searchResult = elasticSearchTemplate.queryForPage(searchQuery, Image.class);
        return searchResult;
    }
}
