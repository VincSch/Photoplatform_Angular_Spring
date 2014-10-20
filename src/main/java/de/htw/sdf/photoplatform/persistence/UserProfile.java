package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a unit representing the corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_USER_PROFILE")
public class UserProfile extends BaseAuditEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2595993675256650125L;

	public UserProfile() {
		super();
	}
	
}
