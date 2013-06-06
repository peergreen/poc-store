package com.peergreen.store.aether.client.impl;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.aether.provider.IRepositoryProvider;

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

    private IRepositoryProvider<LocalRepository> localProvider;
    private IRepositoryProvider<LocalRepository> stagingProvider;
    private Set<IRepositoryProvider<RemoteRepository>> remoteProviders;

    @Validate
    private void validate() {
        
    }

    /**
     * Method to add a repository to the list of remote repositories.
     * 
     * @param url remote repository url
     */
    @Override
    public void addRemoteRepository(String url) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Method to remove a repository from the list of remote repositories.
     * 
     * @param url remote repository url
     */
    @Override
    public void removeRemoteRepository(String url) {
        // TODO Auto-generated method stub
        
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
        // TODO
        File petal = null;

        // search in local repository
        petal = localProvider.retrievePetal(vendor, artifactId, version);

        // if not found locally, browse remote repositories
        if (petal == null) {
            Iterator<IRepositoryProvider<RemoteRepository>> it = remoteProviders.iterator();
            while (petal == null && it.hasNext()) {
                petal = it.next().retrievePetal(vendor, artifactId, version);
            }
        }
        
        return petal;
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
        // TODO
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
    public File getPetalFromLocal(String vendor, String artifactId, String version) {
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
    public File getPetalFromStaging(String vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to retrieve a petal from all remote repositories
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal's binary
     */
    @Override
    public File getPetalFromRemote(String vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    @Bind
    public void bindLocalRepository(IRepositoryProvider<LocalRepository> provider) {
        
    }
    
    @Bind(filter="(staging=false)")
    public void bindLocalProvider(IRepositoryProvider<LocalRepository> provider) {
        localProvider = provider;
    }
    
    @Unbind
    public void unbindLocalProvider(IRepositoryProvider<LocalRepository> provider) {
        localProvider = null;
    }
    
    @Bind(filter="(staging=true)")
    public void bindStagingProvider(IRepositoryProvider<LocalRepository> provider) {
        localProvider = provider;
    }
    
    @Unbind
    public void unbindStagingProvider(IRepositoryProvider<LocalRepository> provider) {
        localProvider = null;
    }
    
    @Bind(optional=true, aggregate=true)
    public void bindRemoteProvider(IRepositoryProvider<RemoteRepository> provider) {
        remoteProviders.add(provider);
    }
    
    @Unbind
    public void unbindRemoteProvider(IRepositoryProvider<RemoteRepository> provider) {
        remoteProviders.remove(provider);
    }
    
}
