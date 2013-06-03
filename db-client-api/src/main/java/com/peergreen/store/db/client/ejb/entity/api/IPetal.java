package com.peergreen.store.db.client.ejb.entity.api;

import java.util.Set;

/**
 * Interface defining an entity bean representing a petal
 */

public interface IPetal {
	
	 /**
     * Method to retrieve the petal's vendor
     * 
     * @return the vendor which provides the petal
     */
	IVendor getVendor();
	
	/**
     * Method to set the petal's vendor
     * 
     * @param vendor The vendor of the petal to set
     */
	void setVendor(IVendor vendor);
	
	
	 /**
     * Method to retrieve the petal's artifactId
     * 
     * @return the artifactId of the petal
     */
	String getArtifactId();
	
	/**
     * Method to set the petal's artifactId
     * 
     * @param artifactId The artifactId of the petal to set
     */
	void setArtifactId(String artifactId);
	
	
	/**
     * Method to retrieve the petal's version
     * 
     * @return the version of the petal
     */
	String getVersion();
	
	/**
     * Method to set the petal's version
     * 
     * @param version The version of the petal to set
     */
	void setVersion(String version);

    /**
     * Method to retrieve the petal's category
     * 
     * @return the category which belongs the petal
     */
    ICategory getCategory();


    /**
     * Method to set the petal's category
     * 
     * @param category The category of the petal to set
     */
    void setCategory(ICategory category);


    /**
     * Method to retrieve the petal's requirement
     * 
     * @return A Set of requirements of the petal instance
     */
    Set<IRequirement> getRequirements();


    /**
     * Method to add requirements to the petal
     * 
     * @param requirement A Set of requirements to add for the petal 
     */
    void setRequirements(Set<IRequirement> requirements);


    /**
     * Method to retrieve the petal's capabilities
     * 
     * @return A Set of capabilities of the petal instance
     */
    Set<ICapability> getCapabilities();


    /**
     * Method to set the petal's capabilities
     * 
     * @param capabilities A Set of capabilities to add for the petal 
     */
    void setCapabilities(Set<ICapability> capabilities);

}
