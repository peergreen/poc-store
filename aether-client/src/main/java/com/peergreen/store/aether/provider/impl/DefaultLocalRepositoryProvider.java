package com.peergreen.store.aether.provider.impl;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import com.peergreen.store.db.client.ejb.entity.Vendor;

/**
 * Class defining methods for local repository provider.
 */
@Component
@Provides
public class DefaultLocalRepositoryProvider extends DefaultRepositoryProvider<LocalRepository> {

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
    public File retrievePetal(Vendor vendor, String artifactId, String version) {
        Artifact artifact = new DefaultArtifact(vendor.getVendorName()+":"+artifactId+":"+version);
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
