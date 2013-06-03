package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

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
	private List<IPetal> petals;

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
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
	public List<IPetal> getPetals() {
		return petals;
	}

	/**
	 * @param petals the petals to set
	 */
	public void setPetals(List<IPetal> petals) {
		this.petals = petals;
	}
}
