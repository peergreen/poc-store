package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a petal
 */

public interface Petal {


    /**
     * @return the petalId
     */
    public int getPetalId();


    /**
     * @param petalId the petalId to set
     */
    public void setPetalId(int petalId);


    /**
     * @return the groupId
     */
    public String getGroupId();


    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId);


    /**
     * @return the artifactid
     */
    public String getArtifactid();


    /**
     * @param artifactid the artifactid to set
     */
    public void setArtifactid(String artifactid);


    /**
     * @return the version
     */
    public String getVersion();


    /**
     * @param version the version to set
     */
    public void setVersion(String version);


    /**
     * @return the description
     */
    public String getDescription();


    /**
     * @param description the description to set
     */
    public void setDescription(String description);


    /**
     * @return the category
     */
    public Category getCategory();


    /**
     * @param category the category to set
     */
    public void setCategory(Category category);


    /**
     * @return the requirements
     */
    public List<Requirement> getRequirements();


    /**
     * @param requirements the requirements to set
     */
    public void setRequirements(List<Requirement> requirements);


    /**
     * @return the capabilities
     */
    public List<Capability> getCapabilities();


    /**
     * @param capabilities the capabilities to set
     */
    public void setCapabilities(List<Capability> capabilities);

}
