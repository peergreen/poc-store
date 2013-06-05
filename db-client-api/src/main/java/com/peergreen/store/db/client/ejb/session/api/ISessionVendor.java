package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;


public interface ISessionVendor {

    /**
     * Method to add a new instance of vendor in the database 
     * The attribute petals are null when creating the group
     * 
     * @param vendorName the vendor's name
     * @param vendorDescription the vendor's description
     * 
     * @return A new instance of Vendor
     */
	Vendor addVendor(String vendorName, String vendorDescription);
	
	/**
	 * Method to delete a vendor with the name 'vendorName'
	 * 
	 * @param vendorName the name of the vendor to delete
	 */
	void deleteVendor(String vendorName);
	
	/**
	 * Method to find the vendor with the name 'vendorName'
	 * 
	 * @param vendorName the name of the vendor to find
	 * 
	 * @return the vendor with the name 'vendorName'
	 */
	Vendor findVendor(String vendorName);
	
	/**
	 * Method to collect all the petals provided by
	 * the vendor with the name 'vendorName'
	 * 
	 * @param vendorName the vendor's name to which we collect petals which he provides
	 * 
	 * @return A collection of petals which are provided by the vendor with the name 'vendorName'
	 */
	Collection<Petal> collectPetals(String vendorName);
	
	/**
	 * Method to add a new petal to those provided by a vendor
	 * 
	 * @param vendor the vendor to which add a new petal to provide
	 * @param petal the petal to provide by the vendor
	 * 
	 * @return A new vendor with the petal added in his list of petals provided
	 */
	Vendor addPetal(Vendor vendor, Petal petal);
	
	/**
	 * Method to delete a petal from those provided by a vendor
     * 
     * @param vendor the vendor to which remove a petal
     * @param petal the petal to remove from the list of petals provided by the vendor
     * 
     * @return A new vendor with the petal removed from his list of petals provided
	 */
	Vendor removePetal(Vendor vendor, Petal petal);

}
