package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Vendor {

	@Id
	private String vendorName;

	private String vendorDescription;

	@OneToMany(mappedBy="vendor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Set<Petal> petals;


	/**
	 * Method to get the name of the  vendor
	 * 
	 * @return the vendorDescription
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * Method to set a name for the vendor 
	 * 
	 * @param vendorName the name to set for the vendor
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * Method to get the description's vendor
	 * 
	 * @return the vendorDescription
	 */
	public String getVendorDescription() {
		return vendorDescription;
	}

	/**
	 * Method to set a description for the vendor 
	 * 
	 * @param vendorDescription the description to set for the vendor
	 */
	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	/**
	 * Method for retrieve all the petals provided by the vendor instance
	 * 
	 * @return Set containing petals that are provided by the vendor instance
	 */
	public Set<Petal> getPetals() {
		return petals;
	}

	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	public void setPetals(Set<Petal> petals) {
		this.petals = petals;
	}


}