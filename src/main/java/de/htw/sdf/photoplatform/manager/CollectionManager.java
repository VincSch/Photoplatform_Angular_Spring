/**
 *
 */
package de.htw.sdf.photoplatform.manager;

import java.util.Set;

import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;
import de.htw.sdf.photoplatform.persistence.model.Collection;

/**
 * Interface defining business methods for collections.
 *
 * @author <a href="mailto:sergej_meister@gmx.net">Sergej Meister</a>
 */
public interface CollectionManager {

    /**
     * Returns a single collection.
     *
     * @param collectionId collection id.
     * @return collection.
     * @throws ManagerException exception.
     */
	Collection getCollection(Long collectionId) throws ManagerException;
	
    /**
     * Returns list of collection images.
     *
     * @param collectionId collection id.
     * @param start start.
     * @param count max.
     * @return list of collection images.
     * @throws ManagerException exception.
     */
    Set<CollectionImage> getCollectionImages(Long collectionId,int start, int count) throws ManagerException ;
}
