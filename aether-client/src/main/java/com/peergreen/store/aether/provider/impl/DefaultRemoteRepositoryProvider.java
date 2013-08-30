package com.peergreen.store.aether.provider.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.apache.felix.ipojo.annotations.Validate;
import org.eclipse.aether.repository.RemoteRepository;


/**
 * Class defining methods for remote repository provider.
 */
@Component
@Provides
public class DefaultRemoteRepositoryProvider extends
DefaultRepositoryProvider<RemoteRepository> {

    // retrieved from Config Admin
    @ServiceProperty
    private String name;
    @Property
    private String path;
    @Property
    private String tmpPath;

    public DefaultRemoteRepositoryProvider() {
        
    }
    
    public DefaultRemoteRepositoryProvider(String url) {
        this.path = url;
    }
    
    /**
     * Method to build up requirements to use Aether with remote repository.
     */
    @Validate
    @Override
    public void init() {

    }

    /**
     * Method to retrieve a petal's binary from the repository.
     * 
     * @param vendor petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return
     */
    @Override
    public File retrievePetal(String vendor,
            String artifactId,
            String version) {
        // TODO
        ClassLoader classloaderOld = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(DefaultRemoteRepositoryProvider.class.getClassLoader());
        
        try {
            Client client = ClientBuilder.newClient();
    
            // entity getContent => il devrait y avoir un inputStream
            Response response = client.target(path + "/petal/local/" + vendor +
                    "/" + artifactId + "/" + version)
                    .request(MediaType.MULTIPART_FORM_DATA).get();
    
            BufferedInputStream bis = new BufferedInputStream(
                    (InputStream)response.getEntity());
    
            // TODO change to dynamic name
            File tmpFile = new File(tmpPath + "/test.jar");
    
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
            
            return tmpFile;
        } finally {
            Thread.currentThread().setContextClassLoader(classloaderOld);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
