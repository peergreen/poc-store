package com.peergreen.store.db.client.ejb.session.api;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public interface ISessionProperty {

    /**
     * Method to create a Property in database.
     * 
     * @param name Property name
     * @param value Property value
     * @return created Property
     */
    Property createProperty(String name, String value);
    
    /**
     * Method to remove a Property from database.
     * 
     * @param id Property id
     */
    void removeProperty(long id);
    
    /**
     * Method to retrieve associated Capability.<br />
     * Throws {@literal NoEntityFoundException} if Property cannot be find.
     * 
     * @param id Property id
     * @return associated Capability
     * @throws NoEntityFoundException 
     */
    Capability getCapability(long id) throws NoEntityFoundException;
    
    /**
     * Method to set associated Capability.<br />
     * Throws {@literal NoEntityFoundException} if Property or Capability cannot be find.<br />
     * Both have to be attached entities.
     * 
     * @param id Property id
     * @return associated Capability
     * @throws NoEntityFoundException 
     */
    Property setCapability(long id, Capability capability) throws NoEntityFoundException;
    
    /**
     * Method to retrieve a Property thanks to its id.
     * 
     * @param id property id
     * @return found Property
     */
    Property find(long id);
    
}
