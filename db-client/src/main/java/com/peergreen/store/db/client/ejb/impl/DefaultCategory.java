package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


/**
 * Class defining an entity session to manage the entity Category
 * <ul>
 *      <li>Create a category on database</li>
 *      <li>Remove a category from the database</li>
 *      <li>Find a category from the database</li>
 *      <li>Collect all the petals that belongs to a category</li>>
 *      <li>Add a petal to those which belongs to a category</li>
 *      <li>Remove a petal from those which belongs to a category</li>
 *      <li>Collect all categories on database</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultCategory implements ISessionCategory {

    private EntityManager entityManager;

    private ISessionPetal petalSession;
    
    @PersistenceContext 
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a new category in the database.<br />
     * Throws EntityAlreadyExistsException when a category
     *  with same name already exist in the database.<br />
     * 
     * @param categoryName category name
     * @return created Category instance
     * @throws EntityAlreadyExistsException
     */
    @Override
    public Category addCategory(String categoryName) throws EntityAlreadyExistsException {
        Category temp = findCategory(categoryName);

        if (temp != null ) {      
            throw new EntityAlreadyExistsException("Category with name " + categoryName + " already exists in database.");
        } else {
            Category category = new Category(categoryName);  
            entityManager.persist(category);  
            return category ;
        }
    }

    /**
     * Method to delete a specified category.
     * 
     * @param categoryName name of the category to delete
     */
    @Override
    public void deleteCategory(String categoryName) {
        Category temp = findCategory(categoryName);
        if (temp != null) {
            entityManager.remove(temp);
        }
    }

    /**
     * Method to find a specified category.<br />
     * 
     * @param categoryName name of the category to find
     * @return category with the name categoryName
     */
    @Override
    public Category findCategory(String categoryName){
        Query q = entityManager.createNamedQuery("CategoryByName");
        q.setParameter("name", categoryName);
        Category result;
        try {
            result = (Category)q.getSingleResult();
        } catch(NoResultException e) {
            result = null;
        }
        return result;
    }

    /**
     * Method to collect all the petals which belongs to the specified category.<br />
     * Throws {@link NoEntityFoundException} when Category doesn't exist in database.
     * 
     * @param categoryName name of the category whose petals are collected
     * @return collection of petals which belongs to this category
     * @throws NoEntityFoundException
     */
    @Override
    public Collection<Petal> collectPetals(String categoryName) throws NoEntityFoundException {
        Category category = findCategory(categoryName);

        if (category != null) {
            return category.getPetals();
        } else{
            throw new NoEntityFoundException("Category " + categoryName + " does not exist in database.");
        }
    }

    /**
     * Method to add a new petal to the list of petals associated to this category.
     * 
     * @param category category to which add a petal
     * @param petal petal to add to the category
     * 
     * @return modified Category instance (updated list of associated petals)
     */
    @Override
    public Category addPetal(Category category, Petal petal) {
        // retrieve attached category entity
        Category c = findCategory(category.getCategoryName());
        // retrieve attached petal entity
        Petal p = petalSession.findPetal(petal.getVendor(), petal.getArtifactId(), petal.getVersion());  
        
        c.getPetals().remove(p);
        return entityManager.merge(c);
    }

    /**
     * Method to remove a petal from a category.
     * 
     * @param category category from which remove a petal
     * @param petal petal to remove from the category
     * @return modified Category instance (updated list of associated Petal instances)
     */
    @Override
    public Category removePetal(Category category, Petal petal) {
        // retrieve attached category
        Category c = findCategory(category.getCategoryName());
        // retrieve attached petal entity
        Petal p = petalSession.findPetal(petal.getVendor(), petal.getArtifactId(), petal.getVersion());
        
        c.getPetals().remove(p);
        return entityManager.merge(c);
    }

    /**
     * Method to collect all categories present in database
     * 
     * @return collection of categories present in database
     */
    @Override
    public Collection<Category> collectCategories() {
        Query query = entityManager.createNamedQuery("Category.findAll");
        @SuppressWarnings("unchecked")
        List<Category> catList = query.getResultList();
        Set<Category> categorySet = new HashSet<Category>(catList);
        return categorySet;
    }
    
    @EJB
    public void setSessionPetal(ISessionPetal sessionPetal) {
        this.petalSession = sessionPetal;
    }
}