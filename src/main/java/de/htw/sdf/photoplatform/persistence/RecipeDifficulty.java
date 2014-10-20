package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a recipe representing the corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_RECIPE_DIFFICULTY")
public class RecipeDifficulty extends BaseAuditEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 174609213319757623L;

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
