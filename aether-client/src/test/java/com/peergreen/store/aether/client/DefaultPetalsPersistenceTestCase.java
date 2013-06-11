package com.peergreen.store.aether.client;

import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.aether.repository.RemoteRepository;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.aether.client.impl.DefaultPetalsPersistence;
import com.peergreen.store.aether.provider.IRepositoryProvider;
import com.peergreen.store.aether.provider.impl.DefaultRemoteRepositoryProvider;

public class DefaultPetalsPersistenceTestCase {
    private DefaultPetalsPersistence petalsPersistence = null;

    @BeforeMethod
    public void setUp() {
        petalsPersistence = new DefaultPetalsPersistence();
        
        // create remote providers list
        CopyOnWriteArraySet<IRepositoryProvider<RemoteRepository>> providers
                = new CopyOnWriteArraySet<IRepositoryProvider<RemoteRepository>>();
        petalsPersistence.setRemoteProviders(providers);
    }

    @Test
    public void removeRemoteRepositoryTest() {
        // initialize list with 2 elements
        DefaultRemoteRepositoryProvider<RemoteRepository> remoteRepoProvider1
                = new DefaultRemoteRepositoryProvider<RemoteRepository>();
        remoteRepoProvider1.setName("provider1");
        remoteRepoProvider1.setPath("toto");
        remoteRepoProvider1.setRepository(null);
        petalsPersistence.getRemoteProviders().add(remoteRepoProvider1);

        DefaultRemoteRepositoryProvider<RemoteRepository> remoteRepoProvider2
                = new DefaultRemoteRepositoryProvider<RemoteRepository>();
        remoteRepoProvider2.setName("provider2");
        remoteRepoProvider2.setPath("/home/toto");
        remoteRepoProvider2.setRepository(null);
        petalsPersistence.getRemoteProviders().add(remoteRepoProvider2);
        
        // call method to test if the remove action is done effectively
        petalsPersistence.removeRemoteRepository(remoteRepoProvider2.getPath());
        Assert.assertTrue(!petalsPersistence.getRemoteProviders().contains(remoteRepoProvider2.getPath()));
    }
}
