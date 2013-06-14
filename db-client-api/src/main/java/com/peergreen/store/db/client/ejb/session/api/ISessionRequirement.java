package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;

public interface ISessionRequirement {

    /**
     *  Method to add a new requirement in the database.
     *  
     * @param requirementName
     * @param filter
     * @param namespace
     * 
     * @return A new instance of requirement
     */
    Requirement addRequirement(String requirementName,String namespace,String filter);

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
     * Method to collect the petals which have the requirement with the name 'requirementName'
     * 
     * @param requirementName the requirement's name
     * @return A collection of all the petals which have this requirement
     */
    Collection<Petal> collectPetals(String requirementName);

    /**
     * Method to add a petal to the list of petals which have the requirement
     * 
     * @param requirement the requirement that is needed for the petal
     * @param petal the petal to add 
     * 
     * @return A new requirement with a new list of petals 
     */
    Requirement addPetal(Requirement requirement,Petal petal);

    /**
     * Method to remove a petal to the list of petals which have the requirement
     * 
     * @param requirement the requirement that is needed for the petal
     * @param petal the petal to remove
     * 
     * @return A new requirement with a new list of petals 
     */
    Requirement removePetal(Requirement requirement, Petal petal);
    
    /**
     * Method to collect all the requirement in the database
     * 
     * @return A collection of requirements in the database
     */
    Collection<Requirement> collectRequirements();
    
    Requirement updateNamespace(Requirement requirement, String nameSpace);
    
    Requirement updateFilter(Requirement requirement, String filter);

}