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
     * @return The capability creates
     */
    Capability addCapability(String capabilityName,String version, String namespace, Map<String,String> properties);

    /**
     * Method to delete a capability in the database
     * 
     * @param capabilityName the capability's name
     * @param version the version of the capability to delete
     */
    void deleteCapability (String capabilityName, String version);

    /**
     * Method to find a capability in the database
     * 
     * @param capabilityName the capability's name
     * @return the capability with the name 'capabilityName'
     */
    Capability findCapability (String capabilityName, String version);

    /**
     * Method to change a capability existing namespace
     * @param capability the capability to modify
     * @param namespace the new namespace of the capability
     * @return The capability with the change updated
     */
    Capability updateNamespace (Capability capability, String namespace);

    /**
     * Method to change a capability existing properties
     * @param capability the capability to modify
     * @param properties the new properties of the capability
     * @return The capability with the change updated
     */
    Capability updateProperties (Capability capability, Map<String,String> prooperties);

    /**
     * Method to collect the petals which give the capability with the name 'capabilityName'
     * 
     * @param name the capability's name
     * @param version the version of the capability to delete
     * @return A collection of all the petals which give this capability
     */
    Collection<Petal> collectPetals(String capabilityName, String version);

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