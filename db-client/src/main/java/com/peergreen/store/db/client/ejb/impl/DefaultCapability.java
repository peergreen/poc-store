package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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


    private EntityManager entityManager;


    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



    /**
     * Method to add a new capability in the database.
     * 
     * @param capabilityName the capability's name
     * @param namespace the capability's namespace
     * @param properties the capability's properties
     * @param petal the petal which provides this capability
     * @return The capability creates
     */
    @Override
    public Capability addCapability(String capabilityName, String namespace, Map<String, Object> properties) {

           Capability capability = new Capability();
            capability.setName(capabilityName);
            capability.setNamespace(namespace);
            capability.setProperties(properties);
            Set<Petal> petals = new HashSet<Petal>();
            capability.setPetals(petals);
            entityManager.persist(capability); 
             
        return capability;
    }



    /**
     * Method to delete a capability in the database
     * 
     * @param capabilityName the capability's name
     */
    @Override
    public void deleteCapability(String capabilityName) {

        Capability temp = entityManager.find(Capability.class, capabilityName);
        entityManager.remove(temp);
    }

    /**
     * Method to find a capability in the database
     * 
     * @param capabilityName the capability's name
     * @return the capacity with the name 'capabilityName'
     */
    @Override
    public Capability findCapability(String capabilityName) {

        Capability capability = entityManager.find(Capability.class, capabilityName);
        return capability;
    }

    /**
     * Method to collect the petals which give the capability with the name 'capabilityName'
     * 
     * @param name the capability's name
     * @return A collection of all the petals which give this capability
     */
    @Override
    public Collection<Petal> collectPetals(String capabilityName) {

        Capability capability = entityManager.find(Capability.class, capabilityName);

        return capability.getPetals();
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

        Set<Petal> petals = capability.getPetals();
        petals.add(petal);
        capability.setPetals(petals);
        entityManager.merge(capability);
        return capability;
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

        Set<Petal> petals = capability.getPetals();
        petals.remove(petal);
        /**
         * If any petal doesn't give this capability no longer
         * so we can delete it
         */
        if(petals.isEmpty()){
            entityManager.remove(capability);
            capability = null;
        }
        else {
            capability = entityManager.merge(capability);

        }
        return capability;
    }

    @Override
    public Collection<Capability> collectcapabilities() {
        // TODO Auto-generated method stub
        return null;
    }


}