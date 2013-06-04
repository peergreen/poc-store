package com.peergreen.store.db.client.ejb.key.primary.api;

import com.peergreen.store.db.client.ejb.entity.api.IVendor;

public interface IPetalId {

	/**
	 * Method to get the attribute Vendor
	 * 
	 * @return the vendor of the petal
	 */
	IVendor getVendor();

	/**
	 * Method to get the attribute ArtifactId
	 * 
	 * @return the artifactid of the petal 
	 */
	String getArtifactId();

	/**
	 * Method to get the attribut version 
	 * 
	 * @return the version of the petal
	 */
	String getVersion();
	
	/**
	 * Method to set the petal's vendor
	 * 
	 * @param vendor the vendor to set for the petal
	 */
	void setVendor(IVendor vendor);
	
	/**
	 * Method to set the petal's artifactId
	 * 
	 * @param artifactid An artifactId to set for the petal
	 */
	void setArtifactId(String artifactId);
	
	/**
	 * Method to set the petal's version
	 * 
	 * @param version A version to set for the petal
	 */
	void setVersion(String version);

	/**
	 * Method for comparing an object with an instance of petalId
	 * 
	 * @param obj the object to compare to the petalId instance
	 * @return the comparison result
	 */
	boolean equals(Object obj);
	
	/** 
	 * @return 
	 */
	int hashCode();

}
