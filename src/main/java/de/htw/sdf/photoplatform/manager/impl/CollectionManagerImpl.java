/**
 *
 */
package de.htw.sdf.photoplatform.manager.impl;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.exception.common.ManagerException;
import de.htw.sdf.photoplatform.manager.CollectionManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.model.CollectionImage;

/**
 * business methods for collection.
 *
 * @author <a href="mailto:sergej_meister@gmx.net">Sergej Meister</a>
 */
@Service
@Transactional
public class CollectionManagerImpl extends DAOReferenceCollector implements
        CollectionManager {


    /**
     * {@inheritDoc}
     */
    @Override
    public Set<CollectionImage> getCollectionImages( Long collectionId, int start, int count) throws ManagerException {
        if (collectionId == null) {
            throw new ManagerException(AbstractBaseException.COLLECTION_ID_NOT_VALID);
        }

        if(start > 0 && count > 0){
            return collectionDAO.findCollectionImagesBy(collectionId, start, count);
        }

        return collectionDAO.findCollectionImagesBy(collectionId);
    }
}
