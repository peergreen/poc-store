package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;

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
public class DefaultSessionCategory implements ISessionCategory {

    private EntityManager entityManager;

    private ISessionPetal petalSession;

    private static Log logger = LogFactory.getLog(DefaultSessionCategory.class);

    @PersistenceContext 
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Set entity session which manage the entity Petal
     * @param sessionPetal Instance of sessionPetal 
     */
    @EJB
    public void setSessionPetal(ISessionPetal sessionPetal) {
        this.petalSession = sessionPetal;
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
    public Category addCategory(String categoryName)
            throws EntityAlreadyExistsException {
        Category temp = findCategory(categoryName);

        if (temp != null ) {      
            throw new EntityAlreadyExistsException("Category with name "
                    + categoryName + " already exists in database.");
        } else {
            Category category = new Category(categoryName);  
            entityManager.persist(category);  
            return category ;
        }
    }

    /**
     * Method to delete a specified category.<br />
     * If the category to delete doesn't exist, we return {@literal null}.<br />
     * 
     * @param categoryName name of the category to delete
     * @return Category instance deleted or
     * <em>null</em> if category can't be deleted
     */
    @Override
    public Category deleteCategory(String categoryName) {
        Category temp = findCategory(categoryName);
        if (temp != null) {
            //Remove all petals from this category 
            Collection<Petal> petals = temp.getPetals();
            Iterator<Petal> it = petals.iterator();
            while(it.hasNext()){
                Petal p = it.next();
                try {
                    //Then each petal belongs to no specific category
                    //TODO create a default category ??? 
                    petalSession.addCategory(p, new Category());
                } catch (NoEntityFoundException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            //Then remove the category from the database
            entityManager.remove(temp);
            return temp;
        }
        else{
            return temp; 
        }
    }

    /**
     * Method to find a specified category.<br />
     * If the category to find doesn't exist, we return {@literal null}.<br />
     * 
     * @param categoryName name of the category to find
     * @return category with the name categoryName or
     * <em>null</em> if category doesn't exist
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
     * Method to collect all the petals which belongs 
     * to the specified category.<br />
     * Throws {@link NoEntityFoundException} when Category doesn't exist 
     * in database.
     * 
     * @param categoryName name of the category whose petals are collected
     * @return collection of petals which belongs to this category
     * @throws NoEntityFoundException
     */
    @Override
    public Collection<Petal> collectPetals(String categoryName) 
            throws NoEntityFoundException {
        Category category = findCategory(categoryName);
        if (category != null) {
            return new HashSet<>(category.getPetals());
        } else{
            throw new NoEntityFoundException("Category " + categoryName 
                    + " does not exist in database.");
        }
    }

    /**
     * Method to add a new petal to the list of petals
     *  associated to this category.
     * 
     * @param category category to which add a petal
     * @param petal petal to add to the category
     * 
     * @return modified Category instance (updated list of associated petals)
     */
    @Override
    public Category addPetal(Category category, Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached category entity
        Category c = findCategory(category.getCategoryName());
        if(c!=null){
            // retrieve attached petal entity
            Petal p = petalSession.findPetal(petal.getVendor(), 
                    petal.getArtifactId(), petal.getVersion());  
            c.getPetals().add(p);
            return entityManager.merge(c);

        } else{
            throw new NoEntityFoundException("Category " + 
                    category.getCategoryName() + " does not exist in database.");
        }

    }

    /**
     * Method to remove a petal from a category.
     * 
     * @param category category from which remove a petal
     * @param petal petal to remove from the category
     * @return modified Category instance (updated 
     * of associated Petal instances)
     * @throws NoEntityFoundException
     */
    @Override
    public Category removePetal(Category category, Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached category
        Category c = findCategory(category.getCategoryName());
        if(c!=null){
            // retrieve attached petal entity
            Petal p = petalSession.findPetal(petal.getVendor(), 
                    petal.getArtifactId(), petal.getVersion());

            c.getPetals().remove(p);
            return entityManager.merge(c);
        } else{
            throw new NoEntityFoundException("Category " + 
                    category.getCategoryName() + " does not exist in database.");
        }
    }

    /**
     * Method to collect all categories in the database
     * 
     * @return collection of categories in the database
     */
    @Override
    public Collection<Category> collectCategories() {
        Query query = entityManager.createNamedQuery("Category.findAll");
        @SuppressWarnings("unchecked")
        List<Category> catList = query.getResultList();
        Set<Category> categorySet = new HashSet<Category>(catList);
        return categorySet;
    }
}