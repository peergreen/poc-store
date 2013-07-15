package com.peergreen.store.controller.util;

import com.peergreen.store.db.client.ejb.entity.Vendor;

public class DependencyRequest {
    private Vendor vendor;
    private String artifactId;
    private String version;
    private String namspace;
    private int depth;

    public Vendor getVendor() {
        return vendor;
    }
    
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
    
    public String getArtifactId() {
        return artifactId;
    }
    
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getNamspace() {
        return namspace;
    }

    public void setNamspace(String namspace) {
        this.namspace = namspace;
    }

    public int getDepth() {
        return depth;
    }
    
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
