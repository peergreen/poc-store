package com.peergreen.store.aether.client;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;


/**
 * Interface defining petal's persistence relative functionalities.
 * <p>
 * Provides methods to:
 * <ul>
 * 		<li>retrieve petal's metadata</li>
 * 		<li>retrieve petal's binary</li>
 * 		<li>add a petal to the staging repository</li>
 * 		<li>add a petal to the local repository</li>
 * </ul>
 */
public interface IPetalsPersistence 
{

    /**
     * Method to recover petal's metadata from its information
     * 
     * @param vendor petal's group id
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return collection containing all petal's metadata
     */
    Map<String, String> getMetadata(String vendor, String artifactId, String version);

    /**
     * Method to recover petal's binary from its information
     * 
     * @param vendor petal's group id
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return petal's binary
     */
    File getPetal(String vendor, String artifactId, String version);

    /**
     * Method to add a petal to the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements requirements list
     * @param capabilities exported capabilities list
     * @param properties petal's additional properties
     * @param petal petal's binary
     */
    void addToStaging(
            String vendor,
            String artifactId,
            String version,
            String description,
            ICategory category,
            List<IRequirement> requirements,
            List<ICapability> capabilities,
            Map<String, String> properties,
            File petal);

    /**
     * Method to retrieve a petal from the local repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    IPetal getPetalFromLocal(String vendor, String artifactId, String version);
    
    /**
     * Method to add a petal to the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements requirements list
     * @param capabilities exported capabilities list
     * @param properties petal's additional properties
     * @param petal petal's binary
     */
    void addToLocal(
            String vendor,
            String artifactId,
            String version,
            String description,
            ICategory category,
            List<IRequirement> requirements,
            List<ICapability> capabilities,
            Map<String, String> properties,
            File petal);
    
    /**
     * Method to retrieve a petal from the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    IPetal getPetalFromStaging(String vendor, String artifactId, String version);
}
