package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.NutritionFact;
import de.htw.sdf.photoplatform.repository.NutritionFactDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for nutritionfacts
 * 
 * @author Vincent Schwarzer
 * 
 */
@Repository
@Transactional
public class NutritionFactDAOImpl extends GenericDAOImpl<NutritionFact>
		implements NutritionFactDAO {

	public NutritionFactDAOImpl() {
		super();
		setClazz(NutritionFact.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NutritionFact findByIngredientId(Long id) {
		String queryString = "SELECT nutritionfact FROM NutritionFact nutritionfact "
				+ "WHERE nutritionfact.ingredient.id = ?1";

		Query query = createQuery(queryString);
		query.setParameter(1, id);
		return (NutritionFact) query.getSingleResult();
	}

}
