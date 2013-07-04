package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
 *      <li>Collect all existing capabilities on database</li>
 *      <li>Modify existing capability changing his namespace</li>
 *      <li>Modify existing capability changing his properties</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultCapability implements ISessionCapability{

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a new capability in the database.
     * The attributes petals is empty when the capability is created
     * 
     * @param capabilityName the capability's name
     * @param namespace the capability's namespace
     * @param properties the capability's properties

     * @return The capability that was created
     */
    @Override
    public Capability addCapability(String capabilityName,String version, String namespace, Map<String, String> properties) throws EntityExistsException {

        Capability temp = findCapability(capabilityName, version);
        if(temp != null){

            throw new EntityExistsException();
        }
        else
        {
            Capability  capability = new Capability(capabilityName, version, namespace, properties);

            entityManager.persist(capability); 

            return capability;
        }

    }

    /**
     * Method to delete a capability in the database.
     * We throw an exception if the capability to delete doesn't exist
     * 
     * @param capabilityName the capability's name
     * @param version the capability's version 
     */
    @Override
    public void deleteCapability(String capabilityName, String version)throws IllegalArgumentException {

        Capability temp = findCapability(capabilityName,version);
        if(temp != null)
        {
            entityManager.remove(temp);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to find a capability in the database.
     * If the capability to find doesn't exist, we return null
     * 
     * @param capabilityName the capability's name
     * @return the capacity with the name 'capabilityName'
     */
    @Override
    public Capability findCapability(String capabilityName, String version){

        Query q = entityManager.createNamedQuery("CapabilityByName");
        q.setParameter("name", capabilityName);
        q.setParameter("version", version);

        Capability capabilityResult;
        try{ 
            capabilityResult = (Capability)q.getSingleResult();

        }catch (NoResultException e){
            capabilityResult = null ; 
        }

        return capabilityResult;

    }

    /**
     * Method to collect the petals which give a capability.
     * We throw a new exception when the capability doesn't exist 
     * 
     * @param name the capability's name
     * @param version the capability's version 
     * 
     * @return A collection of all the petals which give this capability
     */
    @Override
    public Collection<Petal> collectPetals(String capabilityName, String version) throws IllegalArgumentException {

        Capability capability = findCapability(capabilityName,version);
        if(capability != null){
            return capability.getPetals();
        }
        else
        {
            String message = " The capability doesn't exist";
            throw new IllegalArgumentException(message);
        }
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

    /**
     * Method to collect all the capabilities in the database 
     * 
     * @return A collection of all the capabilities which are stored in the database 
     */
    @Override
    public Collection<Capability> collectCapabilities() {

        Query capQuery = entityManager.createNamedQuery("Capability.findAll");
        List<Capability> capList = capQuery.getResultList();
        Set<Capability> capSet = new HashSet<Capability>();
        capSet.addAll(capList);

        return capSet;
    }

    /**
     * Method to change a capability's namespace
     * 
     * @param capability the capability to modify 
     * @param namespace the new namespace 
     * 
     * @return The capability with new attribute 
     */
    @Override
    public Capability updateNamespace(Capability capability, String namespace) {

        capability.setNamespace(namespace);
        return entityManager.merge(capability);

    }
    
    /**
     * Method to change a capability's properties
     * 
     * @param capability the capability to modify 
     * @param properties new properties for the capability 
     * 
     * @return The capability with new attribute 
     */
    @Override
    public Capability updateProperties(Capability capability, Map<String, String> properties) {

        capability.setProperties(properties);
        return entityManager.merge(capability);

    }


}