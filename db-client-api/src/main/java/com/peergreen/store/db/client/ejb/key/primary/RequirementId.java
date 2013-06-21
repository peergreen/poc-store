package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;


public class RequirementId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4757815541000575808L;

    private String requirementName;
  
    private int requirementId;

    public RequirementId() {
        
    }
    
    
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
