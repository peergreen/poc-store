package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;


/**
 * Interface defining an entity bean representing requirement of a petal
 */

public interface IRequirement {

    /**
     * @return the requirementId
     */
    int getRequirementId();


    /**
     * @param requirementId the requirementId to set
     */
    void setRequirementId(int requirementId);


    /**
     * @return the filter
     */
    String getFilter();


    /**
     * @param filter the filter to set
     */
    void setFilter(String filter);


    /**
     * @return the petals
     */
    List<IPetal> getPetals();


    /**
     * @param petals the petals to set
     */
    void setPetals(List<IPetal> petals);

}
