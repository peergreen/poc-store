package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public interface ISessionCapability {

    /**
     * Method to add a new capability in the database.
     * 
     * @param capabilityName the capability name
     * @param version capability version
     * @param namespace capability namespace
     * @param properties capability properties
     * @return created Capability instance
     * @throws EntityAlreadyExistsException
     */
    Capability addCapability(String capabilityName, String version, String namespace, Set<Property> properties) throws EntityAlreadyExistsException;

    /**
     * Method to delete a capability in the database
     * 
     * @param capabilityName the capability's name
     * @param version the version of the capability to delete
     * @return 
     */
    Capability deleteCapability (String capabilityName, String version);

    /**
     * Method to find a capability in the database
     * 
     * @param capabilityName the capability's name
     * @return the capability with the name 'capabilityName'
     */
    Capability findCapability (String capabilityName, String version);

    /**
     * Method to change a capability existing namespace.
     * 
     * @param capability the capability to modify 
     * @param namespace the new namespace of the capability
     * @return The capability with the change updated
     * @throws NoEntityFoundException 
     */
    Capability updateNamespace (Capability capability, String namespace)throws NoEntityFoundException;

    /**
     * Method to change a capability existing properties
     * @param capability the capability to modify 
     * @param properties the new properties of the capability
     * @return The capability with the change updated
     * @throws NoEntityFoundException 
     */
    Capability updateProperties (Capability capability, Set<Property> properties) throws NoEntityFoundException;
    
    /**
     * Method to collect all properties associated with a capability.<br />
     * Throws {@link NoEntityFoundException} if the capability cannot be found in database.
     * 
     * @param capabilityName capability name
     * @param version capability version
     * @return associated properties
     * @throws NoEntityFoundException
     */
    Collection<Property> collectProperties(String capabilityName, String version) throws NoEntityFoundException;
    
    /**
     * Method to add a property to the list of properties associated with this capability.<br />
     * Throws {@link NoEntityFoundException} if the capability cannot be found in database.
     * 
     * @param capabilityName capability name
     * @param version capability version
     * @param property property to associate
     * @return updated capability
     * @throws NoEntityFoundException
     */
    Capability addProperty(String capabilityName, String version, Property property) throws NoEntityFoundException;
    
    /**
     * Method to remove a property from the list of properties associated with this capability.<br />
     * Throws {@link NoEntityFoundException} if the capability cannot be found in database.
     * 
     * @param capabilityName capability name
     * @param version capability version
     * @param property property to associate
     * @return updated capability
     * @throws NoEntityFoundException
     */
    Capability removeProperty(String capabilityName, String version, Property property) throws NoEntityFoundException;

    /**
     * Method to collect the petals which provides the specified capability.<br />
     * Throws {@link NoEntityFoundException} if the capability cannot be found in database.
     * 
     * @param capabilityName capability name
     * @param version capability version
     * @return collection of all petals providing this capability
     * @throws NoEntityFoundException 
     */
    Collection<Petal> collectPetals(String capabilityName, String version) throws NoEntityFoundException;

    /**
     * Method to add a petal to the list of petals which provide the capability.
     * 
     * @param capability the capability that is given by the petal
     * @param petal the petal to add 
     * 
     * @return A new capability with a new list of petals 
     * @throws NoEntityFoundException 
     */
    Capability addPetal(Capability capability, Petal petal)throws NoEntityFoundException;

    /**
     * Method to remove a petal to the list of petals which give the capability
     * 
     * @param capability the capability that is given by the petal
     * @param petal the petal to remove
     * 
     * @return A new capability with a new list of petals 
     * @throws NoEntityFoundException 
     */
    Capability removePetal (Capability capability, Petal petal) throws NoEntityFoundException;

    /**
     * Method to collect all the capability in the database
     * 
     * @return A collection of capabilities in the database
     */
    Collection<Capability> collectCapabilities();

}