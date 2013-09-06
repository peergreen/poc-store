package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


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
 *      <li>Collect all existing petals 
 *      in the Local repository on database</li>
 *      <li>Collect all existing petals 
 *      in the Staging repository on database</li>
 *      <li>Collect all existing petals 
 *      in the Remote repository on database</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultSessionPetal implements ISessionPetal {

    private ISessionCapability sessionCapability;
    private ISessionCategory sessionCategory;
    private ISessionGroup sessionGroup;
    private ISessionRequirement sessionRequirement;
    private ISessionVendor sessionVendor;

    private static Log logger = LogFactory.
            getLog(DefaultSessionPetal.class);
    private EntityManager entityManager;

    /**
     * @param sessionCapability the sessionCapability to set
     */
    @EJB
    public void setSessionCapability(ISessionCapability sessionCapability) {
        this.sessionCapability = sessionCapability;
    }

    /**
     * @param sessionCategory the sessionCategory to set
     */
    @EJB
    public void setSessionCategory(ISessionCategory sessionCategory) {
        this.sessionCategory = sessionCategory;
    }

    /**
     * @param sessionGroup the sessionGroup to set
     */
    @EJB
    public void setSessionGroup(ISessionGroup sessionGroup) {
        this.sessionGroup = sessionGroup;
    }

    /**
     * @param sessionRequirement the sessionRequirement to set
     */
    @EJB
    public void setSessionRequirement(ISessionRequirement sessionRequirement) {
        this.sessionRequirement = sessionRequirement;
    }

    /**
     * @param sessionVendor the sessionVendor to set
     */
    @EJB
    public void setSessionVendor(ISessionVendor sessionVendor) {
        this.sessionVendor = sessionVendor;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a petal in the database.<br />
     * Group 'Administrator' must be
     *  created first (has access to all petals). <br />
     * Throws {@link NoEntityFoundException} if the group 'Administrator' 
     * doesn't exist
     *  or if a requirement or a capability cannot be found in database.<br />
     * Throws {@link EntityAlreadyExistsException} if the petal already exists.
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
    @Override
    public Petal addPetal(Vendor vendor, String artifactId, String version,
            String description, Category category,
            Set<Capability> capabilities, Set<Requirement> requirements,
            Origin origin) 
                    throws NoEntityFoundException, 
                    EntityAlreadyExistsException {

        Vendor v = sessionVendor.findVendor(vendor.getVendorName());
        if (v == null) {
            throw new NoEntityFoundException("Vendor " + vendor.getVendorName()
                    +  "doesn't exist in database.");}
        Category c = sessionCategory.findCategory(category.getCategoryName());
        if (c == null) {
            throw new NoEntityFoundException("The Category of petals " +
                    category.getCategoryName() +  "doesn't exist in database.");}

        Group group = sessionGroup.findGroup("Administrator");
        // group 'Administrator' must exists
        if (group == null) {
            throw new NoEntityFoundException("The group Administrator " +
                    "must be created first at all.");
        } else {
            Petal petal = findPetal(v, artifactId, version);
            if (petal != null) {
                String msg = "Petal " + artifactId + " provided by " 
                        + vendor.getVendorName() + " in version " 
                        + version + " is already present in database.";
                throw new EntityAlreadyExistsException(msg);
            } else {
                petal = new Petal(v, artifactId, version, c,
                        description, requirements, capabilities, origin);
                petal.getGroups().add(group);
                entityManager.persist(petal);

                sessionVendor.addPetal(v, petal);
                sessionCategory.addPetal(c, petal);
                sessionGroup.addPetal(group, petal);

                Iterator<Capability> it = capabilities.iterator();
                while(it.hasNext()) {
                    Capability cap = it.next();

                    Capability loopC = sessionCapability.
                            findCapability(cap.getCapabilityName(),
                                    cap.getVersion(), cap.getNamespace());
                    if (loopC != null) {
                        sessionCapability.addPetal(loopC, petal);
                    } else {
                        throw new NoEntityFoundException("Capability " + 
                                cap.getCapabilityName() + " doesn't exist in database.");
                    }
                }

                Iterator<Requirement> itreq = requirements.iterator();
                while(itreq.hasNext()) {
                    Requirement req = itreq.next();

                    Requirement loopR = sessionRequirement.
                            findRequirement(req.getRequirementName());
                    if (loopR != null) {
                        sessionRequirement.addPetal(loopR, petal);
                    } else {
                        throw new NoEntityFoundException("Requirement " 
                                + req.getRequirementName() + " doesn't exist in database.");
                    }
                }
            }

            return petal;
        }
    }

    /**
     * Method to find a petal.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * 
     * @return petal found in database with the attributes corresponding
     *  to given parameters, or {@literal null} if not found.
     */
    @Override
    public Petal findPetal(Vendor vendor, String artifactId, String version) {
        //The query to retrieve petal we are looking for
        Query q = entityManager.createNamedQuery("Petal.find");
        q.setParameter("vendor", vendor);
        q.setParameter("artifactId", artifactId);
        q.setParameter("version", version);

        try {
            return (Petal) q.getSingleResult();
        }  catch (NoResultException e) {
            //The query has no result
            return null ;
        }
    }

    /**
     * Method to collect the groups that have access to a petal 
     * 
     * @param petal The petal to which collect the groups that have access to it
     * 
     * @return A collection of groups that have access to the petal
     * @throws NoEntityFoundException
     */
    @Override
    public Collection<Group> collectGroups(Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached petal
        Set<Group> groups = new HashSet<>();
        Petal p = findPetal(petal.getVendor(), 
                petal.getArtifactId(), petal.getVersion());
        if( p!=null){
            groups.addAll(p.getGroups());
            return groups;
        } else {
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName() +
                    " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to collect the capabilities of a petal 
     * 
     * @param petal The petal to which collect the capabilities
     * 
     * @return A collection of capabilities of the petal 
     * @throws NoEntityFoundException
     */
    @Override
    public Collection<Capability> collectCapabilities(Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached petal
        Set<Capability> caps = new HashSet<>(); 
        Petal p = findPetal(petal.getVendor(), petal.getArtifactId(), petal.getVersion());
        if(p!=null){
            caps.addAll(p.getCapabilities());
            return caps;
        }else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName() +
                    " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    } 

    /**
     * Method to collect the requirements of a petal
     * 
     * @param petal The petal to which collect the requirements
     * 
     * @return A collection of the requirements of the petal 
     * @throws NoEntityFoundException
     */
    @Override
    public Collection<Requirement> collectRequirements(Petal petal)
            throws NoEntityFoundException{
        // retrieve attached petal
        Set<Requirement> reqs = new HashSet<>(); 
        Petal p = findPetal(petal.getVendor(), petal.getArtifactId(),
                petal.getVersion());
        if(p!=null){
            reqs.addAll(p.getRequirements());
            return reqs;
        }else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName() +
                    " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to delete an instance of petal from the database
     * and all information related to it. 
     * If the petal to delete doesn't exist, we return {@literal null}.<br />
     * @param petal The petal to delete
     * 
     * @return Petal instance deleted or 
     * <em>null</em> if petaldoesn't exist    
     */
    @Override
    public Petal deletePetal(Petal petal) {
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        Vendor v = sessionVendor.findVendor(petal.getVendor().getVendorName());
        Category c = sessionCategory.
                findCategory(petal.getCategory().getCategoryName());

        if (p != null) {
            try {
                //Make that the vendor does not provide the petal anymore
                sessionVendor.removePetal(v, p);
                //Remove the petal from the category to which he belonged
                sessionCategory.removePetal(c, p);

            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }

            Set<Capability> capabilities = p.getCapabilities();
            Iterator<Capability> it = capabilities .iterator();
            while(it.hasNext()) {
                Capability cap = it.next();
                try {
                    it.remove();
                    sessionCapability.removePetal(cap, p);
                } catch (NoEntityFoundException e) {
                    logger.warn(e.getMessage(), e);

                }
            }

            Set<Requirement> requirements =p.getRequirements();
            Iterator<Requirement> itreq = requirements .iterator();
            while(itreq.hasNext()) {
                Requirement req = itreq.next();
                try {
                    itreq.remove();
                    sessionRequirement.removePetal(req, p);
                } catch (NoEntityFoundException e) {
                    logger.warn(e.getMessage(), e);
                }
            }

            Collection<Group> groups;
            try {
                groups = collectGroups(p);
                Iterator<Group> itgrp = groups.iterator();
                while (itgrp.hasNext()) {
                    Group group = itgrp.next();
                    itgrp.remove();
                    sessionGroup.removePetal(group,p);
                } 
            }
            catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);

            }

            entityManager.remove(p);
            return p; 
        }
        else{
            return p; 
        }
    }

    /**
     * Method to update the description of petal
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal that which the description will change
     * @param newDescription the new description of the petal
     * @return Petal instance updated 
     */
    @Override
    public Petal updateDescription(Petal petal, String newDescription)
            throws NoEntityFoundException{
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(), petal.getArtifactId(),
                petal.getVersion());
        if(p!=null){
            p.setDescription(newDescription);
            return entityManager.merge(p);
        }
        else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName() +
                    " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }
    /**
     * Method to update the origin of petal
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal that which the origin will change
     * @param newOrigin the new origin of the petal
     * @throws NoEntityFoundException
     * @return Petal instance updated 
     */
    @Override
    public Petal updateOrigin(Petal petal, Origin newOrigin)
            throws NoEntityFoundException{
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        if(p!=null){
            p.setOrigin(newOrigin);
            return entityManager.merge(p);

        }
        else{
            throw new NoEntityFoundException("Petal " +
                    petal.getArtifactId() + " provided by "
                    + petal.getVendor().getVendorName() +
                    " in version " +
                    petal.getVersion() + " does not exist in database.");
        }
    }

    /**
     * Method to give an access to a petal from a group
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal which is to be set up access
     * @param group The group you want to give an access to the petal
     * 
     * @return  A petal with new groups that have access to it 
     * @throws NoEntityFoundException
     */
    @Override
    public Petal giveAccesToGroup(Petal petal, Group group)
            throws NoEntityFoundException {
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        if(p!=null){
            // retrieve attached group
            Group g = sessionGroup.findGroup(group.getGroupname());
            try {
                sessionGroup.addPetal(g, p);
                p.getGroups().add(g);
            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }

            return entityManager.merge(p);
        }
        else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName() + 
                    " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }
    /**
     * Method to remove an access to a petal from a group
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal which is to be set up access
     * @param group The group you want to remove access to the petal
     * 
     * @return A petal with new groups that have access 
     * to it except the one removed 
     * @throws NoEntityFoundException
     */
    @Override
    public Petal removeAccesToGroup(Petal petal, Group group)
            throws NoEntityFoundException {
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        if(p!= null){
            // retrieve attached category
            Group g = sessionGroup.findGroup(group.getGroupname());
            p.getGroups().remove(g);
            return entityManager.merge(p);
        }
        else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName()
                    + " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to add a category for the petal 
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal An instance of petal
     * @param category A category to set for the petal
     * 
     * @return A petal with the Category 'category'
     * @throws NoEntityFoundException
     */
    @Override
    public Petal addCategory(Petal petal, Category category) 
            throws NoEntityFoundException {
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        if(p!=null){
            // retrieve attached category
            Category c = sessionCategory.
                    findCategory(category.getCategoryName());
            try {
                sessionCategory.addPetal(c, p);
                p.setCategory(c);
            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }

            return entityManager.merge(p);
        }
        else{
            throw new NoEntityFoundException("Petal "
                    + petal.getArtifactId() + " provided by "
                    + petal.getVendor().getVendorName()
                    + " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to get a category of a petal
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal An instance of petal
     * 
     * @return the category of the petal given in parameter
     * @throws NoEntityFoundException
     */
    @Override
    public Category getCategory(Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        if(p!=null){
            return p.getCategory();
        }
        else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName()
                    + " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to add a new capability for a petal
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal to which add a new capability
     * @param capability The capability to add for the petal
     * 
     * @return A new petal with the new capability
     * @throws NoEntityFoundException
     */
    @Override
    public Petal addCapability(Petal petal,
            Capability capability)throws NoEntityFoundException {
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion()); 
        if(p!=null){
            // retrieve attached capability
            Capability c = sessionCapability.
                    findCapability(capability.getCapabilityName(),
                            capability.getVersion(), capability.getNamespace());
            try {
                sessionCapability.addPetal(c, p);
                p.getCapabilities().add(c);
            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }

            return entityManager.merge(p);
        }
        else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName()
                    + " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     *  Method to remove a capability of a petal
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal to which remove a capability
     * @param capability The capability to remove
     * 
     * @return A new petal without the capability deleted
     * @throws NoEntityFoundException
     */
    @Override
    public Petal removeCapability(Petal petal, Capability capability) 
            throws NoEntityFoundException {
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(), petal.getArtifactId(),
                petal.getVersion());
        if(p!=null){
            // retrieve attached capability
            Capability c = sessionCapability.
                    findCapability(capability.getCapabilityName(),
                            capability.getVersion(), capability.getNamespace());

            p.getCapabilities().remove(c);
            try {
                sessionCapability.removePetal(c, p);
            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }
            return  entityManager.merge(petal);
        }
        else{
            throw new NoEntityFoundException("Petal "
                    + petal.getArtifactId() + " provided by "
                    + petal.getVendor().getVendorName()
                    + " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to add a new requirement for a petal
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal to which add a new requirement
     * @param requirement The requirement to add for the petal
     * 
     * @return A new petal with the new requirement
     * @throws NoEntityFoundException
     */
    @Override
    public Petal addRequirement(Petal petal,
            Requirement requirement) throws NoEntityFoundException{
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        if(p!=null){
            // retrieve attached requirement
            Requirement r = sessionRequirement.
                    findRequirement(requirement.getRequirementName());

            try{
                sessionRequirement.addPetal(r, p);
                p.getRequirements().add(r);
            }catch(NoEntityFoundException e){
                logger.warn(e.getMessage(), e);
            }
            return entityManager.merge(petal);
        }
        else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName()
                    + " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to remove a requirement of a petal
     * Throws {@link NoEntityFoundException} when the petal doesn't exist.
     * @param petal The petal to which remove a requirement
     * @param requirement The requirement to remove
     * 
     * @return A new petal without the requirement deleted
     * @throws NoEntityFoundException
     */
    @Override
    public Petal removeRequirement(Petal petal,
            Requirement requirement) throws NoEntityFoundException{
        // retrieve attached petal
        Petal p = findPetal(petal.getVendor(),
                petal.getArtifactId(), petal.getVersion());
        if(p!=null){
            // retrieve attached requirement
            Requirement r = sessionRequirement.
                    findRequirement(requirement.getRequirementName());

            p.getRequirements().remove(r);
            try{
                sessionRequirement.removePetal(r, p);
            }catch(NoEntityFoundException e){
                logger.warn(e.getMessage(), e);
            }

            return entityManager.merge(p);} 
        else{
            throw new NoEntityFoundException("Petal " + petal.getArtifactId()
                    + " provided by " + petal.getVendor().getVendorName()
                    + " in version " + petal.getVersion()
                    + " does not exist in database.");
        }
    }

    /**
     * Method to collect all existing petals on database.
     * 
     * @return petals list
     */
    @Override
    public Collection<Petal> collectPetals() {
        Query petals = entityManager.createNamedQuery("Petal.findAll");
        @SuppressWarnings("unchecked")
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
        Query petals = entityManager.createQuery("select p " +
                "from Petal p where p.origin = :origin");
        petals.setParameter("origin", Origin.LOCAL);

        @SuppressWarnings("unchecked")
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
        Query petals = entityManager.createQuery("select p " +
                "from Petal p where p.origin = :origin");
        petals.setParameter("origin", Origin.STAGING);

        @SuppressWarnings("unchecked")
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
        Query petals = entityManager.createQuery("select p " +
                "from Petal p where p.origin = :origin");
        petals.setParameter("origin", Origin.REMOTE);

        @SuppressWarnings("unchecked")
        List<Petal> petalsList = petals.getResultList();
        Set<Petal> petalSet = new HashSet<Petal>();
        petalSet.addAll(petalsList);

        return petalSet;
    }

    /**
     * Method to find a petal using his id.
     * 
     * @param id petal's id
     * @return The petal corresponding to the id given in parameter 
     * or <code>null</code>if it doesn't exist
     */
    @Override
    public Petal findPetalById(int id) {
        //The query to retrieve petal we are looking for
        Query q = entityManager.createNamedQuery("Petal.findById");
        q.setParameter("pid", id);

        try {
            return (Petal) q.getSingleResult();
        }  catch (NoResultException e) {
            //The query has no result
            return null ;
        }

    }

}