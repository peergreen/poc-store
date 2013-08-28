package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

public class CategoryId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3163373459046602500L;

    private String categoryName;

    private int categoryId;

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

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
    
}
