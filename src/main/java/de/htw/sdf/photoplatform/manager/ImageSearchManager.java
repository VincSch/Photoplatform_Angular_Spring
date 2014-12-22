package de.htw.sdf.photoplatform.manager;

import de.htw.sdf.photoplatform.persistence.model.Image;
import org.springframework.data.domain.Page;

/**
 * Search engine for image data.
 * <p/>
 * Use with spring data elasticsearch.
 *
 * @author Sergej Meister
 */
public interface ImageSearchManager {


    /**
     * Init index for all image's in db.
     * <p/>
     * Indexes will be used for full-text search.
     */
    void initIndexes();


    /**
     * Create search index.
     *
     * @param image image.
     */
    void createIndex(Image image);

    /**
     * Delete search index.
     *
     * @param image image.
     */
    void deleteIndex(Image image);


    /**
     * Delete all image search index.
     */
    void deleteIndexes();

    /**
     * Search image by id.
     *
     * @param searchId image id to search.
     * @return affected image's.
     */
    Page<Image> searchById(String searchId);

    /**
     * Search image by name and/or description.
     * <p/>
     * Use wild cards and returns all images where name or description like <param>searchData</param>
     *
     * @param searchData search data, name and/or description.
     * @return affected image's.
     */
    Page<Image> searchByNameAndDescription(String searchData);
}
