package com.peergreen.store.aether.client.impl;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.aether.provider.IRepositoryProvider;

/**
 * Class to handle petal's persistence relative functionalities.<br />
 * Default implementation of FeaturePersistence interface.
 * <p>
 * Provides methods to:
 * <ul>
 * 		<li>retrieve petal's metadata</li>
 * 		<li>retrieve petal's binary</li>
 * 		<li>add a petal to the staging repository</li>
 * 		<li>add a petal to the local repository</li>
 * </ul>
 */
@Component
@Instantiate
@Provides
public class DefaultPetalsPersistence implements IPetalsPersistence {

    @Requires
    private ConfigurationAdmin configAmdin;
    private Configuration config = null;
    private IRepositoryProvider<LocalRepository> localProvider;
    private IRepositoryProvider<LocalRepository> stagingProvider;
    private CopyOnWriteArraySet<IRepositoryProvider<RemoteRepository>> remoteProviders;

    public DefaultPetalsPersistence() {
        remoteProviders = new CopyOnWriteArraySet<>();
    }

    @Validate
    private void validate() {
        try {
            config = configAmdin.createFactoryConfiguration("factoryRemoteRepository", "DefaultRemoteRepositoryProvider");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add a repository to the list of remote repositories.
     * 
     * @param name remote repository name
     * @param url remote repository url
     */
    @Override
    public void addRemoteRepository(String name, String url) {
        // create a new configuration
        Dictionary<String, String> dict = new Hashtable<String, String>();
        dict.put("name", name);
        dict.put("path", url);

        // push configuration to Configuration Admin to generate iPOJO instance
        try {
            config.update(dict);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to remove a repository from the list of remote repositories.
     * 
     * @param url remote repository url
     */
    @Override
    public void removeRemoteRepository(String url) {
        boolean found = false;
        Iterator<IRepositoryProvider<RemoteRepository>> it = remoteProviders.iterator();
        while (!found && it.hasNext()) {
            IRepositoryProvider<RemoteRepository> current = it.next();

            // if corresponding instance found, remove it
            if (found = current.getPath().equals(url)) {
                remoteProviders.remove(current);
            }
        }
    }

    /**
     * Method to add a petal to the local repository
     * 
     * @param vendor petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param petal petal's binary
     */
    @Override
    public void addToLocal(String vendor, String artifactId, String version, File petal) {
        localProvider.addPetal(vendor, artifactId, version, petal);
    }

    /**
     * Method to retrieve a petal from the local repository
     * 
     * @param vendor petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    @Override
    public File getPetalFromLocal(String vendor, String artifactId, String version) {
        return localProvider.retrievePetal(vendor, artifactId, version);
    }

    /**
     * Method to add a petal to the staging repository
     * 
     * @param vendor petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param petal petal's binary
     */
    @Override
    public void addToStaging(String vendor, String artifactId, String version, File petal) {
        stagingProvider.addPetal(vendor, artifactId, version, petal);
    }

    /**
     * Method to retrieve a petal from the staging repository
     * 
     * @param vendorName petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal
     */
    @Override
    public File getPetalFromStaging(String vendor, String artifactId, String version) {
        return stagingProvider.retrievePetal(vendor, artifactId, version);
    }

    /**
     * Method to retrieve a petal from all remote repositories
     * 
     * @param vendor petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal's binary
     */
    @Override
    public File getPetalFromRemote(
            String vendor,
            String artifactId,
            String version,
            String url) {
        
        File petal = null;

        Iterator<IRepositoryProvider<RemoteRepository>> it = remoteProviders.iterator();
        while (petal == null && it.hasNext()) {
            petal = it.next().retrievePetal(vendor, artifactId, version);
        }

        return petal;
    }

    // bindings 
    @Bind(filter="(staging=false)")
    public void bindLocalProvider(IRepositoryProvider<LocalRepository> provider) {
        localProvider = provider;
    }

    @Unbind
    public void unbindLocalProvider(IRepositoryProvider<LocalRepository> provider) {
        localProvider = null;
    }

    @Bind(filter="(staging=true)")
    public void bindStagingProvider(IRepositoryProvider<LocalRepository> provider) {
        stagingProvider = provider;
    }

    @Unbind
    public void unbindStagingProvider(IRepositoryProvider<LocalRepository> provider) {
        stagingProvider = null;
    }

    @Bind(optional=true, aggregate=true)
    public void bindRemoteProvider(IRepositoryProvider<RemoteRepository> provider) {
        remoteProviders.add(provider);
    }

    @Unbind
    public void unbindRemoteProvider(IRepositoryProvider<RemoteRepository> provider) {
        remoteProviders.remove(provider);
    }


    // getter / setter
    public IRepositoryProvider<LocalRepository> getLocalProvider() {
        return localProvider;
    }

    public void setLocalProvider(IRepositoryProvider<LocalRepository> localProvider) {
        this.localProvider = localProvider;
    }

    public IRepositoryProvider<LocalRepository> getStagingProvider() {
        return stagingProvider;
    }

    public void setStagingProvider(IRepositoryProvider<LocalRepository> stagingProvider) {
        this.stagingProvider = stagingProvider;
    }

    public CopyOnWriteArraySet<IRepositoryProvider<RemoteRepository>> getRemoteProviders() {
        return remoteProviders;
    }

    public void setRemoteProviders(CopyOnWriteArraySet<IRepositoryProvider<RemoteRepository>> providers) {
        this.remoteProviders = providers;
    }

}
