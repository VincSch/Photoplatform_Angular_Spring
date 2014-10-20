package de.htw.sdf.photoplatform.persistence;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a recipe representing the corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_RECIPE")
public class Recipe extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1086441426865570028L;

	public Recipe() {
		super();
	}

	public Recipe(String name, RecipeDifficulty difficulty, int serves,
			String preperationTime, String cookingTime, String preperation,
			String notes, Rating rating, List<Ingredient> ingredients) {
		setName(name);
		setDifficulty(difficulty);
		setServes(serves);
		setPreperation(preperation);
		setPreperationTime(preperationTime);
		setCookingTime(cookingTime);
		setNotes(notes);
		setRating(rating);
	}

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DIFFICULTY_ID", referencedColumnName = "ID")
	private RecipeDifficulty difficulty;

	@Column(name = "SERVES")
	private int serves;

	@Column(name = "PREPERATION_TIME")
	private String preperationTime;

	@Column(name = "COOKING_TIME")
	private String cookingTime;

	@Column(name = "PREPERATION")
	private String preperation;

	@Column(name = "NOTES")
	private String notes;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
	private Category category;

	public enum Rating {
		good, very_good, excellent
	}

	@Column(name = "RATING")
	@Enumerated(EnumType.STRING)
	private Rating rating;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
	private List<UsedIngredient> usedIngredients;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getServes() {
		return serves;
	}

	public void setServes(int serves) {
		this.serves = serves;
	}

	public String getPreperationTime() {
		return preperationTime;
	}

	public void setPreperationTime(String preperationTime) {
		this.preperationTime = preperationTime;
	}

	public String getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getPreperation() {
		return preperation;
	}

	public void setPreperation(String preperation) {
		this.preperation = preperation;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public List<UsedIngredient> getUsedIngredients() {
		return usedIngredients;
	}

	public void setUsedIngredients(
			List<UsedIngredient> recipeUsesIngredients) {
		this.usedIngredients = recipeUsesIngredients;
	}

	public RecipeDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(RecipeDifficulty difficulty) {
		this.difficulty = difficulty;
	}
	
}
