package com.peergreen.store.aether.provider.impl;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.apache.felix.ipojo.annotations.Validate;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import com.peergreen.store.aether.provider.ILocalRepositoryProvider;

/**
 * Class defining methods for local repository provider.
 */
@Component
@Provides
public class DefaultLocalRepositoryProvider
extends DefaultRepositoryProvider<LocalRepository>
implements ILocalRepositoryProvider<LocalRepository> {

    private LocalRepository repository;
    // retrieved from Config Admin
    @Property
    private String path;
    @ServiceProperty
    private boolean staging;

    /**
     * Method to build up requirements to use Aether with local repository.
     */
    @Validate
    public void init() {
        validate();
        repository = new LocalRepository(path);
        getSession().setLocalRepositoryManager(getSystem().newLocalRepositoryManager(getSession(), repository));
    }

    /**
     * Method to retrieve a petal's binary from the repository.
     * 
     * @param vendor petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return
     */
    @Override
    public File retrievePetal(String vendor, String artifactId, String version) {
        Artifact artifact = new DefaultArtifact(vendor+":"+artifactId+":"+version);
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(artifact);
        ArtifactResult artifactResult = null;

        // better to throw exception and manage it on call level
        try {
            artifactResult = getSystem().resolveArtifact(getSession(),artifactRequest);
        } catch (ArtifactResolutionException e) {
            e.printStackTrace();
        }
        artifact = artifactResult.getArtifact();

        return artifact.getFile();
    }
    
    /**
     * Method to retrieve repository path.
     * 
     * @return path to the repository (remote or local)
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Method to check if local repository is a staging one.
     *
     * @return {@literal true} if store is staging, {@literal false} otherwise
     */
    public boolean isStaging() {
        return staging;
    }
}
