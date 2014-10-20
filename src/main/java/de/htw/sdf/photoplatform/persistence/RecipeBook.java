package de.htw.sdf.photoplatform.persistence;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a recipe books corresponding database table
 * 
 * @author Vincent Schwarzer
 * 
 */
@Entity
@Table(name = "RB_RECIPEBOOK")
public class RecipeBook extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 316132395359811636L;

	public RecipeBook() {
		super();
	}

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	public enum Type {
		priv, pub
	}

	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private Type type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeBook")
	private List<UsedRecipe> usedRecipes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<UsedRecipe> getUsedRecipes() {
		return usedRecipes;
	}

	public void setUsedRecipes(List<UsedRecipe> usedRecipes) {
		this.usedRecipes = usedRecipes;
	}

}
