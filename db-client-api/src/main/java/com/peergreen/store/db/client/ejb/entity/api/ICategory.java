package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;


/**
 * Entity Bean representing the category of a petal  
 */
public interface ICategory {

	/**
	 * Method to retrieve the id of the category instance
	 * 
	 * @return the category's id
	 */
	int getCategoryId();

	/**
	 * Method to retrieve the name of the category instance
	 * 
	 * @return the category's name
	 */
	String getCategoryname();


	/**
	 * Method to give the name of the category instance 
	 * 
	 * @param  the category's name to set
	 */
	void setCategoryname(String categoryname);


	/**
	 * Method for retrieve all the petals that belongs to this category
	 * 
	 * @return List containing petals that belongs to this category
	 */
	List<IPetal> getPetals();


	/**
	 * Method to add petals to the list of petals that belongs to this category
	 * 
	 * @param List containing the petals to set
	 */
	void setPetals(List<IPetal> petals);
}
