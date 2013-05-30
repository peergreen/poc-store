package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;


/**
 * Entity Bean representing the category of a petal  
 */
public interface Category {

    /**
     * @return the categoryId
     */
    public int getCategoryId();


    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId);


    /**
     * @return the categoryname
     */
    public String getCategoryname();


    /**
     * @param categoryname the categoryname to set
     */
    public void setCategoryname(String categoryname);


    /**
     * @return the petals
     */
    public List<Petal> getPetals();


    /**
     * @param petals the petals to set
     */
    public void setPetals(List<Petal> petals);
}
