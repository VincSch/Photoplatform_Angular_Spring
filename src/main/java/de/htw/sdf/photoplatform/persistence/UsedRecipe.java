package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a recipe books recipes corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_RECIPEBOOK_HAS_RECIPE")
public class UsedRecipe extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2140082125469640164L;

	public UsedRecipe() {
		super();
	}

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "RECIPEBOOK_ID", referencedColumnName = "ID")
	private RecipeBook recipeBook;

	@ManyToOne
	@JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID")
	private Recipe recipe;

	public RecipeBook getRecipeBook() {
		return recipeBook;
	}

	public void setRecipeBook(RecipeBook recipeBook) {
		this.recipeBook = recipeBook;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
