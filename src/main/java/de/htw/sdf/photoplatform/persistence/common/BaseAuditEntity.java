package de.htw.sdf.photoplatform.persistence.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import de.htw.sdf.photoplatform.persistence.User;

/**
 * Superclass for all entities defining audit fields 
 * @author Vincent Schwarzer
 *
 */
@MappedSuperclass
public abstract class BaseAuditEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3699932723300487164L;

	@Column(name = "CREATED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Size(max = 20)
	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "UPDATED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Size(max = 20)
	@Column(name = "UPDATED_BY", length = 20)
	private String updatedBy;
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Sets createdAt before insert
	 */
	@PrePersist
	public void setCreationDate() {
		this.createdAt = new Date();
	}

	/**
	 * Sets updatedAt before update
	 */
	@PreUpdate
	public void setChangeDate() {
		this.updatedAt = new Date();
	}
}
