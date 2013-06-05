package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.Map;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;

/**
 * Class defining an entity session to manage the entity Capability
 * <ul>
 *      <li>Create a capability on database</li>
 *      <li>Remove a capability from the database</li>
 *      <li>Find a capability from the database</li>
 *      <li>Collect all the petals that have the capability</li>>
 *      <li>Add a petal to the list of petals of a capability</li>
 *      <li>Remove a petal from the list of petals of a capability</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultCapability implements ISessionCapability{

    /**
     * Method to add a new capability in the database.
     * 
     * @param capabilityName the capability's name
     * @param namespace the capability's namespace
     * @param properties the caability's properties
     * @return The capability creates
     */
    @Override
    public Capability addCapability(String capabilityName, String namespace, Map<String, String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to delete a capability in the database
     * 
     * @param capabilityName the capability's name
     */
    @Override
    public void deleteCapability(String capabilityName) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to find a capability in the database
     * 
     * @param capabilityName the capability's name
     * @return the capacity with the name 'capabilityName'
     */
    @Override
    public Capability findCapability(String capabilityName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect the petals which give the capability with the name 'capabilityName'
     * 
     * @param name the capability's name
     * @return A collection of all the petals which give this capability
     */
    @Override
    public Collection<Petal> collectPetals(String capabilityName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to add a petal to the list of petals which give the capability
     * 
     * @param capability the capability that is given by the petal
     * @param petal the petal to add 
     * 
     * @return A new capability with a new list of petals 
     */
    @Override
    public Capability addPetal(Capability capability, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to remove a petal to the list of petals which give the capability
     * 
     * @param capability the capability that is given by the petal
     * @param petal the petal to remove
     * 
     * @return A new capability with a new list of petals 
     */
    @Override
    public Capability removePetal(Capability capability, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }


}