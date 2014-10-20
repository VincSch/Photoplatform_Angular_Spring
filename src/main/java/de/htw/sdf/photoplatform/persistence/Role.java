package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a role representing the corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_ROLE")
public class Role extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -219754255462744875L;

	public Role() {
		super();
	}

	@Column(name = "NAME")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
