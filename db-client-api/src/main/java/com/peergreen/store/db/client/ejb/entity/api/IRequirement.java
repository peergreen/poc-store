package com.peergreen.store.db.client.ejb.entity.api;

import java.util.Set;


/**
 * Interface defining an entity bean representing requirement of a petal
 */

public interface IRequirement {

	/**
	 * Method to retrieve the requirement's Id
	 * 
	 * @return the id of the requirement instance
	 */
	public int getRequirementId();

	/**
	 * Method to retrieve the requirement's filter 
	 * 
	 * @return the filter of the requirement instance
	 */
	String getFilter();


	/**
	 * Method to set the requiremeent's filter
	 * 
	 * @param filter The filter to set
	 */
	void setFilter(String filter);


	/**
	 * Method to retrieve the petals which had this requirement
	 * 
	 * @return A Set containing all the petals which had this requirement 
	 */
	Set<IPetal> getPetals();


	/**
	 * Method to add new petals that have this requirement
	 * 
	 * @param petals A Set of new petals that have this requirement
	 */
	void setPetals(Set<IPetal> petals);

}
