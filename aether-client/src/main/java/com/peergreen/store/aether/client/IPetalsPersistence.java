package com.peergreen.store.aether.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Requirement;


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
     * Method to recover petal's metadata from its id
     * 
     * @param id petal's id
     * @return collection containing all petal's metadata
     */
    public HashMap<String, String> getMetadata(int id);

    /**
     * Method to recover petal's metadata from its information
     * 
     * @param groupId petal's group id
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return collection containing all petal's metadata
     */
    public HashMap<String, String> getMetadata(String groupId, String artifactId, String version);

    /**
     * Method to recover petal's binary from its id
     * 
     * @param id
     * @return petal's binary
     */
    public File getFeature(int id);

    /**
     * Method to recover petal's binary from its information
     * 
     * @param groupId petal's group id
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return petal's binary
     */
    public File getFeature(String groupId, String artifactId, String version);

    /**
     * Method to add a petal to the staging repository
     * 
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements requirements list
     * @param capabilities exported capabilities list
     * @param properties petal's additional properties
     * @param petal petal's binary
     */
    public void addToStaging(
            String groupId,
            String artifactId,
            String version,
            String description,
            Category category,
            ArrayList<Requirement> requirements,
            ArrayList<Capability> capabilities,
            HashMap<String, String> properties,
            File petal);

    /**
     * Method to add a petal to the staging repository
     * 
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements requirements list
     * @param capabilities exported capabilities list
     * @param properties petal's additional properties
     * @param petal petal's binary
     */
    public void addToLocal(
            String groupId,
            String artifactId,
            String version,
            String description,
            Category category,
            ArrayList<Requirement> requirements,
            ArrayList<Capability> capabilities,
            HashMap<String, String> properties,
            File petal);
}
