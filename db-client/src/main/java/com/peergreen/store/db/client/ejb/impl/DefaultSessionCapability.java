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
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionProperty;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


/**
 * Class defining an entity session to manage the entity Capability
 * <ul>
 *      <li>Create a capability on database</li>
 *      <li>Remove a capability from the database</li>
 *      <li>Find a capability from the database</li>
 *      <li>Collect all the petals that have the capability</li>>
 *      <li>Add a petal to the list of petals of a capability</li>
 *      <li>Remove a petal from the list of petals of a capability</li>
 *      <li>Collect all existing capabilities on database</li>
 *      <li>Modify existing capability changing his namespace</li>
 *      <li>Modify existing capability changing his properties</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultSessionCapability implements ISessionCapability{

    private EntityManager entityManager;
    private ISessionPetal petalSession;
    private ISessionProperty propertySession;

    private static Log logger = LogFactory.
            getLog(DefaultSessionCapability.class);

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Set entity session which manage the entity Petal
     * @param sessionPetal Instance of sessionPetal 
     */
    @EJB
    public void setSessionPetal(ISessionPetal sessionPetal) {
        this.petalSession = sessionPetal;
    }

    /**
     * Set entity session which manage the entity Property
     * @param propertySession Instance of propertySession
     */
    @EJB
    public void setSessionProperty(ISessionProperty propertySession) {
        this.propertySession = propertySession;
    }

    /**
     * Method to add a new capability in the database.
     * Throws {@literal EntityAlreadyExistsException}
     * if capability already exists.
     * 
     * @param capabilityName the capability name
     * @param version capability version
     * @param namespace capability namespace
     * @param properties capability properties
     * 
     * @return created Capability instance
     */
    @Override
    public Capability addCapability(String capabilityName, String version,
            String namespace, Set<Property> properties)
                    throws EntityAlreadyExistsException {

        //Check if the capability to add already exists
        Capability temp = findCapability(capabilityName, version, namespace);
        if (temp != null) {
            throw new EntityAlreadyExistsException("Capability " 
                    + capabilityName + " in version " + version 
                    + " already exists on database.");
        } else {
            Set<Property> props = null;
            if (properties != null) {
                props = new HashSet<>(properties);
            } else {
                props = new HashSet<>();
            }

            // add properties
            props.add(propertySession.
                    createProperty("capabilityName", capabilityName));
            props.add(propertySession.
                    createProperty("version", version));
            props.add(propertySession.
                    createProperty("namespace", namespace));

            Capability capability = new Capability(capabilityName, 
                    version, namespace, props);
            entityManager.persist(capability); 

            Iterator<Property> it = props.iterator();
            while(it.hasNext()) {
                Property p = it.next();
                try {
                    propertySession.setCapability(p.getId(), capability);
                } catch (NoEntityFoundException e) {
                    e.printStackTrace();
                }
            }
            entityManager.merge(capability); 

            return capability;
        }
    }

    /**
     * Method to delete a capability in the database. <br />
     * If the capability to delete doesn't exist,
     * we return {@literal null}.<br />
     * 
     * @param capabilityName capability name
     * @param version capability version 
     * 
     * @return Capability instance deleted or 
     * <em>null</em> if capability can't be deleted
     */
    @Override
    public Capability deleteCapability(String capabilityName,
            String version, String namespace) {
        // retrieve attached capability 
        Capability cap = findCapability(capabilityName, version, namespace);
        if(cap!=null){
            try {
                //Retrieve petals which provided the capability to delete
                Collection<Petal> petals = cap.getPetals();
                Iterator<Petal> it = petals.iterator();

                //Remove the capability from each petal's capability 
                while(it.hasNext()) {
                    Petal p = it.next();
                    petalSession.removeCapability(p, cap);
                }
                //Then remove the capability from the database
                entityManager.remove(cap);
            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }
            return cap; 
        }else{
            return cap; 
        }
    }

    /**
     * Method to find a capability in the database. <br />
     * If the capability to find doesn't exist, we return {@literal null}.
     * 
     * @param capabilityName the capability's name
     * @return retrieved Capability instance or 
     * {@literal null} if no matching instance found
     */
    @Override
    public Capability findCapability(String capabilityName,
            String version, String namespace) {
        //The query to retrieve the capability we are looking for 
        Query q = entityManager.createNamedQuery("CapabilityByName");
        q.setParameter("name", capabilityName);
        q.setParameter("version", version);
        q.setParameter("namespace", namespace);

        try { 
            //return the only result for the query 
            return (Capability)q.getSingleResult();
        } catch (NoResultException e) {
            //The query has no result 
            return null ; 
        }
    }

    /**
     * Method to change capability properties.
     * 
     * @param capability capability to modify 
     * @param properties new properties for the capability 
     * @return modified Capability instance
     * @throws NoEntityFoundException 
     */
    @Override
    public Capability updateProperties(Capability capability, 
            Set<Property> properties) throws NoEntityFoundException {
        // retrieve attached capability entity
        Capability c = findCapability(capability.getCapabilityName(), 
                capability.getVersion(), capability.getNamespace());
        if(c != null){
            c.setProperties(properties);
            return entityManager.merge(c);
        } else {
            throw new NoEntityFoundException("Capability " + 
                    capability.getCapabilityName() + " in version " 
                    + capability.getVersion() + " is not present on database.");
        }
    }

    /**
     * Method to collect all properties associated with a capability.<br />
     * Throws {@link NoEntityFoundException} 
     * if the capability cannot be found in database.
     *
     * @param capabilityName capability name
     * @param version capability version
     * @param namespace capability namespace
     * @return associated properties
     * @throws NoEntityFoundException
     */
    public Collection<Property> collectProperties(String capabilityName, 
            String version, String namespace) throws NoEntityFoundException {
        Capability capability = findCapability(capabilityName, version, 
                namespace);
        if (capability != null) {
            return new HashSet<Property>(capability.getProperties());
        } else {
            throw new NoEntityFoundException("Capability " + capabilityName 
                    + " in version " + version 
                    + " is not present on database.");
        }
    }

    /**
     * Method to add a property to the list of properties 
     * associated with this capability.<br />
     * Throws {@link NoEntityFoundException} 
     * if the capability cannot be found in database.
     * 
     * @param capabilityName capability name
     * @param version capability version
     * @param namespace capability namespace
     * @param property property to associate
     * @return updated capability
     * @throws NoEntityFoundException
     */
    public Capability addProperty(String capabilityName, String version, 
            String namespace, Property property) throws NoEntityFoundException {
        // retrieve attached capability entity
        Capability c = findCapability(capabilityName, version, namespace);

        if (c != null) {
            // retrieve attached petal entity
            Property p = propertySession.find(property.getId());
            p.setCapability(c);

            Set<Property> properties = c.getProperties();
            properties.add(p);
            c.setProperties(properties);

            return entityManager.merge(c); 
        } else {
            throw new NoEntityFoundException("Capability " + capabilityName 
                    + " in version " + version 
                    + " is not present on database.");
        }
    }

    /**
     * Method to remove a property from the list of properties
     *  associated with this capability.<br />
     * Throws {@link NoEntityFoundException} 
     * if the capability cannot be found in database.
     * 
     * @param capabilityName capability name
     * @param version capability version
     * @param namespace capability namespace
     * @param property property to associate
     * @return updated capability
     * @throws NoEntityFoundException
     */
    public Capability removeProperty(String capabilityName, String version, 
            String namespace, Property property) throws NoEntityFoundException {
        // retrieve attached capability entity
        Capability c = findCapability(capabilityName, version, namespace);
        if (c != null){
            // retrieve attached petal entity
            Property p = propertySession.find(property.getId());  

            Set<Property> properties = c.getProperties();
            properties.remove(p);

            return entityManager.merge(c);
        } else {
            throw new NoEntityFoundException("Capability " + capabilityName 
                    + " in version " + version 
                    + " is not present on database.");
        }
    }


    /**
     * Method to collect the petals which provides a specific capability.<br />
     * Throws {@literal NoEntityFoundException} if capability doesn't exist.
     * 
     * @param name capability name
     * @param version capability version 
     * @param namespace capability namespace
     * @return collection of all petals providing this specific capability
     */
    @Override
    public Collection<Petal> collectPetals(String capabilityName, 
            String version, String namespace) throws NoEntityFoundException {
        Capability capability = findCapability(capabilityName, version, 
                namespace);
        if (capability != null) {
            Set<Petal> petals = new HashSet<>();
            petals.addAll(capability.getPetals());
            return petals;
        } else {
            throw new NoEntityFoundException("Capability " + capabilityName 
                    + " in version " + version 
                    + " is not present on database.");
        }
    }

    /**
     * Method to add a petal to the list of petals which provide the capability.
     * 
     * @param capability capability provided by the petal
     * @param petal petal to add 
     * @return modified Capability instance (updated list of providers)
     * 
     * @throws NoEntityFoundException if the capability doesn't exist
     */
    @Override
    public Capability addPetal(Capability capability, Petal petal) 
            throws NoEntityFoundException{
        // retrieve attached capability entity
        Capability c = findCapability(capability.getCapabilityName(), 
                capability.getVersion(), capability.getNamespace());

        if (c != null) {
            // retrieve attached petal entity
            Petal p = petalSession.findPetal(petal.getVendor(), 
                    petal.getArtifactId(), petal.getVersion());       

            Set<Petal> petals = c.getPetals();
            petals.add(p);
            c.setPetals(petals);

            return entityManager.merge(c); 
        } else {
            throw new NoEntityFoundException("Capability " + 
                    capability.getCapabilityName() + " in version "
                    + capability.getVersion() + " is not present on database.");
        }
    }

    /**
     * Method to remove a petal from the petals list providing a capability.
     * 
     * @param capability capability provided by the petal.
     * @param petal petal to remove
     * @return modified Capability instance (updated list of providers),
     *  or {@literal null} if no more petals provide this capability
     * @throws NoEntityFoundException 
     */
    @Override
    public Capability removePetal(Capability capability, Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached capability entity
        Capability c = findCapability(capability.getCapabilityName(), 
                capability.getVersion(), capability.getNamespace());
        if (c != null) {
            // retrieve attached petal entity
            Petal p = petalSession.findPetal(petal.getVendor(), 
                    petal.getArtifactId(), petal.getVersion());  

            Set<Petal> petals = c.getPetals();
            petals.remove(p);

            // We can delete this capability if no petal provides 
            //this capability any more.
            if (petals.isEmpty()) {
                entityManager.remove(c);
                return null;
            } else {
                c = entityManager.merge(c);
            }

            return c;
        } else {
            throw new NoEntityFoundException("Capability "
                    + capability.getCapabilityName() + " in version " 
                    + capability.getVersion() + " is not present on database.");
        }
    }

    /**
     * Method to collect all capabilities present in database.
     * 
     * @return collection of all capabilities which are stored in the database
     */
    @Override
    public Collection<Capability> collectCapabilities() {
        Query capQuery = entityManager.createNamedQuery("Capability.findAll");
        @SuppressWarnings("unchecked")
        List<Capability> capList = capQuery.getResultList();
        Set<Capability> capSet = new HashSet<Capability>();
        capSet.addAll(capList);

        return capSet;
    }
}