package com.peergreen.store.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;

public interface IPetalController {

    /**
     * Method to retrieve metadata related to a petal.
     * 
     * @param pseudo petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal related metadata
     */
    Map<String, String> getPetalMetadata(String vendor, String artifactId, String version);

    /**
     * Method to directly add a petal to the store.<br />
     * This method make the petal persistent in the store.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's capabilities
     * @param properties petal's additional properties
     * @param petalBinary petal's binary
     */
    void addPetal(String vendor, String artifactId,
            String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petalBinary);

    /**
     * Method to remove a petal from the store thanks to its information.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    void removePetal(String vendor, String artifactId, String version);

    /**
     * Method to update a petal.
     * 
     * @param vendor petal's vendor
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
    IPetal updatePetal(String vendor, String artifactId,
            String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petalBinary);
}
