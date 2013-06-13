package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;

public interface ISessionCategory {

    /**
     * Method to add a new category in the database
     * The attributes petals are null when creating the category
     * 
     * @param categoryName the category's name
     * @return A new instance of Category
     */
    Category addCategory(String categoryName);

    /**
     * Method to delete the category with the name categoryName
     * 
     * @param categoryName the name of the category to delete
     */
    void deleteCategory(String categoryName);

    /**
     * Method to find the category with the name categoryName
     * 
     * @param categoryName the name of the category to find
     * 
     * @return the category with the name categoryName
     */
    Category findCategory(String categoryName);

    /**
     * Method to collect all the petals which belongs to the category
     * with the name categoryName
     * 
     * @param categoryName the name of the category whose petals are collected
     *  
     * @return A collection of petals which belongs to this category
     */
    Collection<Petal> collectPetals(String categoryName);

    /**
     * Method to add a new petal to a category
     * 
     * @param category the category to which add a new petal
     * @param petal the petal to add to the category
     * 
     * @return A new category with new petals included the petal added 
     */
    Category addPetal(Category category, Petal petal);

    /**
     * Method to remove a petal from a category
     * 
     * @param category the category to which remove a petal
     * @param petal the petal to remove from the category
     * 
     * 
     * @return A new category with petals excluded the petal removed 
     */
    Category removePetal(Category category, Petal petal);
    
    /**
     * Method to collect all the category in the database
     * 
     * @return A collection of cetegories in the database
     */
    Collection<Category> collectCategories();

}
