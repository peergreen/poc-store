package com.peergreen.store.aether.client.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;


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
     * Method to recover petal's metadata from its information
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return collection containing all petal's metadata
     */
    @Override
    public HashMap<String, String> getMetadata(String vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to recover petal's binary from its information
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal's binary
     */
    @Override
    public File getPetal(String vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

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
    @Override
    public void addToStaging(String vendor, String artifactId, String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petal) {
        // TODO Auto-generated method stub
        
    }

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
    @Override
    public void addToLocal(String vendor, String artifactId, String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petal) {
        // TODO Auto-generated method stub
        
    }

}
