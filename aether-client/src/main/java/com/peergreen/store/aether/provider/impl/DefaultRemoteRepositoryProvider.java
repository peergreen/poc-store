package com.peergreen.store.aether.provider.impl;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.eclipse.aether.repository.RemoteRepository;

/**
 * Class defining methods for remote repository provider.
 */
@Component
@Provides
public class DefaultRemoteRepositoryProvider extends DefaultRepositoryProvider<RemoteRepository> {

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

        // TODO change to dynamic name
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

    public void setName(String name) {
        this.name = name;
    }

    public String getTmpPath() {
        return tmpPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
