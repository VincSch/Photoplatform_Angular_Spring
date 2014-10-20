package de.htw.sdf.photoplatform.repository.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.UserRole;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for user roles
 * 
 * @author Vincent Schwarzer
 * 
 */
@Repository
@Transactional
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole> implements
		UserRoleDAO {

	public UserRoleDAOImpl() {
		super();
		setClazz(UserRole.class);
	}

	@Override
	public List<UserRole> findByUserId(Long id) {
		String queryString = "SELECT userRole FROM UserRole userRole "
				+ "LEFT JOIN FETCH userRole.user user "
				+ "LEFT JOIN FETCH userRole.role role " + "WHERE user.id = ?1";

		Query query = createQuery(queryString);
		query.setParameter(1, id);
		return query.getResultList();
	}

	@Override
	public List<UserRole> findByRecipeRoleId(Long id) {
		String queryString = "SELECT userRole FROM UserRole userRole "
				+ "LEFT JOIN FETCH userRole.user user "
				+ "LEFT JOIN FETCH userRole.role role " + "WHERE role.id = ?1";

		Query query = createQuery(queryString);
		query.setParameter(1, id);
		return query.getResultList();
	}

	@Override
	public List<UserRole> findByUserAndRoleId(Long userId, Long roleId) {
		String queryString = "SELECT userRole FROM UserRole userRole "
				+ "LEFT JOIN FETCH userRole.user user "
				+ "LEFT JOIN FETCH userRole.role role "
				+ "WHERE user.id = ?1 AND role.id = ?2";

		Query query = createQuery(queryString);
		query.setParameter(1, userId);
		query.setParameter(2, roleId);
		return query.getResultList();
	}

}
