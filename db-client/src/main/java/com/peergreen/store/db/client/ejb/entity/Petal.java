package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="idPetalSeq", initialValue=1, allocationSize=50)
public class Petal {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idPetalSeq")
    private int petalId;
    private String groupId;
    private String artifactid;
    private String version;
    private String description; 
    private Category category;
    private List<Requirement> requirements;
    private List<Capability> capabilities;

    /**
     * @return the petalId
     */
    public int getPetalId() {
        return petalId;
    }

    /**
     * @param petalId the petalId to set
     */
    public void setPetalId(int petalId) {
        this.petalId = petalId;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the artifactid
     */
    public String getArtifactid() {
        return artifactid;
    }

    /**
     * @param artifactid the artifactid to set
     */
    public void setArtifactid(String artifactid) {
        this.artifactid = artifactid;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }
    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the requirements
     */
    public List<Requirement> getRequirements() {
        return requirements;
    }

    /**
     * @param requirements the requirements to set
     */
    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    /**
     * @return the capabilities
     */
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities the capabilities to set
     */
    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    } 

}
