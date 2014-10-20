package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a unit representing the corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_UNIT")
public class Unit extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4144282015263601060L;

	public Unit() {
		super();
	}

	public Unit(GermanUnitName unitName) {
		setGermanUnitName(unitName);
	}

	public enum GermanUnitName {
		EL, TL, mg, g, Kg, t, l, ml
	}

	@Column(name = "NAME")
	@Enumerated(EnumType.STRING)
	private GermanUnitName germanUnitName;

	public GermanUnitName getGermanUnitName() {
		return germanUnitName;
	}

	public void setGermanUnitName(GermanUnitName germanUnitName) {
		this.germanUnitName = germanUnitName;
	}
}
