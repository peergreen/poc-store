package com.peergreen.store.controller.resolver.wrapper;

import org.osgi.resource.Requirement;

public interface IRequirementWrapper extends Requirement {

    /**
     * Method to init OSGi like requirement with Requirement instance.
     * 
     * @param requirement to use for init OSGi Requirement
     */
    void initFromRequirement(com.peergreen.store.db.client.ejb.entity.Requirement requirement);
    
}
