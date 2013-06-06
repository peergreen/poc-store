package com.peergreen.store.aether.client.impl;

import java.io.File;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;

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

    private IRepositoryProvider localProvider;
    private IRepositoryProvider stagingProvider;
    private Set<IRepositoryProvider> remoteProviders;

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
        /*
        Artifact artifact = new DefaultArtifact(vendor+":"+artifactId+":"+version);
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(artifact);
        ArtifactResult artifactResult = null;
        try {
            artifactResult = system.resolveArtifact(session,artifactRequest);
        } catch (ArtifactResolutionException e) {
            e.printStackTrace();
        }
        artifact = artifactResult.getArtifact();
        return artifact.getFile();
        */
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
        // TODO
        /*
        Artifact jarArtifact = new DefaultArtifact(vendor, artifactId, "", "", version);
        jarArtifact = jarArtifact.setFile(petal);
//        Artifact pomArtifact = new SubArtifact( jarArtifact, "", "pom" );
//        pomArtifact = pomArtifact.setFile( new File( "pom.xml" ) );
        InstallRequest installRequest = new InstallRequest();
        installRequest.addArtifact(jarArtifact);
        try {
            system.install(session, installRequest);
        } catch (InstallationException e) {
            e.printStackTrace();
        }
        */
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
    public void bindLocalRepository(IRepositoryProvider provider) {
        
    }
    
    @Bind(filter="(&(local=true)(staging=false))")
    public void bindLocalProvider(IRepositoryProvider provider) {
        localProvider = provider;
    }
    
    @Unbind
    public void unbindLocalProvider(IRepositoryProvider provider) {
        localProvider = null;
    }
    
    @Bind(filter="(&(local=true)(staging=true))")
    public void bindStagingProvider(IRepositoryProvider provider) {
        localProvider = provider;
    }
    
    @Unbind
    public void unbindStagingProvider(IRepositoryProvider provider) {
        localProvider = null;
    }
    
    @Bind(optional=true, aggregate=true, filter="(local=false)")
    public void bindRemoteProvider(IRepositoryProvider provider) {
        remoteProviders.add(provider);
    }
    
    @Unbind
    public void unbindRemoteProvider(IRepositoryProvider provider) {
        remoteProviders.remove(provider);
    }
    
}
