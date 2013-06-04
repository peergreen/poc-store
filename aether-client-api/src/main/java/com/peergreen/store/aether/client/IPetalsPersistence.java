package com.peergreen.store.aether.client;

import java.io.File;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;


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
    File getPetal(Vendor vendor, String artifactId, String version);

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
            Vendor vendor,
            String artifactId,
            String version,
            String description,
            Category category,
            Set<Requirement> requirements,
            Set<Capability> capabilities,
            File petal);

    /**
     * Method to retrieve a petal from the local repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    Petal getPetalFromLocal(Vendor vendor, String artifactId, String version);

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
            Vendor vendor,
            String artifactId,
            String version,
            String description,
            Category category,
            Set<Requirement> requirements,
            Set<Capability> capabilities,
            File petal);

    /**
     * Method to retrieve a petal from the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    Petal getPetalFromStaging(Vendor vendor, String artifactId, String version);
}
