package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;


/**
 * Entity Bean representing the category of a petal  
 */
public interface ICategory {

    /**
     * @return the categoryname
     */
    String getCategoryname();


    /**
     * @param categoryname the categoryname to set
     */
    void setCategoryname(String categoryname);


    /**
     * @return the petals
     */
    List<IPetal> getPetals();


    /**
     * @param petals the petals to set
     */
    void setPetals(List<IPetal> petals);
}
