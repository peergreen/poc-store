package com.peergreen.store.aether.client.impl;

import java.io.File;

import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.installation.InstallRequest;
import org.eclipse.aether.installation.InstallationException;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.aether.client.util.IAetherUtils;

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

    @Requires
    private IAetherUtils utils;
    private RepositorySystem system;
    private DefaultRepositorySystemSession session;

    @Validate
    private void validate() {
        system = utils.newRepositorySystem();
        session = utils.newRepositorySystemSession(system);
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

}
