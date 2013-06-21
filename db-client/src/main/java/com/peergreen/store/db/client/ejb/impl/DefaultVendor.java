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

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;

/**
 * Class defining an entity session to manage the entity Vendor
 * <ul>
 *      <li>Create vendor on database</li>
 *      <li>Delete a vendor from the database</li>
 *      <li>Find a vendor from the database</li>
 *      <li>Collect the petal provided by a vendor</li>
 *      <li>Add a petal to those provided by a vendor</li>
 *      <li>Remove a petal from those provided by a vendor</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultVendor implements ISessionVendor {


    private EntityManager entityManager;


    /**
     * Method to add a new instance of vendor in the database.
     * It throws an exception "EntityExistsException " when the entity already
     * exist in the database
     * The attribute petals are null when creating the group.
     * 
     * @param vendorName the vendor's name
     * @param vendorDescription the vendor's description
     * 
     * @return A new instance of Vendor
     */
    @Override
    public Vendor addVendor(String vendorName, String vendorDescription) throws EntityExistsException{

        Vendor vendor = entityManager.find(Vendor.class, vendorName);
        if(vendor != null){
            throw new EntityExistsException();
        }
        else{
            vendor = new Vendor(vendorName, vendorDescription);
            entityManager.persist(vendor);
            return vendor;
        }
    }


    /**
     * Method to delete a vendor with the name 'vendorName'
     * It throws an IllegalArgumentException if the entity to remove
     * doesn't exist in the database.
     * 
     * @param vendorName the name of the vendor to delete
     */
    @Override
    public void deleteVendor(String vendorName)throws IllegalArgumentException {
        Vendor temp = entityManager.find(Vendor.class, vendorName);
        if(temp != null){
            entityManager.remove(temp);}
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to find the vendor with the name 'vendorName'
     * 
     * @param vendorName the name of the vendor to find
     * 
     * @return the vendor with the name 'vendorName'
     */
    @Override
    public Vendor findVendor(String vendorName) {
        Vendor vendor = entityManager.find(Vendor.class, vendorName);
        return vendor;
    }

    /**
     * Method to collect all the petals provided by
     * the vendor with the name 'vendorName'.It throws an IllegalArgumentException
     * if the entity to remove doesn't exist in the database.
     *  
     * @param vendorName the vendor's name to which we collect petals which he provides
     * 
     * @return A collection of petals which are provided by the vendor with the name 'vendorName'
     */
    @Override
    public Collection<Petal> collectPetals(String vendorName) throws IllegalArgumentException{

        Vendor temp = entityManager.find(Vendor.class, vendorName);
        if(temp != null){
            return temp.getPetals();
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to add a new petal to those provided by a vendor
     * 
     * @param vendor the vendor to which add a new petal to provide
     * @param petal the petal to provide by the vendor
     * 
     * @return A new vendor with the petal added in his list of petals provided
     */
    @Override
    public Vendor addPetal(Vendor vendor, Petal petal) {

        Set<Petal> petals = vendor.getPetals();
        petals.add(petal);
        vendor.setPetals(petals);
        vendor =  entityManager.merge(vendor);
        return vendor;
    }

    /**
     * Method to delete a petal from those provided by a vendor
     * 
     * @param vendor the vendor to which remove a petal
     * @param petal the petal to remove from the list of petals provided by the vendor
     * 
     * @return A new vendor with the petal removed from his list of petals provided
     */
    @Override
    public Vendor removePetal(Vendor vendor, Petal petal) {

        Set<Petal> petals = vendor.getPetals();
        petals.remove(petal);
        vendor.setPetals(petals);

        entityManager.merge(vendor);
        return vendor;
    }

    /**
     * Method to set manually the entity manager.
     * 
     * @param entityManager used
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Collection<Vendor> collectVendors() {
        Query query = entityManager.createNamedQuery("Vendor.findAll");
        List<Vendor> vendorList = query.getResultList();
        Set<Vendor> vendorSet = new HashSet<Vendor>(vendorList);
        return vendorSet;   
    }

}
