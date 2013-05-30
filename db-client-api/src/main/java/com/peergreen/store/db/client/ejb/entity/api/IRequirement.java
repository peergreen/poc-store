package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;


/**
 * Interface defining an entity bean representing requirement of a petal
 */

public interface IRequirement {

    /**
     * @return the requirementId
     */
    public int getRequirementId();


    /**
     * @param requirementId the requirementId to set
     */
    public void setRequirementId(int requirementId);


    /**
     * @return the filter
     */
    public String getFilter();


    /**
     * @param filter the filter to set
     */
    public void setFilter(String filter);


    /**
     * @return the petals
     */
    public List<IPetal> getPetals();


    /**
     * @param petals the petals to set
     */
    public void setPetals(List<IPetal> petals);

}
