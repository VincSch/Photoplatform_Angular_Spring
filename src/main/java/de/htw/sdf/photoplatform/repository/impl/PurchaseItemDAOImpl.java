/**
 *
 */
package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.Image;
import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.PurchaseItemDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository methods for PurchaseItemDAO.
 *
 * @author Sergej Meister
 */
@Repository
@Transactional
public class PurchaseItemDAOImpl extends GenericDAOImpl<PurchaseItem> implements PurchaseItemDAO {

    /**
     * Collection DAO constructor.
     */
    public PurchaseItemDAOImpl() {
        super();
        setClazz(PurchaseItem.class);
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PurchaseItem> findByUserAndPurchasedFilter(User user, Boolean purchased) {
        StringBuilder queryBuilder = initQuery();
        queryBuilder.append("WHERE user.id = :userId AND ");
        queryBuilder.append("purchaseItem.purchased = :purchased");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("userId", user.getId());
        query.setParameter("purchased", purchased);
        return query.getResultList();
    }

    @Override
    public PurchaseItem findByUserAndImage(User user, Image image) {
        StringBuilder queryBuilder = initQuery();
        queryBuilder.append("WHERE user.id = :userId AND ");
        queryBuilder.append("image.id = :imageId");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter("userId", user.getId());
        query.setParameter("imageId", image.getId());
        try {
            return (PurchaseItem) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private StringBuilder initQuery() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(purchaseItem) FROM PurchaseItem purchaseItem ");
        queryBuilder.append("LEFT JOIN FETCH purchaseItem.user user ");
        queryBuilder.append("LEFT JOIN FETCH purchaseItem.image image ");

        return queryBuilder;
    }
}
