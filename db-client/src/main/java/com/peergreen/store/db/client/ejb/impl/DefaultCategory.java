package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;

/**
 * Class defining an entity session to manage the entity Category
 * <ul>
 *      <li>Create a category on database</li>
 *      <li>Remove a category from the database</li>
 *      <li>Find a category from the database</li>
 *      <li>Collect all the petals that belongs to a category</li>>
 *      <li>Add a petal to those which belongs to a category</li>
 *      <li>Remove a petal from those which belongs to a category</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultCategory implements ISessionCategory{


    private EntityManager entityManager = null;

    EntityManager getEntityManager() {
        return entityManager;
    }
    @PersistenceContext 
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a new category in the database
     * The attributes petals are null when creating the category
     * 
     * @param categoryName the category's name
     * @return A new instance of Category
     */
    @Override
    public Category addCategory(String categoryName) throws EntityExistsException {
        Category category = new Category();
        category.setCategoryName(categoryName);
        Set<Petal> petals = new HashSet<Petal>(); 
        category.setPetals(petals);
        entityManager.persist(category);    
        return category;
    }

    /**
     * Method to delete the category with the name categoryName
     * 
     * @param categoryName the name of the category to delete
     */
    @Override
    public void deleteCategory(String categoryName) {
        Category temp = entityManager.find(Category.class, categoryName);
        entityManager.remove(temp);
    }

    /**
     * Method to find the category with the name categoryName
     * 
     * @param categoryName the name of the category to find
     * 
     * @return the category with the name categoryName
     */
    @Override
    public Category findCategory(String categoryName) {
        return entityManager.find(Category.class, categoryName);
    }

    /**
     * Method to collect all the petals which belongs to the category
     * with the name categoryName
     * 
     * @param categoryName the name of the category whose petals are collected
     *  
     * @return A collection of petals which belongs to this category
     */
    @Override
    public Collection<Petal> collectPetals(String categoryName) {
        Category category = entityManager.find(Category.class, categoryName);

        return category.getPetals();
    }

    /**
     * Method to add a new petal to a category
     * 
     * @param category the category to which add a new petal
     * @param petal the petal to add to the category
     * 
     * @return A new category with new petals included the petal added 
     */
    @Override
    public Category addPetal(Category category, Petal petal) {
        Set<Petal> petals = category.getPetals();
        petals.add(petal);
        category.setPetals(petals);
        category =  entityManager.merge(category);
        return category;
    }

    /**
     * Method to remove a petal from a category
     * 
     * @param category the category to which remove a petal
     * @param petal the petal to remove from the category
     * 
     * 
     * @return A new category with petals excluded the petal removed 
     */
    @Override
    public Category removePetal(Category category, Petal petal) {
        category.getPetals().remove(petal);
        category =  entityManager.merge(category);
        return category;
    }

    /**
     * Method to collect all the category in the database
     * 
     * @return A collection of cetegories in the database
     */
    @Override
    public Collection<Category> collectCategories() {
        Query query = entityManager.createNamedQuery("Category.findAll");
        List<Category> catList = query.getResultList();
        Set<Category> categorySet = new HashSet<Category>(catList);
        return categorySet;
    }

}
