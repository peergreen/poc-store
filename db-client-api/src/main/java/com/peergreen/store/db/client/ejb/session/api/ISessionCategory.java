package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public interface ISessionCategory {

    /**
     * Method to add a new category in the database.<br />
     * Throws EntityAlreadyExistsException when a category
     *  with same name already exist in the database.<br />
     * 
     * @param categoryName category name
     * @return created Category instance
     * @throws EntityAlreadyExistsException
     */
    Category addCategory(String categoryName) throws EntityAlreadyExistsException;

    /**
     * Method to delete the category with the name categoryName
     * 
     * @param categoryName the name of the category to delete
     * @return 
     */
    Category deleteCategory(String categoryName);

    /**
     * Method to find the category with the name categoryName
     * 
     * @param categoryName the name of the category to find
     * 
     * @return the category with the name categoryName
     */
    Category findCategory(String categoryName);

    /**
     * Method to collect all the petals which belongs to the specified category.<br />
     * Throws {@link NoEntityFoundException} when Category doesn't exist in database.
     * 
     * @param categoryName name of the category whose petals are collected
     * @return collection of petals which belongs to this category
     * @throws NoEntityFoundException
     */
    Collection<Petal> collectPetals(String categoryName) throws NoEntityFoundException;

    /**
     * Method to add a new petal to a category
     * 
     * @param category the category to which add a new petal
     * @param petal the petal to add to the category
     * 
     * @return A new category with new petals included the petal added 
     * @throws NoEntityFoundException
     */
    Category addPetal(Category category, Petal petal) throws NoEntityFoundException;

    /**
     * Method to remove a petal from a category
     * 
     * @param category the category to which remove a petal
     * @param petal the petal to remove from the category
     * 
     * 
     * @return A new category with petals excluded the petal removed 
     * @throws NoEntityFoundException
     */
    Category removePetal(Category category, Petal petal) throws NoEntityFoundException;

    /**
     * Method to collect all the category in the database
     * 
     * @return A collection of cetegories in the database
     */
    Collection<Category> collectCategories();

}
