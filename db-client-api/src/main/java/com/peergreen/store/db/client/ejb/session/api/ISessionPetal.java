package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.enumeration.Origin;

public interface ISessionPetal {

    /**
     * Method to create an instance of a petal and add it in the database
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
    Petal addPetal(Vendor vendor, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<Requirement> requirements, Origin origin);


    /**
     * Method to find a petal 
     * 
     * @param vendor the petal's vendor
     * @param artifactId the petal's artifactId!
     * @param version the petal's version
     * 
     * @return The petal with the attributes given in parameters
     */
    Petal findPetal(Vendor vendor, String artifactId,String version);

    /**
     * Method to collect the groups that have access to a petal 
     * 
     * @param petal The petal to which collect the groups that have access to it
     * 
     * @return A collection of groups that have access to the petal
     */
    Collection<Group> collectGroups(Petal petal); 

    /**
     * Method to collect the capabilities of a petal 
     * 
     * @param petal The petal to which collect the capabilities
     * 
     * @return A collection of capabilities of the petal 
     */
    Collection<Capability> collectCapabilities(Petal petal); 

    /**
     * Method to collect the requirements of a petal
     * 
     * @param petal The petal to which collect the requirements
     * 
     * @return A collection of the requirements of the petal 
     */
    Collection<Requirement> collectRequirements(Petal petal); 

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
    Petal updatePetal(Petal oldpetal,Vendor vendor, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<Requirement> requirements);

    /**
     * Method to delete an instance of petal
     * 
     * @param petal The petal to delete
     */
    void deletePetal(Petal petal); 

    /**
     * Method to give an access to a petal from a group
     * 
     * @param petal The petal which is to be set up access
     * @param group The group you want to give an access to the petal
     * 
     * @return  A petal with new groups that have access to it  
     */
    Petal giveAccesToGroup(Petal petal, Group group);

    /**
     * Method to remove an access to a petal from a group
     * 
     * @param petal The petal which is to be set up access
     * @param group The group you want to remove access to the petal
     * 
     * @return A petal with new groups that have access to it except the one removed 
     */
    Petal removeAccesToGroup(Petal petal, Group group);

    /**
     * Method to add a category for the petal 
     * 
     * @param petal An instance of petal
     * @param category A category to set for the petal
     * 
     * @return A petal with the Category 'category'
     */
    Petal addCategory(Petal petal, Category category);

    /**
     * Method to get a category of a petal
     * 
     * @param petal An instance of petal
     * 
     * @return the category of the petal given in parameter
     */
    Category getCategory(Petal petal);

    /**
     * Method to add a new capability for a petal
     * 
     * @param petal The petal to which add a new capability
     * @param capability The capability to add for the petal
     * 
     * @return A new petal with the new capability
     */
    Petal addCapability(Petal petal, Capability capability);

    /**
     *  Method to remove a capability of a petal
     * 
     * @param petal The petal to which remove a capability
     * @param capability The capability to remove
     * 
     * @return A new petal without the capability deleted
     */
    Petal removeCapability(Petal petal, Capability capability);

    /**
     * Method to add a new requirement for a petal
     * 
     * @param petal The petal to which add a new requirement
     * @param requirement The requirement to add for the petal
     * 
     * @return A new petal with the new requirement
     */
    Petal addRequirement(Petal petal, Requirement requirement);

    /**
     * Method to remove a requirement of a petal
     * 
     * @param petal The petal to which remove a requirement
     * @param requirement The requirement to remove
     * 
     * @return A new petal without the requirement deleted
     */
    Petal removeRequirement(Petal petal, Requirement requirement);

    /**
     * Method to collect all existing petals on database.
     * 
     * @return petals list
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
    
    /**
     * Method to retrieve petal's origin.
     * 
     * @param petal to which retrieve origin
     * @return petal's origin {LOCAL, STAGING, REMOTE}
     */
    Origin getOrigin(Petal petal);
    
    /**
     * Method to set petal's origin.
     * 
     * @param petal to which set origin
     * @param origin petal's origin {LOCAL, STAGING, REMOTE}
     */
    void setOrigin(Petal petal, Origin origin);
    
}
