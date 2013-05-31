package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * Entity Bean representing the category of a petal  
 */

@Entity
@SequenceGenerator(name="idCategorySeq", initialValue=1, allocationSize=50)
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idCategorySeq")
	private int categoryId;
	private String categoryname;
	@OneToMany(mappedBy="category")
	private List<Petal> petals;

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryname
	 */
	public String getCategoryname() {
		return categoryname;
	}

	/**
	 * @param categoryname the categoryname to set
	 */
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	/**
	 * @return the petals
	 */
	public List<Petal> getPetals() {
		return petals;
	}

	/**
	 * @param petals the petals to set
	 */
	public void setPetals(List<Petal> petals) {
		this.petals = petals;
	}
}
