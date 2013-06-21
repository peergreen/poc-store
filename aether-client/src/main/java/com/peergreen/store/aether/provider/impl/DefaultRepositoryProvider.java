package com.peergreen.store.aether.provider.impl;

import java.io.File;

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
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;

import com.peergreen.store.aether.provider.IRepositoryProvider;
import com.peergreen.store.db.client.ejb.entity.Vendor;

/**
 * Class defining methods for repository provider.
 * 
 * @param <T> repository type (LocalRepository or RemoteRepository)
 */
public abstract class DefaultRepositoryProvider<T> implements IRepositoryProvider<T> {

    private RepositorySystem system;
    private DefaultRepositorySystemSession session;
    
    /**
     * Method to build up context.
     */
    public abstract void init();
    
    /**
     * Build requirements to use Aether (system and session).
     */
    protected void validate() {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, FileRepositoryConnectorFactory.class);
        locator.addService(RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class);
        system = locator.getService(RepositorySystem.class);
        
        session = MavenRepositorySystemUtils.newSession();
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
    public void addPetal(Vendor vendor, String artifactId, String version, File binary) {
        Artifact jarArtifact = new DefaultArtifact(vendor.getVendorName(), artifactId, null, "jar", version);
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

    public RepositorySystem getSystem() {
        return system;
    }

    public DefaultRepositorySystemSession getSession() {
        return session;
    }

}
