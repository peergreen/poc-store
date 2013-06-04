package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;

/**
 * Entity Bean representing the category of a petal  
 */

@Entity
@SequenceGenerator(name="idCategorySeq", initialValue=1, allocationSize=50)
public class Category implements ICategory {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idCategorySeq")
	private int categoryId;

	private String categoryname;

	@OneToMany(mappedBy="category", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Set<IPetal> petals;

	/**
	 * Method to retrieve the id of the category instance
	 * 
	 * @return the category's id
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * Method to retrieve the name of the category instance
	 * 
	 * @return the category's name
	 */
	public String getCategoryname() {
		return categoryname;
	}

	/**
	 * Method to give the name of the category instance 
	 * 
	 * @param categoryname the category's name to set
	 */
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	/**
	 * Method for retrieve all the petals that belongs to this category
	 * 
	 * @return Set containing petals that belongs to this category
	 */
	public Set<IPetal> getPetals() {
		return petals;
	}

	/**
	 * Method to add petals to the Set of petals that belongs to this category
	 * 
	 * @param petals Set containing the petals to set
	 *//**
	 * @param petals the petals to set
	 */
	public void setPetals(Set<IPetal> petals) {
		this.petals = petals;
	}
}
