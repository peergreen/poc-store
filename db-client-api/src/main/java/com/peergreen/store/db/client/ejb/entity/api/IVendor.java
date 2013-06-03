package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining a vendor for a petal
 */
public interface IVendor {
	
	/**
	 * Method to get the name of the  vendor
	 * 
	 * @return the vendorDescription
	 */
	String getVendorName();
	
	/**
	 * Method to set a name for the vendor 
	 * 
	 * @param  the name to set for the vendor
	 */
	
	void setVendorName(String vendorName);

	/**
	 * Method to get the description's vendor
	 * 
	 * @return the vendorDescription
	 */
	String getVendorDescription();

	/**
	 * Method to set a description for the vendor 
	 * 
	 * @param the description to set for the vendor
	 */
	void setVendorDescription(String vendorDescription);
	
	/**
	 * Method for retrieve all the petals provided by the vendor instance
	 * 
	 * @return List containing petals that are provided by the vendor instance
	 */
	List<IPetal> getPetals();


	/**
	 * Method to add petals to the list of petals that are provided by the vendor instance
	 * 
	 * @param List containing the petals to set
	 */
	void setPetals(List<IPetal> petals);

	

}
