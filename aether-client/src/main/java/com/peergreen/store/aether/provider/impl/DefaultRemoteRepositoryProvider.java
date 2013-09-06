package com.peergreen.store.aether.provider.impl;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.eclipse.aether.repository.RemoteRepository;

import com.peergreen.store.aether.provider.IRemoteRepositoryProvider;

/**
 * Class defining methods for remote repository provider.
 */
@Component
@Provides
public class DefaultRemoteRepositoryProvider
extends DefaultRepositoryProvider<RemoteRepository>
implements IRemoteRepositoryProvider<RemoteRepository> {

    // retrieved from Config Admin
    @ServiceProperty
    private String name;
    @Property
    private String tmpPath;
    private String path;

    /**
     * Method to retrieve a petal's binary from the repository.
     * 
     * @param vendor petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return
     */
    @Override
    public File retrievePetal(
            String vendor,
            String artifactId,
            String version) {

        File tmpFile = new File(tmpPath + "/"
                + vendor + ":" + artifactId + ":" + version + ".jar");

        /*
         * Need Jersey to work
        Client client = ClientBuilder.newClient();

        // entity getContent => there might be an InputStream
        Response response = client.target(path + "/petal/local/" + vendor +
                "/" + artifactId + "/" + version)
                .request(MediaType.MULTIPART_FORM_DATA).get();

        BufferedInputStream bis = new BufferedInputStream(
                (InputStream)response.getEntity());

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
        } catch (FileNotFoundException e) {
            // can't create file to store petal to tmp directory
            e.printStackTrace();
            return null;
        }

        int bytesRead = 0;
        byte[] buffer = new byte[1024];

        // Keep reading until there is no more content left.
        // -1 (EOF) is returned when end of file is reached.
        try {
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         */

        return tmpFile;
    }

    /**
     * Method to retrieve name associated with remote repository.
     *
     * @return remote repository name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set name associated with the remote store.
     *
     * @param name name to set to the store
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to retrieve temporary path where petals are stored.
     *
     * @return temporary path where petals are stored
     */
    public String getTmpPath() {
        return tmpPath;
    }

    /**
     * Method to retrieve repository path.
     *
     * @return path to the repository (remote or local)
     */
    public String getPath() {
        return path;
    }

    /**
     * Method to set path of the remote store
     *
     * @param path path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}
