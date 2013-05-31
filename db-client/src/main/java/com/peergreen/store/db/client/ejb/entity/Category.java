package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity Bean representing the category of a petal  
 */

@Entity
public class Category {

    @Id
    private int categoryId;
    private String categoryname;
    private List<Petal> petals;
    
    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }
    
    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    /**
     * @return the categoryname
     */
    public String getCategoryname() {
        return categoryname;
    }
    
    /**
     * @param categoryname the categoryname to set
     */
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
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
