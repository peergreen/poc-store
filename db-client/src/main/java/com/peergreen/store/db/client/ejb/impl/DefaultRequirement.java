package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ow2.easybeans.osgi.annotation.OSGiResource;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;
import com.peergreen.store.ldap.parser.ILdapParser;
import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


/**
 * Class defining a session to manage the entity Requirement
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

    @OSGiResource
    private ILdapParser ldapParser;
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a new requirement in the database.<br />
     * Throws EntityAlreadyExistException when requirement
     *  is already present in the database.
     *  
     * @param requirementName requirement name
     * @param filter requirement filter
     * @return created Requirement instance
     * @throws EntityAlreadyExistsException
     */
    @Override
    public Requirement addRequirement(String requirementName,String namespace,String filter) throws EntityAlreadyExistsException {
        Requirement requirement = findRequirement(requirementName);

        if (requirement != null) {
            throw new EntityAlreadyExistsException("The requirement " + requirementName + " already exists in database.");
        } else {
            requirement = new Requirement(requirementName,namespace,filter);
            entityManager.persist(requirement);
            return requirement;
        }
    }

    /**
     * Method to delete a requirement from the database
     * 
     * @param requirementName the requirement's name
     */
    @Override
    public void deleteRequirement(String requirementName) {
        Requirement req = findRequirement(requirementName);
        if (req != null) {
            entityManager.remove(req);
        }
    }

    /**
     * Method to find a requirement in the database.<br />
     * It returns null if the category doesn't exist. 
     * 
     * @param requirementName requirement's name
     * @return requirement with the name 'requirementName'
     */
    @Override
    public Requirement findRequirement(String requirementName) {
        Query q = entityManager.createNamedQuery("RequirementByName");
        q.setParameter("name", requirementName);
        Requirement result;
        try {
            result = (Requirement)q.getSingleResult();
        } catch(NoResultException e) {
            result = null;
        }
        return result;
    }

    /**
     * Method to collect the petals which have this requirement.
     * 
     * @param name requirement's name
     * @return collection of all the petals with this requirement
     * @throws NoEntityFoundException 
     */
    @Override
    public Collection<Petal> collectPetals(String requirementName) throws NoEntityFoundException {
        Requirement requirement = findRequirement(requirementName);
        if (requirement != null) {
            return requirement.getPetals();
        } else {
            throw new NoEntityFoundException("Cannot find this requirement on database.");
        }
    }

    /**
     * Method to add a petal to the list of petals which have this specific requirement.
     * 
     * @param requirement requirement that is needed by the petal
     * @param petal petal to add 
     * @return modified requirement (updated list of petals which share this requirement) 
     */
    @Override
    public Requirement addPetal(Requirement requirement, Petal petal) {
        Set<Petal> petals = requirement.getPetals();
        petals.add(petal);
        requirement.setPetals(petals);
        return entityManager.merge(requirement);
    }

    /**
     * Method to remove a petal from the list of petals which share this specific requirement.
     * 
     * @param requirement requirement needed by the petal
     * @param petal petal to remove
     * @return modified requirement (updated list of petals which share this requirement) 
     */
    @Override
    public Requirement removePetal(Requirement requirement, Petal petal) {
        Set<Petal> petals = requirement.getPetals();
        petals.remove(petal);
        requirement.setPetals(petals);
        return entityManager.merge(requirement);
    }

    /**
     * Method to collect all existing requirements in database.
     * 
     * @return collection containing all requirements existing in database
     */
    @Override
    public Collection<Requirement> collectRequirements() {
        Query reqs = entityManager.createNamedQuery("Requirement.findAll");
        @SuppressWarnings("unchecked")
        List<Requirement> reqList = reqs.getResultList();
        Set<Requirement> reqSet = new HashSet<Requirement>();
        reqSet.addAll(reqList);
        return reqSet;
    }

    /**
     * Method to modify a requirement's namespace.
     * 
     * @param requirement requirement to modify 
     * @param namespace new namespace 
     * @return updated Requirement
     */
    @Override
    public Requirement updateNamespace(Requirement requirement, String namespace) {
        requirement.setNamespace(namespace);
        return entityManager.merge(requirement);
    }

    /**
     * Method to modify a requirement's filter.
     * 
     * @param requirement requirement to modify
     * @param filter new filter
     * @return updated Requirement
     */
    @Override
    public Requirement updateFilter(Requirement requirement, String filter) {
        requirement.setFilter(filter);
        return entityManager.merge(requirement);
    }

    /**
     * Method to find matching between LDAP expression (Requirement filter) and Capabilities.
     * 
     * @param namespace request namespace
     * @param requirement requirement containing all constaints to resolve
     * @return collection of Capability that meets the given requirement
     * @see DefaultLdapParser
     */
    @Override
    public Collection<Capability> findCapabilities(String namespace, Requirement requirement) {
        String filter = requirement.getFilter();
        
        IValidatorNode<String> root = null;
        try {
            root = ldapParser.parse(filter);
            // asking for lazy generation of JPQL
            root.visit();
        } catch (InvalidLdapFormatException e) {
            e.printStackTrace();
        }
        
        Query query = entityManager.createNamedQuery("Requirement.findCapabilities");
        query.setParameter("namespace", namespace);
        if (root != null) {
            query.setParameter("req", root.getHandler().toQueryElement());
        } else {
            // TODO: exception if parsed tree is null?
        }
        @SuppressWarnings("unchecked")
        List<Capability> results = query.getResultList();
        
        Set<Capability> capabilities = new HashSet<Capability>();
        capabilities.addAll(results);
        return capabilities;
    }
}