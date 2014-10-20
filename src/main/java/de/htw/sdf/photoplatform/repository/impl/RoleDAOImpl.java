package de.htw.sdf.photoplatform.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.Role;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for roles
 * 
 * @author Vincent Schwarzer
 * 
 */
@Repository
@Transactional
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO {

	public RoleDAOImpl() {
		super();
		setClazz(Role.class);
	}

	@Override
	public Role findByName(String name) {
		String queryString = "SELECT role FROM Role role "
				+ "WHERE role.name like ?1";

		Query query = createQuery(queryString);
		query.setParameter(1, name);
		return (Role) query.getSingleResult();
	}

}
