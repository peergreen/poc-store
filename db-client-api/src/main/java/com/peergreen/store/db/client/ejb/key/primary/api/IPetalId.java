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
	 * @param the artifactid of the petal 
	 */
	String getArtifactId();

	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	String getVersion();
	
	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	void setVendor(IVendor vendor);
	
	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	void setArtifactId(String artifactId);
	
	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	void setVersion(String version);

	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	boolean equals(Object obj);
	
	/**
	 * Method to add petals to the Set of petals that are provided by the vendor instance
	 * 
	 * @param petals Set containing the petals to set
	 */
	int hashCode();

}
