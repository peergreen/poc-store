package com.peergreen.store.aether.client.util;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;


/**
 * Interface defining tool methods to ease the use of Aether
 */
public interface IAetherUtils {
    public RepositorySystem newRepositorySystem();
    public DefaultRepositorySystemSession newRepositorySystemSession(RepositorySystem system);
}
