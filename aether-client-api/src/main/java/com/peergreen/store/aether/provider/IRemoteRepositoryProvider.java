package com.peergreen.store.aether.provider;

/**
 * Interface defining all common methods for RemoteRepositoryProvider.
 *
 * @param <T> type of repository
 */
public interface IRemoteRepositoryProvider<T> extends IRepositoryProvider<T> {

    /**
     * Method to retrieve name associated with remote repository.
     *
     * @return remote repository name
     */
    String getName();
    
    /**
     * Method to retrieve temporary path where petals are stored.
     *
     * @return temporary path where petals are stored
     */
    String getTmpPath();
    
}
