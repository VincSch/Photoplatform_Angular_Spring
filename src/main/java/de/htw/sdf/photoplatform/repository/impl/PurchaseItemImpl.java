/**
 *
 */
package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.PurchaseItem;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.repository.PurchaseItemDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

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
public class PurchaseItemImpl extends GenericDAOImpl<PurchaseItem> implements PurchaseItemDAO {

    /**
     * Collection DAO constructor.
     */
    public PurchaseItemImpl() {
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

    private StringBuilder initQuery() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(purchaseItem) FROM PurchaseItem purchaseItem ");
        queryBuilder.append("LEFT JOIN FETCH purchaseItem.user user ");
        queryBuilder.append("LEFT JOIN FETCH purchaseItem.image image ");

        return queryBuilder;
    }
}
