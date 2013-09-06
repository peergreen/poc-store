package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public interface ISessionPetal {

    /**
     * Method to add a petal in the database.<br />
     * Group 'Administrator' must be
     * created first (has access to all petals). <br />
     * Throws {@link NoEntityFoundException} if
     * the group 'Administrator' doesn't exist.<br />
     * Throws {@link EntityAlreadyExistsException} if
     * the petal already exists.
     * 
     * @param vendorName petal vendor name
     * @param artifactId petal artifactId
     * @param version petal version
     * @param description petal description
     * @param category petal category
     * @param capabilities petal capabilities
     * @param requirements petal requirements
     * @param Origin petal origin
     * @return created Petal instance
     * @throws NoEntityFoundException
     * @throws EntityAlreadyExistsException
     */
    Petal addPetal(Vendor vendor, String artifactId, 
            String version, String description, Category category, 
            Set<Capability> capabilities,
            Set<Requirement> requirements, Origin origin)
            throws NoEntityFoundException, EntityAlreadyExistsException;


    /**
     * Method to find a petal.
     * 
     * @param vendor the petal's vendor
     * @param artifactId the petal's artifactId
     * @param version the petal's version
     *
     * @return The petal with the attributes given in parameters or
     * <code>null</code> if the petal doesn't exist
     */
    Petal findPetal(Vendor vendor, String artifactId,String version);
    
    /**
     * Method to find a petal using his id.
     *
     * @param id petal's id
     *
     * @return The petal corresponding to the id given in parameter
     * or <code>null</code>if it doesn't exist
     */
    Petal findPetalById(int id);


    /**
     * Method to collect the groups that have access to a petal.
     *
     * @param petal The petal to which collect the groups that have access to it
     *
     * @return A collection of groups that have access to the petal
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Collection<Group> collectGroups(Petal petal) throws NoEntityFoundException; 

    /**
     * Method to collect the capabilities of a petal.
     * 
     * @param petal The petal to which collect the capabilities
     * 
     * @return A collection of capabilities of the petal
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Collection<Capability> collectCapabilities(Petal petal)
            throws NoEntityFoundException; 

    /**
     * Method to collect the requirements of a petal.
     * 
     * @param petal The petal to which collect the requirements
     * 
     * @return A collection of the requirements of the petal 
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Collection<Requirement> collectRequirements(Petal petal)
            throws NoEntityFoundException; 

    /**
     * Method to update the description of petal.
     * 
     * @param petal The petal that which the description will change
     * @param newDescription the new description of the petal
     * @return Peal instance updated
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    public Petal updateDescription(Petal petal, String newDescription)
            throws NoEntityFoundException;

    /**
     * Method to update the origin of petal.
     * 
     * @param petal The petal that which the origin will change
     * @param newOrigin the new origin of the petal
     * @return Peal instance updated
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    public Petal updateOrigin(Petal petal, Origin newOrigin)
            throws NoEntityFoundException;

    /**
     * Method to delete an instance of petal.
     *
     * @param petal The petal to delete
     * @return Petal instance deleted or <code>null</code> if 
     * the petal does'nt exist.
     */
    Petal deletePetal(Petal petal); 

    /**
     * Method to give an access to a petal from a group.
     *
     * @param petal The petal which is to be set up access
     * @param group The group to which give an access to the petal
     *
     * @return  A petal with new groups that have access to it  
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Petal giveAccesToGroup(Petal petal, Group group)
            throws NoEntityFoundException;

    /**
     * Method to remove an access to a petal from a group.
     * 
     * @param petal The petal which is to be set up access
     * @param group The group you want to remove access to the petal
     * 
     * @return A petal with new groups that have access 
     * to it except the one removed 
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Petal removeAccesToGroup(Petal petal, Group group)
            throws NoEntityFoundException;

    /**
     * Method to add a category for the petal.
     * 
     * @param petal An instance of petal
     * @param category A category to set for the petal
     * 
     * @return A petal with the Category 'category'
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Petal addCategory(Petal petal, Category category)
            throws NoEntityFoundException;

    /**
     * Method to get a category of a petal.
     * 
     * @param petal An instance of petal
     * 
     * @return the category of the petal given in parameter
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Category getCategory(Petal petal)
            throws NoEntityFoundException;

    /**
     * Method to add a new capability for a petal.
     * 
     * @param petal The petal to which add a new capability
     * @param capability The capability to add for the petal
     * 
     * @return A new petal with the new capability
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Petal addCapability(Petal petal, Capability capability)
            throws NoEntityFoundException;

    /**
     *  Method to remove a capability of a petal
     * 
     * @param petal The petal to which remove a capability
     * @param capability The capability to remove
     * 
     * @return A new petal without the capability deleted
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Petal removeCapability(Petal petal, Capability capability) 
            throws NoEntityFoundException;

    /**
     * Method to add a new requirement for a petal
     * 
     * @param petal The petal to which add a new requirement
     * @param requirement The requirement to add for the petal
     * 
     * @return A new petal with the new requirement
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Petal addRequirement(Petal petal, Requirement requirement) 
            throws NoEntityFoundException;

    /**
     * Method to remove a requirement of a petal
     * 
     * @param petal The petal to which remove a requirement
     * @param requirement The requirement to remove
     * 
     * @return A new petal without the requirement deleted
     * @throws NoEntityFoundException if the petal doesn't exist
     */
    Petal removeRequirement(Petal petal, Requirement requirement) 
            throws NoEntityFoundException;

    /**
     * Method to collect all existing petals on database.
     * 
     * @return A collection of all petals
     */
    Collection<Petal> collectPetals();

    /**
     * Method to collect all the petals in the local repository 
     * 
     * @return A collection of petals coming from the local repository
     */
    Collection<Petal> collectPetalsFromLocal();

    /**
     * Method to collect all the petals in the staging repository 
     * 
     * @return A collection of petals coming from the staging repository
     */
    Collection<Petal> collectPetalsFromStaging();

    /**
     * Method to collect all the petals in the remote repository 
     * 
     * @return A collection of petals coming from the remote repository
     */
    Collection<Petal> collectPetalsFromRemote();

}
