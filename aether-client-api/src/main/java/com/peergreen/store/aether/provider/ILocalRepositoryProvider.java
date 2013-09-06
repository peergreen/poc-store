package com.peergreen.store.aether.provider;

/**
 * Interface defining all common methods for LocalRepositoryProvider.
 *
 * @param <T> type of repository
 */
public interface ILocalRepositoryProvider<T> extends IRepositoryProvider<T> {

    /**
     * Method to check if local repository is a staging one.
     *
     * @return {@literal true} if store is staging, {@literal false} otherwise
     */
    boolean isStaging();
    
}
