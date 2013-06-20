package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

public class CapabilityId implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -3938463371051240633L;
    
    private String capabilityName;
   
    private int capabilityId;
    
    private String version;
    
    public CapabilityId(){
        
    }
    
    public CapabilityId(String name, int id, String version){
        
        this.capabilityName = name;
        this.capabilityId = id;
        this.version = version; 
    }

    /**
     * @return the capabilityName
     */
    public String getCapabilityName() {
        return capabilityName;
    }

    /**
     * @param capabilityName the capabilityName to set
     */
    public void setCapabilityName(String capabilityName) {
        this.capabilityName = capabilityName;
    }

    /**
     * @return the capabilityId
     */
    public int getCapabilityId() {
        return capabilityId;
    }

    /**
     * @param capabilityId the capabilityId to set
     */
    public void setCapabilityId(int capabilityId) {
        this.capabilityId = capabilityId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    
    
}