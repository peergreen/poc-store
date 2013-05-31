package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a petal
 */

public interface IPetal {


    /**
     * @return the petalId
     */
   int getPetalId();


    /**
     * @param petalId the petalId to set
     */
    void setPetalId(int petalId);


    /**
     * @return the groupId
     */
    String getGroupId();


    /**
     * @param groupId the groupId to set
     */
    void setGroupId(String groupId);


    /**
     * @return the artifactid
     */
    String getArtifactid();


    /**
     * @param artifactid the artifactid to set
     */
    void setArtifactid(String artifactid);


    /**
     * @return the version
     */
    String getVersion();


    /**
     * @param version the version to set
     */
    void setVersion(String version);


    /**
     * @return the description
     */
    String getDescription();


    /**
     * @param description the description to set
     */
    void setDescription(String description);


    /**
     * @return the category
     */
    ICategory getCategory();


    /**
     * @param category the category to set
     */
    void setCategory(ICategory category);


    /**
     * @return the requirements
     */
    List<IRequirement> getRequirements();


    /**
     * @param requirements the requirements to set
     */
    void setRequirements(List<IRequirement> requirements);


    /**
     * @return the capabilities
     */
    List<ICapability> getCapabilities();


    /**
     * @param capabilities the capabilities to set
     */
    void setCapabilities(List<ICapability> capabilities);

}
