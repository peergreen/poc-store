package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a petal
 */

public interface IPetal {

    /**
     * Method to retrieve the petal's category
     * 
     * @return the category which belongs the petal
     */
    ICategory getCategory();


    /**
     * Method to set the petal's category
     * 
     * @param The category of the petal to set
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
     * @param A list of requirements to add for the petal 
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
     * @param A list of capabilities to add for the petal 
     */
    void setCapabilities(List<ICapability> capabilities);

}
