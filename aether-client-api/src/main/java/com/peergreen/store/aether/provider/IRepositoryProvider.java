package com.peergreen.store.aether.provider;

import java.io.File;

/**
 * Interface defining methods for repository provider.
 */
public interface IRepositoryProvider<T> {

    /**
     * Method to build up context.
     */
    void init();

    /**
     * Method to add a petal to the repository.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param binary petal's binary
     */
    void addPetal(String vendor, String artifactId, String version, File binary);

    /**
     * Method to retrieve a petal's binary from the repository.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return
     */
    File retrievePetal(String vendor, String artifactId, String version);

}
