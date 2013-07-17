package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


public interface ISessionRequirement {

    /**
     * Method to add a new requirement in the database.<br />
     * Throws EntityAlreadyExistException when  requirement
     *  already present in the database.
     *  
     * @param requirementName requirement name
     * @param filter requirement filter
     * @return created Requirement instance
     * @throws EntityAlreadyExistsException
     */
    Requirement addRequirement(String requirementName, String namespace, String filter) throws EntityAlreadyExistsException;

    /**
     * Method to delete a requirement in the database
     * 
     * @param requirementName the requirement's name
     */
    void deleteRequirement (String requirementName );

    /**
     * Method to find a requirement in the database
     * 
     * @param requirementName the requirement's name
     * @return the requirement with the name "nameRequirement"
     */
    Requirement findRequirement (String requirementName);

    /**
     * Method to collect the petals which have this requirement.
     * 
     * @param name requirement's name
     * @return collection of all the petals with this requirement
     * @throws NoEntityFoundException 
     */
    Collection<Petal> collectPetals(String requirementName) throws NoEntityFoundException;

    /**
     * Method to add a petal to the list of petals which have this specific requirement.
     * 
     * @param requirement requirement that is needed by the petal
     * @param petal petal to add 
     * @return modified requirement (updated list of petals which share this requirement) 
     */
    Requirement addPetal(Requirement requirement,Petal petal);

    /**
     * Method to remove a petal from the list of petals which share this specific requirement.
     * 
     * @param requirement requirement needed by the petal
     * @param petal petal to remove
     * @return modified requirement (updated list of petals which share this requirement) 
     */
    Requirement removePetal(Requirement requirement, Petal petal);

    /**
     * Method to collect all existing requirements in database.
     * 
     * @return collection containing all requirements existing in database
     */
    Collection<Requirement> collectRequirements();

    /**
     * Method to modify a requirement's namespace.
     * 
     * @param requirement requirement to modify
     * @param namespace new namespace
     * @return updated Requirement
     */
    Requirement updateNamespace(Requirement requirement, String namespace);

    /**
     * Method to modify a requirement's filter.
     * 
     * @param requirement requirement to modify
     * @param filter new filter
     * @return updated Requirement
     */
    Requirement updateFilter(Requirement requirement, String filter);
    
    /**
     * Method to find matching between LDAP expression (Requirement filter) and Capabilities.
     * 
     * @param namespace request namespace
     * @param requirement requirement containing all constaints to resolve
     * @return collection of Capability that meets the given requirement
     * @see DefaultLdapParser
     */
    Collection<Capability> findCapabilities(String namespace, Requirement requirement);
}