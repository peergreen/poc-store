package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;

/**
 * Class defining an entity session to manage the entity Requirement
 * <ul>
 *      <li>Create a requirement on database</li>
 *      <li>Remove a requirement from the database</li>
 *      <li>Find a requirement from the database</li>
 *      <li>Collect all the petals that have the requirement</li>>
 *      <li>Add a petal to the list of petals of a requirement</li>
 *      <li>Remove a petal from the list of petals of a requirement</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultRequirement implements ISessionRequirement {

    @PersistenceContext
    private EntityManager entityManager = null;
    
    /**
     *  Method to add a new requirement in the database.
     *  
     * @param requirementName
     * @param filter
     * 
     * @return A new instance of requirement
     */
    @Override
    public Requirement addRequirement(String requirementName, String filter) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to delete a requirement in the database
     * 
     * @param requirementName the requirement's name
     */
    @Override
    public void deleteRequirement(String requirementName) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to find a requirement in the database
     * 
     * @param requirementName the requirement's name
     * @return the capacity with the name 'requirementName'
     */
    @Override
    public Requirement findRequirement(String requirementName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect the petals which have the requirement with the name 'requirementName'
     * 
     * @param name the requirement's name
     * @return A collection of all the petals which give this requirement
     */
    @Override
    public Collection<Petal> collectPetals(String requirementName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to add a petal to the list of petals which have the requirement
     * 
     * @param requirement the requirement that is needed for the petal
     * @param petal the petal to add 
     * 
     * @return A new requirement with a new list of petals 
     */
    @Override
    public Requirement addPetal(Requirement requirement, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to remove a petal to the list of petals which give the requirement
     * 
     * @param requirement the requirement that is needed for the petal
     * @param petal the petal to remove
     * 
     * @return A new requirement with a new list of petals 
     */
    @Override
    public Requirement removePetal(Requirement requirement, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }





}
