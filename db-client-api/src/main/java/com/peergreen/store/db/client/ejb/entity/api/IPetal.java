package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

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
     * @return A list of requirements of the petal instance
     */
    List<IRequirement> getRequirements();


    /**
     * Method to add requirements to the petal
     * 
     * @param requirement A list of requirements to add for the petal 
     */
    void setRequirements(List<IRequirement> requirements);


    /**
     * Method to retrieve the petal's capabilities
     * 
     * @return A list of capabilities of the petal instance
     */
    List<ICapability> getCapabilities();


    /**
     * Method to set the petal's capabilities
     * 
     * @param capabilities A list of capabilities to add for the petal 
     */
    void setCapabilities(List<ICapability> capabilities);

}
