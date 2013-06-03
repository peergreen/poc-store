package com.peergreen.store.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;

/**
 * Interface defining high level operations to manage server.
 * <p>
 * Provided functionalities:
 * <ul>
 *     <li>Petal submission</li>
 *     <li>Petal submission validation</li>
 * </ul>
 */
public interface IStoreManagment {

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
     * Method to validate a petal's submission thanks to its information.<br />
     * This method make the petal persistent in the store.
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    void validatePetal(String groupId, String artifactId, String version);

}
