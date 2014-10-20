package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity mapping class for a nutrition fact of an ingredient and a specific amount representing the corresponding
 * database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_NUTRITIONFACT")
public class NutritionFact extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -768981160936776172L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INGREDIENT_ID", referencedColumnName = "ID")
	private Ingredient ingredient;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIT_ID", referencedColumnName = "ID")
	private Unit unit;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	@Column(name = "CALORIES")
	private double calories;
	
	@Column(name = "CARBOHYDRATE")
	private double carbohydrate;
	
	@Column(name = "PROTEIN")
	private double protein;
	
	@Column(name = "FAT")
	private double fat;

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}
}
