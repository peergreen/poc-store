package com.peergreen.store.aether.provider.internal;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

/**
 * Class defining methods for local repository provider.
 * 
 * @param <T> repository type (LocalRepository)
 */
@Component
@Provides
public class DefaultLocalRepositoryProvider<T> extends DefaultRepositoryProvider<RemoteRepository> {

    private LocalRepository repository;
    // retrieved from Config Admin
    private String path;
    private boolean staging;

    /**
     * Method to build up requirements to use Aether with local repository.
     */
    @Override
    public void init() {
        validate();
        repository = new LocalRepository(path);
        getSession().setLocalRepositoryManager(getSystem().newLocalRepositoryManager(getSession(), repository));
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
    
    public String getPath() {
        return path;
    }
    
    public boolean isStaging() {
        return staging;
    }
}
