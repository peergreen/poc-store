package com.peergreen.store.aether.client.util.internal;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.connector.file.FileRepositoryConnectorFactory;
import org.eclipse.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;

import com.peergreen.store.aether.client.util.IAetherUtils;

/**
 * Class defining tool methods to ease the use of Aether
 */
@Component
@Instantiate
@Provides
public class DeafultAetherUtils implements IAetherUtils {

    private String path;
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public RepositorySystem newRepositorySystem() {
        RepositorySystem system;
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, FileRepositoryConnectorFactory.class );
        locator.addService(RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class );
        system = locator.getService(RepositorySystem.class);
        
        return system;
    }

    @Override
    public DefaultRepositorySystemSession newRepositorySystemSession(
            RepositorySystem system) {
        DefaultRepositorySystemSession session;
        session = MavenRepositorySystemUtils.newSession();
        LocalRepository localRepo = new LocalRepository(path);
        return session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));
    }

}
