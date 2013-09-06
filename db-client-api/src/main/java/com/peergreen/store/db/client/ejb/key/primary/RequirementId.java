package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

/**
 * Composite primary key of a requirement.
 */
public class RequirementId implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = -4757815541000575808L;

    /**
     * Name of the requirement.
     */
    private String requirementName;

    /**
     * Generated id of the requirement.
     */
    private int requirementId;

    /**
     * Default constructor.
     */
    public RequirementId() {

    }

    /**
     * Constructs an instance of the composite key 
     * with the specified parameters.
     * @param requirementName name of requirement
     * @param requirementId id of the requirement
     */
    public RequirementId(String requirementName, int requirementId) {
        super();
        this.requirementName = requirementName;
        this.requirementId = requirementId;
    }


    /**
     * @return the requirementName
     */
    public String getRequirementName() {
        return requirementName;
    }

    /**
     * @param requirementName the requirementName to set
     */
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    /**
     * @return the requirementId
     */
    public int getRequirementId() {
        return requirementId;
    }

    /**
     * @param requirementId the requirementId to set
     */
    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }
}
