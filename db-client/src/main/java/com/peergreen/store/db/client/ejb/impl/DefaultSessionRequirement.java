package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ow2.easybeans.osgi.annotation.OSGiResource;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;
import com.peergreen.store.db.client.ldap.handler.client.jpql.impl.JPQLClientBinaryNode;
import com.peergreen.store.db.client.ldap.handler.client.jpql.impl.JPQLClientNaryNode;
import com.peergreen.store.db.client.ldap.handler.client.jpql.impl.JPQLClientUnaryNode;
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
public class DefaultSessionRequirement implements ISessionRequirement {
    private EntityManager entityManager;

    @OSGiResource
    private ILdapParser ldapParser;
    private CriteriaBuilder builder;
    private CriteriaQuery<Capability> mainQuery;
    private JPQLClientBinaryNode jpqlClientBinaryNode;
    private JPQLClientNaryNode jpqlClientNaryNode;
    private JPQLClientUnaryNode jpqlClientUnaryNode;
    private ISessionPetal petalSession;
    private static Logger theLogger = Logger.getLogger(DefaultSessionPetal.class.getName());

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initHandlers() {
        builder = entityManager.getCriteriaBuilder();
        mainQuery = builder.createQuery(Capability.class);
        // set property in ldap parser to keep trace of main query
        ldapParser.setProperty(CriteriaQuery.class, mainQuery);
        
        jpqlClientBinaryNode = new JPQLClientBinaryNode();
        jpqlClientBinaryNode.setEntityManager(entityManager);
        ldapParser.register(jpqlClientBinaryNode);
        
        jpqlClientNaryNode = new JPQLClientNaryNode();
        jpqlClientNaryNode.setEntityManager(entityManager);
        ldapParser.register(jpqlClientNaryNode);
        
        jpqlClientUnaryNode = new JPQLClientUnaryNode();
        jpqlClientUnaryNode.setEntityManager(entityManager);
        ldapParser.register(jpqlClientUnaryNode);
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
    public Requirement addRequirement(String requirementName, String namespace, String filter) throws EntityAlreadyExistsException {
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
        // retrieve attached requirement
        Requirement req = findRequirement(requirementName);
        if(req != null){
            try {
                //Collect all the petals which have this requirement
                Collection<Petal> petals = req.getPetals();
                Iterator<Petal> it = petals.iterator();

                //Remove the requirement from each petal's requirements
                while(it.hasNext()) {
                    Petal p = it.next();
                    petalSession.removeRequirement(p, req);
                }
                //Then remove the requirement from the database 
                entityManager.remove(req);

            } catch (NoEntityFoundException e) {
                theLogger.log(Level.SEVERE,e.getMessage());
            }
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
            throw new NoEntityFoundException("Requirement " + requirementName + " doesn't exist in database.");
        }
    }

    /**
     * Method to add a petal to the list of petals which have this specific requirement.
     * 
     * @param requirement requirement that is needed by the petal
     * @param petal petal to add 
     * @return modified requirement (updated list of petals which share this requirement) 
     * @throws NoEntityFoundException
     */
    @Override
    public Requirement addPetal(Requirement requirement, Petal petal) throws NoEntityFoundException {
        // retrieve attached requirement
        Requirement r = findRequirement(requirement.getRequirementName());
        if(r!=null){
            // retrieve attached petal
            Petal p = petalSession.findPetal(petal.getVendor(), petal.getArtifactId(), petal.getVersion());

            r.getPetals().add(p);
            return entityManager.merge(r);
        }
        else{
            throw new NoEntityFoundException("Requirement " + requirement.getRequirementName() + " doesn't exist in database.");
        }
    }
    /**
     * Method to remove a petal from the list of petals which share this specific requirement.
     * 
     * @param requirement requirement needed by the petal
     * @param petal petal to remove
     * @return modified requirement (updated list of petals which share this requirement) 
     * @throws NoEntityFoundException
     */
    @Override
    public Requirement removePetal(Requirement requirement, Petal petal)throws NoEntityFoundException {
        // retrieve attached requirement
        Requirement r = findRequirement(requirement.getRequirementName());
        if(r!=null){
            // retrieve attached petal
            Petal p = petalSession.findPetal(petal.getVendor(), petal.getArtifactId(), petal.getVersion());
            r.getPetals().remove(p);
            return entityManager.merge(r);
        }
        else{
            throw new NoEntityFoundException("Requirement " + requirement.getRequirementName() + " doesn't exist in database.");
        }
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
     * @throws NoEntityFoundException
     */
    @Override
    public Requirement updateNamespace(Requirement requirement, String namespace)throws NoEntityFoundException {
        // retrieve attached requirement
        Requirement r = findRequirement(requirement.getRequirementName());
        if(r != null){
            r.setNamespace(namespace);
            return entityManager.merge(r);
        } else {
            throw new NoEntityFoundException("Requirement " + requirement.getRequirementName() + " doesn't exist in database.");
        }
    }

    /**
     * Method to modify a requirement's filter.
     * 
     * @param requirement requirement to modify
     * @param filter new filter
     * @return updated Requirement
     * @throws NoEntityFoundException
     */
    @Override
    public Requirement updateFilter(Requirement requirement, String filter) throws NoEntityFoundException {
        // retrieve attached requirement
        Requirement r = findRequirement(requirement.getRequirementName());
        
        if (r != null) {
            r.setFilter(filter);
            return entityManager.merge(r);
        } else {
            throw new NoEntityFoundException("Requirement " + requirement.getRequirementName() + " doesn't exist in database.");
        }
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
    public Collection<Capability> findCapabilities(Requirement requirement) {
        // retrieve attached requirement
        Requirement r = findRequirement(requirement.getRequirementName());

        // set namespace as LDAP parser property
        ldapParser.setProperty(String.class, r.getNamespace());

        String filter = r.getFilter();

        IValidatorNode<String> root = null;
        try {
            root = ldapParser.parse(filter);
        } catch (InvalidLdapFormatException e) {
            theLogger.log(Level.SEVERE,e.getMessage());
        }

        if (root != null) {
            // query has been generated during tree generation, just ask for execution
            Root<Capability> rootFrom = mainQuery.from(Capability.class);
            mainQuery.select(rootFrom);
            Collection<Capability> caps = entityManager.createQuery(mainQuery).getResultList();
            return caps;
        } else {
            // TODO: exception if parsed tree is null?
        }

        Set<Capability> capabilities = new HashSet<Capability>();
        return capabilities;
    }

    @EJB
    public void setPetalSession(ISessionPetal petalSession) {
        this.petalSession = petalSession;
    }
}