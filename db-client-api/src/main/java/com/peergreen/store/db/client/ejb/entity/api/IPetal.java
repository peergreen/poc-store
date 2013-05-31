package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a petal
 */

public interface IPetal {

    /**
     * @return the category
     */
    ICategory getCategory();


    /**
     * @param category the category to set
     */
    void setCategory(ICategory category);


    /**
     * @return the requirements
     */
    List<IRequirement> getRequirements();


    /**
     * @param requirements the requirements to set
     */
    void setRequirements(List<IRequirement> requirements);


    /**
     * @return the capabilities
     */
    List<ICapability> getCapabilities();


    /**
     * @param capabilities the capabilities to set
     */
    void setCapabilities(List<ICapability> capabilities);

}
