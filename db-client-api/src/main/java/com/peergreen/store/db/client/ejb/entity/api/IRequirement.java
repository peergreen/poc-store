package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;


/**
 * Interface defining an entity bean representing requirement of a petal
 */

public interface IRequirement {

    /**
     * Method to retrieve the requirement's filter 
     * 
     * @return the filter of the requirement instance
     */
    String getFilter();


    /**
     * Method to set the requiremeent's filter
     * 
     * @param The filter to set
     */
    void setFilter(String filter);


    /**
     * Method to retrieve the petals which had this requirement
     * 
     * @return A list containing all the petals which had this requirement 
     */
    List<IPetal> getPetals();


    /**
     * Method to add new petals that have this requirement
     * 
     * @param A list of new petals that have this requirement
     */
    void setPetals(List<IPetal> petals);

}
