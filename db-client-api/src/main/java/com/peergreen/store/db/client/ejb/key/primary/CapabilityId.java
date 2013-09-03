package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

/**
 * Composite primary key of a capability.
 */
public class CapabilityId implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = -3938463371051240633L;

    /**
     * Name of the capability.
     */
    private String capabilityName;

    /**
     * Generated id of the capability.
     */
    private int capabilityId;

    /**
     * Version of the capability.
     */
    private String version;

    /**
     * Namespace of the capability.
     */
    private String namespace;

    /**
     * Default Constructor.
     */
    public CapabilityId() {

    }

    /**
     * Default Constructor with parameters.
     * @param name name of the capability
     * @param id if of the capability
     * @param version version of the capability
     * @param namespace namespace of the capability
     */
    public CapabilityId(String name, int id, String version, String namespace) {

        this.capabilityName = name;
        this.capabilityId = id;
        this.version = version;
        this.setNamespace(namespace);
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

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


}
