package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.User;
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


    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *  Method to add a new requirement in the database.
     *  It throws an exception "EntityExistsException " when 
     *  the entity already exist in the database
     *  
     * @param requirementName
     * @param filter
     * 
     * @return A new instance of requirement
     */
    @Override
    public Requirement addRequirement(String requirementName,String namespace,String filter)throws EntityExistsException {
        Requirement requirement = findRequirement(requirementName);

        if(requirement != null){
            throw new EntityExistsException();
        }
        else {
            requirement = new Requirement(requirementName,namespace,filter);
            entityManager.persist(requirement);
            return requirement;
        }

    }

    /**
     * Method to delete a requirement in the database
     * It throws an IllegalArgumentException if the entity to remove
     * doesn't exist in the database.
     * 
     * @param requirementName the requirement's name
     */
    @Override
    public void deleteRequirement(String requirementName)throws IllegalArgumentException {
        Requirement temp = findRequirement(requirementName);
        if(temp != null){
            entityManager.remove(temp);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to find a requirement in the database.
     * It returns null if the category doesn't exist. 
     * 
     * @param requirementName the requirement's name
     * @return the capacity with the name 'requirementName'
     */
    @Override
    public Requirement findRequirement(String requirementName) throws NoResultException{
        Query q = entityManager.createNamedQuery("RequirementByName");
        q.setParameter("name", requirementName);
        Requirement result;
        try{
            result = (Requirement)q.getSingleResult();}
        catch(NoResultException e){
            result = null;
        }
        return result;
    }

    /**
     * Method to collect the petals which have the requirement with the name 'requirementName'
     * It throws an IllegalArgumentException if the entity to remove
     * doesn't exist in the database.
     * 
     * @param name the requirement's name
     * @return A collection of all the petals which give this requirement
     */
    @Override
    public Collection<Petal> collectPetals(String requirementName) throws IllegalArgumentException{

        Requirement requirement = findRequirement(requirementName);
        if(requirement != null){
            return requirement.getPetals();
        }
        else {
            throw new IllegalArgumentException();
        }
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

        Set<Petal> petals = requirement.getPetals();
        petals.add(petal);
        requirement.setPetals(petals);
        requirement =  entityManager.merge(requirement);
        return requirement;
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

        Set<Petal> petals = requirement.getPetals();
        petals.remove(petal);
        requirement.setPetals(petals);
        requirement = entityManager.merge(requirement);
        return requirement;
    }

    @Override
    public Collection<Requirement> collectRequirements() {

        Query reqs = entityManager.createNamedQuery("Requirement.findAll");
        List<Requirement> reqList = reqs.getResultList();
        Set<Requirement> reqSet = new HashSet<Requirement>();
        reqSet.addAll(reqList);
        return reqSet;
    }

    @Override
    public Requirement updateNamespace(Requirement requirement, String namespace) {

        requirement.setNamespace(namespace);

        return entityManager.merge(requirement);
    }

    @Override
    public Requirement updateFilter(Requirement requirement, String filter) {

        requirement.setFilter(filter);
        return entityManager.merge(requirement);
    }

    @Override
    public Collection<Capability> findCapabilities(Requirement requirement) {
        // TODO Auto-generated method stub
        return null;
    }





}
