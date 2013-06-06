package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity Bean representing the category of a petal  
 */
@Entity
@Table(name="Categories")
public class Category{

	@Id
	private String categoryName;

	@OneToMany(mappedBy="category", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Set<Petal> petals;

	
	/**
	 * Method to retrieve the name of the category instance
	 * 
	 * @return the category's name
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Method to give the name of the category instance 
	 * 
	 * @param categoryname the category's name to set
	 */
	public void setCategoryName(String categoryname) {
		this.categoryName = categoryname;
	}

	/**
	 * Method for retrieve all the petals that belongs to this category
	 * 
	 * @return Set containing petals that belongs to this category
	 */
	public Set<Petal> getPetals() {
		return petals;
	}

	/**
	 * Method to add petals to the Set of petals that belongs to this category
	 * 
	 * @param petals Set containing the petals to set
	 *//**
	 * @param petals the petals to set
	 */
	public void setPetals(Set<Petal> petals) {
		this.petals = petals;
	}
}
