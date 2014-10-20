package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a ingredient representing the corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_INGREDIENT")
public class Ingredient extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6769576720889667073L;

	public Ingredient() {
		super();
	}

	public Ingredient(String name, Unit uni) {
		setName(name);
	}

	@Column(name = "NAME", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
