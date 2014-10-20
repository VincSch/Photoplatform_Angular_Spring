package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a user and his role/s recipes corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_USER_ROLE")
public class UserRole extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1517204631630105586L;

	public UserRole() {

	}

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
