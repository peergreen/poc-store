package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.key.primary.PetalId;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;

/**
 * Class defining an entity session to manage the entity Petal
 * <ul>
 *      <li>Create a petal in a database</li>
 *      <li>Find a petal from the database</li>
 *      <li>Collect all the groups that have access to a petal</li>
 *      <li>Collect all the capabilities given by a petal</li>
 *      <li>Collect all the requirements of a petal</li>
 *      <li>Modify a petal</li>
 *      <li>Remove a petal from the database</li>
 *      <li>Give an access to a group for a petal</li>
 *      <li>Remove an access to a group for a petal</li>
 *      <li>Add a category for a petal</li>
 *      <li>Change the category of a petal</li>
 *      <li>Get the category of a petal</li>
 *      <li>Add a capability to those which are given by a petal</li>
 *      <li>Remove a capability given by a petal</li>
 *      <li>Add a requirement to those which are needed by a petal</li>
 *      <li>Remove a requirement needed by a petal</li>
 *      <li>Collect all existing petals on database</li>
 *      <li>Retrieve petal's origin {LOCAL, STAGING, REMOTE}</li>
 *      <li>Set petal's origin {LOCAL, STAGING, REMOTE}</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultPetal implements ISessionPetal {

    private ISessionVendor sessionVendor;
    private ISessionCategory sessionCategory;
    private ISessionCapability sessionCapability;
    private ISessionRequirement sessionRequirement;
    private ISessionGroup sessionGroup;

    private EntityManager entityManager;

    /**
     * @param sessionVendor the sessionVendor to set
     */
    @EJB
    public void setSessionVendor(ISessionVendor sessionVendor) {
        this.sessionVendor = sessionVendor;
    }

    /**
     * @param sessionCategory the sessionCategory to set
     */
    @EJB
    public void setSessionCategory(ISessionCategory sessionCategory) {
        this.sessionCategory = sessionCategory;
    }

    /**
     * @param sessionCapability the sessionCapability to set
     */
    @EJB
    public void setSessionCapability(ISessionCapability sessionCapability) {
        this.sessionCapability = sessionCapability;
    }

    /**
     * @param sessionRequirement the sessionRequirement to set
     */
    @EJB
    public void setSessionRequirement(ISessionRequirement sessionRequirement) {
        this.sessionRequirement = sessionRequirement;
    }

    /**
     * @param sessionGroup the sessionGroup to set
     */
    @EJB
    public void setSessionGroup(ISessionGroup sessionGroup) {
        this.sessionGroup = sessionGroup;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Method to create an instance of a petal and add it in the database.
     * It will thrw an EnityNotFoundException if the group administrateur doesn't exist.
     * 
     * @param vendorName The petal's vendor name
     * @param artifactId The petal's artifactId
     * @param version The petal's version
     * @param description The petal's description
     * @param category The petal's category
     * @param capabilities The petal's capabilities 
     * @param requirements The petal's requirements
     * @param Origin the petal's origin 
     * 
     * @return A new instance of petal 
     */
    @Override
    public Petal addPetal(Vendor vendor, String artifactId, String version, String description, Category category,
            Collection<Capability> capabilities, Collection<Requirement> requirements, Origin origin)throws EntityNotFoundException {

        Group group = sessionGroup.findGroup("Administrateur");
        /*
         * All the petals should be accessible via the group "Administrateur" at least.
         * So if it doesn't exist, this method will throw an exception 
         * 
         */
        if(group == null){
            throw new EntityNotFoundException("The group Administrateur must be created first at all");
        }
        else{
            Petal petal = new Petal(vendor, artifactId, version, category,
                    description, (Set<Requirement>)requirements,(Set<Capability>) capabilities, origin);

            Set<Group> groups = new HashSet<Group>();
            groups.add(group);
            petal.setGroups(groups);

            try{
                entityManager.persist(petal);
            }catch(EntityExistsException e){

            }

            sessionVendor.addPetal(vendor, petal);
            sessionCategory.addPetal(category, petal);
            sessionGroup.addPetal(group, petal);

            Iterator<Capability> it = capabilities.iterator();
            while(it.hasNext()) {
                sessionCapability.addPetal(it.next(), petal);
            }

            Iterator<Requirement> itreq = requirements.iterator();
            while(itreq.hasNext()) {
                sessionRequirement.addPetal(itreq.next(), petal);
            }
            return petal;

        }
    }

    /**
     * Method to find a petal 
     * 
     * @param vendor the petal's vendor
     * @param artifactId the petal's artifactId!
     * @param version the petal's version
     * 
     * @return The petal with the attributes given in parameters
     */
    @Override
    public Petal findPetal(Vendor vendor, String artifactId, String version) {

        PetalId petalId = new PetalId(vendor, artifactId, version);
        Petal petal =entityManager.find(Petal.class, petalId);
        return petal;
    }

    /**
     * Method to collect the groups that have access to a petal 
     * 
     * @param petal The petal to which collect the groups that have access to it
     * 
     * @return A collection of groups that have access to the petal
     */
    @Override
    public Collection<Group> collectGroups(Petal petal) {

        return petal.getGroups();
    }

    /**
     * Method to collect the capabilities of a petal 
     * 
     * @param petal The petal to which collect the capabilities
     * 
     * @return A collection of capabilities of the petal 
     */
    @Override
    public Collection<Capability> collectCapabilities(Petal petal) {

        return petal.getCapabilities();
    }

    /**
     * Method to collect the requirements of a petal
     * 
     * @param petal The petal to which collect the requirements
     * 
     * @return A collection of the requirements of the petal 
     */
    @Override
    public Collection<Requirement> collectRequirements(Petal petal) {

        return petal.getRequirements();
    }


    /**
     * Method to delete an instance of petal
     * 
     * @param petal The petal to delete
     */
    @Override
    public void deletePetal(Petal petal) {

        entityManager.remove(petal);
    }

    /**
     * Method to update the description of petal
     * 
     * @param petal The petal that which the description will change
     * @param newDescription the new description of the petal
     */
    @Override
    public Petal updateDescription(Petal petal, String newDescription) {

        petal.setDescription(newDescription);
        return entityManager.merge(petal);

    }

    /**
     * Method to update the origin of petal
     * 
     * @param petal The petal that which the origin will change
     * @param newOrigin the new origin of the petal
     */
    @Override
    public Petal updateOrigin(Petal petal, Origin newOrigin) {

        petal.setOrigin(newOrigin);
        return entityManager.merge(petal);

    }

    /**
     * Method to give an access to a petal from a group
     * 
     * @param petal The petal which is to be set up access
     * @param group The group you want to give an access to the petal
     * 
     * @return  A petal with new groups that have access to it  
     */
    @Override
    public Petal giveAccesToGroup(Petal petal, Group group) {

        Set<Group> groups = petal.getGroups();
        groups.add(group);
        petal.setGroups(groups);
        entityManager.merge(petal);

        sessionGroup.addPetal(group,petal);

        return petal;
    }

    /**
     * Method to remove an access to a petal from a group
     * 
     * @param petal The petal which is to be set up access
     * @param group The group you want to remove access to the petal
     * 
     * @return A petal with new groups that have access to it except the one removed 
     */
    @Override
    public Petal removeAccesToGroup(Petal petal, Group group) {

        Set<Group> groups = petal.getGroups();
        groups.remove(group);
        petal.setGroups(groups);
        entityManager.merge(petal);

        sessionGroup.removePetal(group, petal);

        return petal;
    }

    /**
     * Method to add a category for the petal 
     * 
     * @param petal An instance of petal
     * @param category A category to set for the petal
     * 
     * @return A petal with the Category 'category'
     */
    @Override
    public Petal addCategory(Petal petal, Category category) {

        petal.setCategory(category);
        entityManager.merge(petal);

        sessionCategory.addPetal(category, petal);

        return petal;
    }

    /**
     * Method to get a category of a petal
     * 
     * @param petal An instance of petal
     * 
     * @return the category of the petal given in parameter
     */
    @Override
    public Category getCategory(Petal petal) {

        return petal.getCategory();
    }

    /**
     * Method to add a new capability for a petal
     * 
     * @param petal The petal to which add a new capability
     * @param capability The capability to add for the petal
     * 
     * @return A new petal with the new capability
     */
    @Override
    public Petal addCapability(Petal petal, Capability capability) {

        Set<Capability> capabilities = petal.getCapabilities();
        capabilities.add(capability);
        petal.setCapabilities(capabilities);

        entityManager.merge(petal);

        sessionCapability.addPetal(capability, petal);

        return petal;
    }

    /**
     *  Method to remove a capability of a petal
     * 
     * @param petal The petal to which remove a capability
     * @param capability The capability to remove
     * 
     * @return A new petal without the capability deleted
     */
    @Override
    public Petal removeCapability(Petal petal, Capability capability) {

        Set<Capability> capabilities = petal.getCapabilities();
        capabilities.remove(capability);
        petal.setCapabilities(capabilities);

        entityManager.merge(petal);

        sessionCapability.removePetal(capability, petal);

        return petal;
    }

    /**
     * Method to add a new requirement for a petal
     * 
     * @param petal The petal to which add a new requirement
     * @param requirement The requirement to add for the petal
     * 
     * @return A new petal with the new requirement
     */
    @Override
    public Petal addRequirement(Petal petal, Requirement requirement) {

        Set<Requirement> requirements = petal.getRequirements();
        requirements.add(requirement);
        petal.setRequirements(requirements);

        entityManager.merge(petal);

        sessionRequirement.addPetal(requirement, petal);

        return petal;
    }

    /**
     * Method to remove a requirement of a petal
     * 
     * @param petal The petal to which remove a requirement
     * @param requirement The requirement to remove
     * 
     * @return A new petal without the requirement deleted
     */
    @Override
    public Petal removeRequirement(Petal petal, Requirement requirement) {

        Set<Requirement> requirements = petal.getRequirements();
        requirements.remove(requirement);
        petal.setRequirements(requirements);

        entityManager.merge(petal);

        sessionRequirement.removePetal(requirement, petal);

        return petal;
    }

    /**
     * Method to collect all existing petals on database.
     * 
     * @return petals list
     */
    @Override
    public Collection<Petal> collectPetals() {

        Query petals = entityManager.createNamedQuery("Petal.findAll");
        List<Petal> petalsList = petals.getResultList();
        Set<Petal> petalSet = new HashSet<Petal>();
        petalSet.addAll(petalsList);
        return petalSet;

    }

    /**
     * Method to collect all the petals in the local repository 
     * 
     * @return A collection of petals coming from the local repository
     */
    @Override
    public Collection<Petal> collectPetalsFromLocal() {

        Query petals = entityManager.createQuery("select p from Petal p where p.origin = :origin");
        petals.setParameter("origin", Origin.LOCAL);

        List<Petal> petalsList = petals.getResultList();
        Set<Petal> petalSet = new HashSet<Petal>();
        petalSet.addAll(petalsList);

        return petalSet;
    }

    /**
     * Method to collect all the petals in the staging repository 
     * 
     * @return A collection of petals coming from the staging repository
     */
    @Override
    public Collection<Petal> collectPetalsFromStaging() {

        Query petals = entityManager.createQuery("select p from Petal p where p.origin = :origin");
        petals.setParameter("origin", Origin.STAGING);

        List<Petal> petalsList = petals.getResultList();
        Set<Petal> petalSet = new HashSet<Petal>();
        petalSet.addAll(petalsList);

        return petalSet;
    }

    /**
     * Method to collect all the petals in the remote repository 
     * 
     * @return A collection of petals coming from the remote repository
     */
    @Override
    public Collection<Petal> collectPetalsFromRemote() {

        Query petals = entityManager.createQuery("select p from Petal p where p.origin = :origin");
        petals.setParameter("origin", Origin.REMOTE);

        List<Petal> petalsList = petals.getResultList();
        Set<Petal> petalSet = new HashSet<Petal>();
        petalSet.addAll(petalsList);

        return petalSet;
    }

}
