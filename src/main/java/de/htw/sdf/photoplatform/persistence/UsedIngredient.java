package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity mapping class for a recipe-ingredient representing the corresponding
 * database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_USED_INGREDIENTS")
public class UsedIngredient extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8433747680888337645L;

	public UsedIngredient() {
		super();
	}

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID")
	private Recipe recipe;

	@ManyToOne
	@JoinColumn(name = "INGREDIENT_ID", referencedColumnName = "ID")
	private Ingredient ingredient;

	@Column(name = "AMOUNT")
	private double amount;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIT_ID", referencedColumnName = "ID")
	private Unit unit;

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
