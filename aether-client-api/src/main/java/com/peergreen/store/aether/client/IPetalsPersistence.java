package com.peergreen.store.aether.client;

import java.io.File;


/**
 * Interface defining petal's persistence relative functionalities.
 * <p>
 * Provides methods to:
 * <ul>
 *      <li>add a remote repository</li>
 *      <li>remove a remote repository</li>
 * 		<li>retrieve petal's binary</li>
 *      <li>add a petal to the local repository</li>
 *      <li>retrieve a petal from the local repository</li>
 * 		<li>add a petal to the staging repository</li>
 *      <li>retrieve a petal from the staging repository</li>
 * </ul>
 */
public interface IPetalsPersistence {

    /**
     * Method to add a repository to the list of remote repositories.
     * 
     * @param name remote repository name
     * @param url remote repository url
     */
    public void addRemoteRepository(String name, String url);
    
    /**
     * Method to remove a repository from the list of remote repositories.
     * 
     * @param url remote repository url
     */
    public void removeRemoteRepository(String url);
    
    /**
     * Method to recover petal's binary from its information
     * 
     * @param vendorName petal's vendor's name
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return petal's binary
     */
    File getPetal(String vendorName, String artifactId, String version);

    /**
     * Method to add a petal to the local repository
     * 
     * @param vendorName petal's vendor's name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param petal petal's binary
     */
    void addToLocal(
            String vendorName,
            String artifactId,
            String version,
            File petal);

    /**
     * Method to retrieve a petal from the local repository
     * 
     * @param vendorName petal's vendor's name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal's binary
     */
    File getPetalFromLocal(String vendorName, String artifactId, String version);

    /**
     * Method to add a petal to the staging repository
     * 
     * @param vendorName petal's vendor's name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param petal petal's binary
     */
    void addToStaging(
            String vendorName,
            String artifactId,
            String version,
            File petal);

    /**
     * Method to retrieve a petal from the staging repository
     * 
     * @param vendorName petal's vendor's name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal's binary
     */
    File getPetalFromStaging(String vendorName, String artifactId, String version);
    
    /**
     * Method to retrieve a petal from all remote repositories
     * 
     * @param vendorName petal's vendor's name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal's binary
     */
    File getPetalFromRemote(String vendorName, String artifactId, String version);
}
