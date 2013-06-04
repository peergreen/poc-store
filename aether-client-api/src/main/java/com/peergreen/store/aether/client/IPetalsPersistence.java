package com.peergreen.store.aether.client;

import java.io.File;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;


/**
 * Interface defining petal's persistence relative functionalities.
 * <p>
 * Provides methods to:
 * <ul>
 * 		<li>retrieve petal's binary</li>
 *      <li>add a petal to the local repository</li>
 *      <li>retrieve a petal from the local repository</li>
 * 		<li>add a petal to the staging repository</li>
 *      <li>retrieve a petal from the staging repository</li>
 * </ul>
 */
public interface IPetalsPersistence 
{

    /**
     * Method to recover petal's binary from its information
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return petal's binary
     */
    File getPetal(IVendor vendor, String artifactId, String version);

    /**
     * Method to add a petal to the local repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements requirements list
     * @param capabilities exported capabilities list
     * @param petal petal's binary
     */
    void addToLocal(
            IVendor vendor,
            String artifactId,
            String version,
            String description,
            ICategory category,
            Set<IRequirement> requirements,
            Set<ICapability> capabilities,
            File petal);

    /**
     * Method to retrieve a petal from the local repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    IPetal getPetalFromLocal(IVendor vendor, String artifactId, String version);

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
     * @param petal petal's binary
     */
    void addToStaging(
            IVendor vendor,
            String artifactId,
            String version,
            String description,
            ICategory category,
            Set<IRequirement> requirements,
            Set<ICapability> capabilities,
            File petal);

    /**
     * Method to retrieve a petal from the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    IPetal getPetalFromStaging(IVendor vendor, String artifactId, String version);
}
