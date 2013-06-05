package com.peergreen.store.aether.client.impl;

import java.io.File;
import java.util.Set;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;


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
     * @param petal petal's binary
     */
    @Override
    public void addToLocal(String vendor, String artifactId, String version, File petal) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Method to retrieve a petal from the local repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    @Override
    public Petal getPetalFromLocal(String vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to add a petal to the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param petal petal's binary
     */
    @Override
    public void addToStaging(String vendor, String artifactId, String version, File petal) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Method to retrieve a petal from the staging repository
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    @Override
    public Petal getPetalFromStaging(String vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

}
