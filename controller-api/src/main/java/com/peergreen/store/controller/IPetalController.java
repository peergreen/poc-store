package com.peergreen.store.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

public interface IPetalController {

    /**
     * Method to retrieve metadata related to a petal
     * 
     * @param petalId petal's id
     * @return petal's metadata
     */
    Map<String, String> getPetalMetadata(int petalId);

    /**
     * Method to collect available petals.<br />
     * Retrieve available petals only for user's group.
     * 
     * @return list of available petals
     */
    List<IPetal> collectPetals(); 

    /**
     * Method to collect all
     * 
     * @return
     */
    List<IPetal> collectInstalledPetals();

    /**
     * Method to modify a User instance.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's email
     * @return User instance resulting of the changes
     */
    IUser manageAccount(String pseudo, String password, String email);

    /**
     * Collect all User instances on base
     * 
     * @return list of all the database's users
     */
    List<IUser> collectUsers();

    /**
     * Method to retrieve metadata related to a user.
     * 
     * @param pseudo user's pseudo
     * @return user related metadata
     */
    Map<String, String> getUserMetadata(String pseudo);

    /**
     * Method to submit a petal for an add in the store.<br />
     * Submitted petals needs to be validated to effectively added to the store.
     * 
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's exported capabilities
     * @param properties petal's additional properties
     * @param petalBinary petal's binary file
     */
    void submitPetal(String groupId, String artifactId,
            String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petalBinary);

    /**
     * Method to validate a petal's submission thanks to its id.<br />
     * This method make the petal persistence in the store.
     * 
     * @param petalId petal's id
     */
    void validatePetal(int petalId);

    /**
     * Method to validate a petal's submission thanks to its information.<br />
     * This method make the petal persistent in the store.
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    void validatePetal(String groupId, String artifactId, String version);

    /**
     * Method to directly add a petal to the store.<br />
     * This method make the petal persistent in the store.
     * 
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's capabilities
     * @param properties petal's additional properties
     * @param petalBinary petal's binary
     */
    void addPetal(String groupId, String artifactId,
            String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petalBinary);

    /**
     * Method to remove a petal from the store thanks to its id.
     * 
     * @param petalId petal's id
     */
    void removePetal(int petalId);

    /**
     * Method to remove a petal from the store thanks to its information.
     * 
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    void removePetal(String groupId, String artifactId, String version);

    /**
     * Method to update a petal.
     * 
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's capabilities
     * @param properties petal's properties
     * @param petalBinary petal's petalBinary
     * @return
     */
    IPetal updatePetal(String groupId, String artifactId,
            String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petalBinary);
}
