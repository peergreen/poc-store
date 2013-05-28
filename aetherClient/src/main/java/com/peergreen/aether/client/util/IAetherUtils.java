package com.peergreen.aetherClient.utils;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;


/**
 * Interface defining tool methods to ease the use of Aether
 */
public interface AetherUtils {
	public RepositorySystem newRepositorySystem();
	public DefaultRepositorySystemSession newRepositorySystemSession(RepositorySystem system);
}
