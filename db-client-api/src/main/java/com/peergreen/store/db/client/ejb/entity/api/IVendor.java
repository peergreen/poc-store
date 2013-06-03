package com.peergreen.store.db.client.ejb.entity.api;

import java.util.Set;

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
	 * @param vendorName the name to set for the vendor
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
	 * @param vendorDescription the description to set for the vendor
	 */
	void setVendorDescription(String vendorDescription);
	
	/**
	 * Method for retrieve all the petals provided by the vendor instance
	 * 
	 * @return Set containing petals that are provided by the vendor instance
	 */
	Set<IPetal> getPetals();


	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	void setPetals(Set<IPetal> petals);

	

}
