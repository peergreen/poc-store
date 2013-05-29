package com.peergreen.store.aether.client;

import java.io.File;
import java.util.HashMap;


/**
 * Interface defining feature's persistence relative functionalities.
 * <p>
 * Provides methods to:
 * <ul>
 * 		<li>retrieve feature's metadata</li>
 * 		<li>retrieve feature's binary</li>
 * 		<li>add a feature to the staging repository</li>
 * 		<li>add a feature to the local repository</li>
 * </ul>
 */
public interface IFeaturesPersistence 
{
    /**
     * Method to recover feature's metadata from its id
     * 
     * @param id feature's id
     * @return collection containing all feature's metadata
     */
    public HashMap<String, String> getMetadata(int id);

    /**
     * Method to recover feature's metadata from its information
     * 
     * @param groupId feature's group id
     * @param artifactId feature's artifact id
     * @param version feature's version
     * @return collection containing all feature's metadata
     */
    public HashMap<String, String> getMetadata(String groupId, String artifactId, String version);

    /**
     * Method to recover feature's binary from its id
     * 
     * @param id
     * @return feature's binary
     */
    public File getFeature(int id);

    /**
     * Method to recover feature's binary from its information
     * 
     * @param groupId feature's group id
     * @param artifactId feature's artifact id
     * @param version feature's version
     * @return feature's binary
     */
    public File getFeature(String groupId, String artifactId, String version);

    /**
     * Method to add a feature to the staging repository
     * 
     * @param groupId feature's groupId
     * @param artifactId feature's artifactId
     * @param version feature's version
     * @param description feature's description
     * @param category feature's category
     * @param requirements requirements list
     * @param capabilities exported capabilities list
     * @param properties feature's additional properties
     * @param feature feature's binary
     */
    public void addToStaging(
            String groupId,
            String artifactId,
            String version,
            String description,
            //			Category category,
            //			ArrayList<Requirement> requirements,
            //			ArrayList<Capability> capabilities,
            HashMap<String, String> properties,
            File feature);

    /**
     * Method to add a feature to the staging repository
     * 
     * @param groupId feature's groupId
     * @param artifactId feature's artifactId
     * @param version feature's version
     * @param description feature's description
     * @param category feature's category
     * @param requirements requirements list
     * @param capabilities exported capabilities list
     * @param properties feature's additional properties
     * @param feature feature's binary
     */
    public void addToLocal(
            String groupId,
            String artifactId,
            String version,
            String description,
            //			Category category,
            //			ArrayList<Requirement> requirements,
            //			ArrayList<Capability> capabilities,
            HashMap<String, String> properties,
            File feature);
}
