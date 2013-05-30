package com.peergreen.store.aether.client.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Requirement;


/**
 * Class to handle petal's persistence relative functionalities.<br />
 * Default implementation of FeaturePersistence interface.
 * <p>
 * Provides methods to:
 * <ul>
 * 		<li>retrieve petal's metadata</li>
 * 		<li>retrieve petal's binary</li>
 * 		<li>add a petal to the staging repository</li>
 * 		<li>add a petal to the local repository</li>
 * </ul>
 */
public class DefaultPetalsPersistence implements IPetalsPersistence {

    /**
     * Method to recover petal's metadata from its id
     * 
     * @param id petal's id
     * @return collection containing all petal's metadata
     */
    @Override
    public HashMap<String, String> getMetadata(int id) {
        HashMap<String, String> metadata = new HashMap<String, String>();
        
        return metadata;
    }

    /**
     * Method to recover petal's metadata from its information
     * 
     * @param groupId petal's group id
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return collection containing all petal's metadata
     */
    @Override
    public HashMap<String, String> getMetadata(String groupId, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to recover petal's binary from its id
     * 
     * @param id
     * @return petal's binary
     */
    @Override
    public File getPetal(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to recover petal's binary from its information
     * 
     * @param groupId petal's group id
     * @param artifactId petal's artifact id
     * @param version petal's version
     * @return petal's binary
     */
    @Override
    public File getPetal(String groupId, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

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
    @Override
    public void addToStaging(String groupId, String artifactId, String version, String description, Category category,
            ArrayList<Requirement> requirements, ArrayList<Capability> capabilities,
            HashMap<String, String> properties, File petal) {
        // TODO Auto-generated method stub
        
    }

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
    @Override
    public void addToLocal(String groupId, String artifactId, String version, String description, Category category,
            ArrayList<Requirement> requirements, ArrayList<Capability> capabilities,
            HashMap<String, String> properties, File petal) {
        // TODO Auto-generated method stub
        
    }

}
