package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


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
     * 
     * @param capabilityName the capability name
     * @param version capability version
     * @param namespace capability namespace
     * @param properties capability properties
     * @return created Capability instance
     * @throws EntityAlreadyExistsException
     */
    @Override
    public Capability addCapability(String capabilityName, String version, String namespace,
            Map<String, String> properties) throws EntityAlreadyExistsException {

        Capability temp = findCapability(capabilityName, version);
        if (temp != null) {
            throw new EntityAlreadyExistsException("Capability " + capabilityName +
                    " in version " + version + " already exists on database.");
        } else {
            Capability capability = new Capability(capabilityName, version, namespace, properties);
            entityManager.persist(capability); 

            return capability;
        }
    }

    /**
     * Method to delete a capability in the database.
     * 
     * @param capabilityName capability name
     * @param version capability version 
     */
    @Override
    public void deleteCapability(String capabilityName, String version) {
        Capability temp = findCapability(capabilityName,version);
        if (temp != null) {
            entityManager.remove(temp);
        }
    }

    /**
     * Method to find a capability in the database. <br />
     * If the capability to find doesn't exist, we return {@literal null}.
     * 
     * @param capabilityName the capability's name
     * @return retrieved Capability instance or {@literal null} if no matching instance found
     */
    @Override
    public Capability findCapability(String capabilityName, String version) {
        Query q = entityManager.createNamedQuery("CapabilityByName");
        q.setParameter("name", capabilityName);
        q.setParameter("version", version);

        Capability capabilityResult;
        try { 
            capabilityResult = (Capability)q.getSingleResult();
        } catch (NoResultException e) {
            capabilityResult = null ; 
        }
        return capabilityResult;
    }

    /**
     * Method to collect the petals which provides a specific capability.<br />
     * Throws {@literal NoEntityFoundException} if capability doesn't exist.
     * 
     * @param name capability name
     * @param version capability version 
     * @return collection of all petals providing this specific capability
     */
    @Override
    public Collection<Petal> collectPetals(String capabilityName, String version) throws NoEntityFoundException {
        Capability capability = findCapability(capabilityName,version);
        if (capability != null) {
            return capability.getPetals();
        } else {
            throw new NoEntityFoundException("Capability " + capabilityName + " in version " + version + " is not present on database.");
        }
    }

    /**
     * Method to add a petal to the list of petals which give the capability.
     * 
     * @param capability capability provided by the petal
     * @param petal petal to add 
     * @return modified Capability instance (updated list of providers)
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
     * Method to remove a petal from the petals list providing a capability.
     * 
     * @param capability capability provided by the petal.
     * @param petal petal to remove
     * @return modified Capability instance (updated list of providers)
     */
    @Override
    public Capability removePetal(Capability capability, Petal petal) {
        Set<Petal> petals = capability.getPetals();
        petals.remove(petal);
        
        // We can delete this capability if no petal provides this capability any more.
        if (petals.isEmpty()) {
            entityManager.remove(capability);
            capability = null;
        } else {
            capability = entityManager.merge(capability);
        }
        return capability;
    }

    /**
     * Method to collect all capabilities present in database.
     * 
     * @return collection of all capabilities which are stored in the database
     */
    @Override
    public Collection<Capability> collectCapabilities() {
        Query capQuery = entityManager.createNamedQuery("Capability.findAll");
        @SuppressWarnings("unchecked")
        List<Capability> capList = capQuery.getResultList();
        Set<Capability> capSet = new HashSet<Capability>();
        capSet.addAll(capList);

        return capSet;
    }

    /**
     * Method to change capability namespace.
     * 
     * @param capability capability to modify 
     * @param namespace new namespace 
     * @return modified Capability instance
     */
    @Override
    public Capability updateNamespace(Capability capability, String namespace) {
        capability.setNamespace(namespace);
        return entityManager.merge(capability);
    }
    
    /**
     * Method to change capability properties.
     * 
     * @param capability capability to modify 
     * @param properties new properties for the capability 
     * @return modified Capability instance
     */
    @Override
    public Capability updateProperties(Capability capability, Map<String, String> properties) {
        capability.setProperties(properties);
        return entityManager.merge(capability);
    }
}