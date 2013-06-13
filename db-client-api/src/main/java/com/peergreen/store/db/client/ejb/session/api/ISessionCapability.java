package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;

public interface ISessionCapability {

    /**
     * Method to add a new capability in the database.
     * 
     * @param capabilityName the capability's name
     * @param namespace the capability's namespace
     * @param properties the capability's properties
     * @param petal the petal which provides this capability
     * @return The capability creates
     */
    Capability addCapability(String capabilityName, String namespace, Map<String,Object> properties);

    /**
     * Method to delete a capability in the database
     * 
     * @param capabilityName the capability's name
     */
    void deleteCapability (String capabilityName);

    /**
     * Method to find a capability in the database
     * 
     * @param capabilityName the capability's name
     * @return the capability with the name 'capabilityName'
     */
    Capability findCapability (String capabilityName);

    /**
     * Method to collect the petals which give the capability with the name 'capabilityName'
     * 
     * @param name the capability's name
     * @return A collection of all the petals which give this capability
     */
    Collection<Petal> collectPetals(String capabilityName);

    /**
     * Method to add a petal to the list of petals which give the capability
     * 
     * @param capability the capability that is given by the petal
     * @param petal the petal to add 
     * 
     * @return A new capability with a new list of petals 
     */
    Capability addPetal(Capability capability, Petal petal);

    /**
     * Method to remove a petal to the list of petals which give the capability
     * 
     * @param capability the capability that is given by the petal
     * @param petal the petal to remove
     * 
     * @return A new capability with a new list of petals 
     */
    Capability removePetal (Capability capability, Petal petal);
    
    /**
     * Method to collect all the capability in the database
     * 
     * @return A collection of capabilities in the database
     */
    Collection<Capability> collectcapabilities();

}