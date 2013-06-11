package com.peergreen.store.aether.provider.impl;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

/**
 * Class defining methods for remote repository provider.
 * 
 * @param <T> repository type (RemoteRepository)
 */
@Component
@Provides
public class DefaultRemoteRepositoryProvider<T> extends DefaultRepositoryProvider<RemoteRepository> {

    private RemoteRepository repository;
    // retrieved from Config Admin
    private String name;
    private String path;
    
    /**
     * Method to build up requirements to use Aether with remote repository.
     */
    @Validate
    @Override
    public void init() {
        validate();
        repository = new RemoteRepository.Builder(name, "default", path).build();
    }
    
    /**
     * Method to retrieve a petal's binary from the repository.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return
     */
    @Override
    public File retrievePetal(String vendor, String artifactId, String version) {
        Artifact artifact = new DefaultArtifact(vendor+":"+artifactId+":"+version);
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.addRepository(repository);
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

    public void setRepository(RemoteRepository repository) {
        this.repository = repository;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
}
