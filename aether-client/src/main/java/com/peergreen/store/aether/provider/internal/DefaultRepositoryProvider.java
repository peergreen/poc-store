package com.peergreen.store.aether.provider.internal;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.file.FileRepositoryConnectorFactory;
import org.eclipse.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.installation.InstallRequest;
import org.eclipse.aether.installation.InstallationException;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RemoteRepository.Builder;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;

import com.peergreen.store.aether.provider.IRepositoryProvider;

/**
 * Class defining methods for repository provider.
 */
@Component
//@Instantiate use Config Admin
@Provides
public class DefaultRepositoryProvider implements IRepositoryProvider {

    // better to define it from config file or use Config Admin
    private boolean local;
    private String name;
    private String path;
    // repository system
    private RepositorySystem system;
    // repository
    private ArtifactRepository repository;
    // session
    private DefaultRepositorySystemSession session;

    /**
     * Build requirements to use Aether (RepositorySystem, local repository and staging repository).
     */
    @Validate
    private void validate() {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, FileRepositoryConnectorFactory.class);
        locator.addService(RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class);
        system = locator.getService(RepositorySystem.class);
        
        DefaultRepositorySystemSession session;
        session = MavenRepositorySystemUtils.newSession();
        
        if (local) {
            repository = new LocalRepository(path);
            session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, (LocalRepository) repository));
        } else {
            repository = new RemoteRepository.Builder(name, "default", path).build();
        }
        
    }
    
    /**
     * Method to add a petal to the repository.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param binary petal's binary
     */
    @Override
    public void addPetal(String vendor, String artifactId, String version, File binary) {
        Artifact jarArtifact = new DefaultArtifact(vendor, artifactId, "", "", version);
        jarArtifact = jarArtifact.setFile(binary);
//        Artifact pomArtifact = new SubArtifact( jarArtifact, "", "pom" );
//        pomArtifact = pomArtifact.setFile( new File( "pom.xml" ) );
        InstallRequest installRequest = new InstallRequest();
        installRequest.addArtifact(jarArtifact);
        
        // better to throw exception and manage it on call level
        try {
            system.install(session, installRequest);
        } catch (InstallationException e) {
            e.printStackTrace();
        }
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
        Artifact artifact = new DefaultArtifact( "com.peergreen:example:0.1-SNAPSHOT" );
        ArtifactRequest artifactRequest = new ArtifactRequest();
        // if provider have to fetch remote repository
        if (!local) {
            artifactRequest.addRepository((RemoteRepository) repository);
        }
        artifactRequest.setArtifact(artifact);
        ArtifactResult artifactResult = null;
        
        // better to throw exception and manage it on call level
        try {
            artifactResult = system.resolveArtifact(session,artifactRequest);
        } catch (ArtifactResolutionException e) {
            e.printStackTrace();
        }
        artifact = artifactResult.getArtifact();
        
        return artifact.getFile();
    }

}
