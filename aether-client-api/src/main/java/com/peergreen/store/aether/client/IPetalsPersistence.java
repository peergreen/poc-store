package com.peergreen.store.aether.client;

import java.io.File;

import com.peergreen.store.db.client.ejb.entity.Petal;
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
    File getPetal(String vendor, String artifactId, String version);

    /**
     * Method to add a petal to the local repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param petal petal's binary
     */
    void addToLocal(
            String vendor,
            String artifactId,
            String version,
            File petal);

    /**
     * Method to retrieve a petal from the local repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    Petal getPetalFromLocal(String vendor, String artifactId, String version);

    /**
     * Method to add a petal to the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param petal petal's binary
     */
    void addToStaging(
            String vendor,
            String artifactId,
            String version,
            File petal);

    /**
     * Method to retrieve a petal from the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    Petal getPetalFromStaging(String vendor, String artifactId, String version);
}
