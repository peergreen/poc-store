package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity Bean representing in the database the requirement of a petal  
 */
@Entity
public class Requirement {
    @Id
    private int requirementId;
    private String filter;
    private List<Petal> petals;

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

    /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @return the petals
     */
    public List<Petal> getPetals() {
        return petals;
    }

    /**
     * @param petals the petals to set
     */
    public void setPetals(List<Petal> petals) {
        this.petals = petals;
    }

}
