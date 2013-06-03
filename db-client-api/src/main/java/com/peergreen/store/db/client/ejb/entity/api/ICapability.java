package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;
import java.util.Map;

/**
 *Interface Entity Bean representing capability of a petal
 */

public interface ICapability {
	

	/**
	 * Method to get the id of the capability instance
	 * 
	 * @return the id of the capability
	 */
	int getCapabilityId() ;

	/**
	 * Method for retrieve the properties of the capability instance
	 * 
	 * @return Map containing all the properties of the capability
	 */
	Map<String, String> getProperties() ;


	/**
	 * Method for set the properties of the capability instance
	 * 
	 * @param the properties to set
	 */
	void setProperties(Map<String, String> properties) ;


	/**
	 * Method to retrieve the petals which provides this capability instance
	 * 
	 * @return List containing petals 
	 */
	List<IPetal> getPetals();


	/**
	 * Method for add others petals to the list of petals which provides
	 * this capability instance
	 * 
	 * @param List containing petals to set
	 */
	void setPetals(List<IPetal> petals) ;
}
