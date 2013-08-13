package com.peergreen.store.db.client.ejb.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionProperty;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Class defining methods to manage Property entities.
 * <ul>
 *      <li>Create a Property in database</li>
 *      <li>Remove a Property from database</li>
 *      <li>Retrieve associated Capability</li>
 *      <li>Set associated Capability</li>
 *      <li>Find an existing Property in database</li>
 * </ul>
 *
 */
@Stateless
public class DefaultSessionProperty implements ISessionProperty {

    private EntityManager entityManager;
    private ISessionCapability capabilitySession;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Method to create a Property in database.
     * 
     * @param name Property name
     * @param value Property value
     * @return created Property
     */
    @Override
    public Property createProperty(String name, String value) {
        Property prop = new Property(name, value);
        entityManager.persist(prop);
        
        return prop;
    }

    /**
     * Method to remove a Property from database.
     * 
     * @param id Property id
     */
    @Override
    public void removeProperty(long id) {
        Property prop = find(id);
        if (prop != null) {
            entityManager.remove(prop);
        }
    }

    /**
     * Method to retrieve associated Capability.<br />
     * Throws {@literal NoEntityFoundException} if Property cannot be find.
     * 
     * @param id Property id
     * @return associated Capability
     * @throws NoEntityFoundException 
     */
    @Override
    public Capability getCapability(long id) throws NoEntityFoundException {
        Property prop = find(id);
        if (prop != null) {
            return prop.getCapability();
        } else {
            throw new NoEntityFoundException("No existing Property with id " + id + ".");
        }
    }

    /**
     * Method to set associated Capability.<br />
     * Throws {@literal NoEntityFoundException} if Property or Capability cannot be find.<br />
     * Both have to be attached entities.
     * 
     * @param id Property id
     * @return associated Capability
     * @throws NoEntityFoundException 
     */
    @SuppressWarnings("unused")
    @Override
    public Property setCapability(long id, Capability capability) throws NoEntityFoundException {
        Property prop = find(id);
        
        if (prop != null) {
            Capability cap = capabilitySession.findCapability(capability.getCapabilityName(), capability.getVersion());
            if (capability != null) {
                prop.setCapability(cap);
            } else {
                throw new NoEntityFoundException("Capability " + capability.getCapabilityName() + " in v. " + capability.getVersion());
            }
        } else {
            throw new NoEntityFoundException("No existing Property with id " + id + ".");
        }
        
        return prop;
    }

    @Override
    public Property find(long id) {
        return entityManager.find(Property.class, id);
    }

    @EJB
    public void setSessionCapability(ISessionCapability capabilitySession) {
        this.capabilitySession = capabilitySession;
    }
}
