package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
     * Method to add a new category in the database. It throws
     * an exception "EntityExistsException " when the entity already exist in the database
     * The attributes petals are null when creating the category
     * 
     * @param categoryName the category's name
     * @return A new instance of Category
     */
    @Override
    public Category addCategory(String categoryName) throws EntityExistsException{
        Category temp = findCategory(categoryName);

        if(temp != null ) {      
            throw new EntityExistsException();
        }

        else {
            Category category = new Category(categoryName);  
            entityManager.persist(category);  
            return category ;
        }
    }

    /**
     * Method to delete the category with the name categoryName.
     * It throws an IllegalArgumentException if the entity to remove
     * doesn't exist in the database.
     * 
     * @param categoryName the name of the category to delete
     */
    @Override
    public void deleteCategory(String categoryName) throws IllegalArgumentException{
        Category temp = findCategory(categoryName);

        if (temp != null){

            entityManager.remove(temp);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to find the category with the name categoryName.
     * It returns null if the category doesn't exist. 
     * 
     * @param categoryName the name of the category to find
     * 
     * @return the category with the name categoryName
     */
    @Override
    public Category findCategory(String categoryName)throws NoResultException {
        Query q = entityManager.createNamedQuery("CategoryByName");
        q.setParameter("name", categoryName);
        Category result;
        try{
            result = (Category)q.getSingleResult();
        }catch(NoResultException e){
            result = null;
        }
        return result;
    }

    /**
     * Method to collect all the petals which belongs to the category
     * with the name categoryName. It throws an IllegalArgumentException
     * when the category doesn't exists
     * 
     * @param categoryName the name of the category whose petals are collected
     *  
     * @return A collection of petals which belongs to this category
     */
    @Override
    public Collection<Petal> collectPetals(String categoryName) throws IllegalArgumentException{
        Category category = findCategory(categoryName);

        if(category != null){

            return category.getPetals();

        }
        else{
            throw new IllegalArgumentException();
        }

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
