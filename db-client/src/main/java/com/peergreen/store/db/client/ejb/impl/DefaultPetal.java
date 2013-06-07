package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;

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
 *      <li>Remove a requirement needed by a petal</li> * </ul>
 * 
 */
@Stateless
public class DefaultPetal implements ISessionPetal {
    
    @PersistenceContext
    private EntityManager entityManager = null;


    /**
     * Method to create an instance of a petal and add it in the database
     * 
     * @param vendor The petal's vendor
     * @param artifactId The petal's artifactId
     * @param version The petal's version
     * @param description The petal's description
     * @param category The petal's category
     * @param capabilities The petal's capabilities 
     * @param requirements The petal's requirements
     * 
     * @return A new instance of petal 
     */
    @Override
    public Petal addPetal(Vendor vendor, String artifactId, String version, String description, Category category,
            Collection<Capability> capabilities, Collection<Requirement> requirements) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to modify a petal 
     * 
     * @param oldpetal The petal to modify
     * @param vendor The new petal's vendor
     * @param artifactId The new petal's artifactId
     * @param version The new petal's version
     * @param description The new petal's description
     * @param category The new petal's category
     * @param capabilities The new petal's capabilities 
     * @param requirements The new petal's requirements
     * 
     * @return The oldpetal with new attributes
     */
    @Override
    public Petal updatePetal(Petal oldpetal, Vendor vendor, String artifactId, String version, String description,
            Category category, Collection<Capability> capabilities, Collection<Requirement> requirements) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to delete an instance of petal
     * 
     * @param petal The petal to delete
     */
    @Override
    public void deletePetal(Petal petal) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to change a petal's category
     *  
     * @param petal An instance of petal 
     * @param category The new category of the petal
     * 
     * @return A new petal with his new category
     */
    @Override
    public Petal changeCategory(Petal petal, Category category) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }


}
