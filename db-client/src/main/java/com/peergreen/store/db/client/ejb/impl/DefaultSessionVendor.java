package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Class defining all vendor related operations:
 * <ul>
 *      <li>Create vendor on database</li>
 *      <li>Delete a vendor from the database</li>
 *      <li>Find a vendor in the database thanks to its name</li>
 *      <li>Collect the petal provided by a vendor</li>
 *      <li>Add a petal to a vendor provided petals list</li>
 *      <li>Remove a petal from a vendor provided petals list</li>
 *      <li>Retrieve all vendors present in database</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultSessionVendor implements ISessionVendor {

    private EntityManager entityManager;
    private ISessionPetal petalSession;

    /**
     * Method to add a new instance of vendor in the database.<br />
     * Throws EntityAlreadyExistsException if a vendor with
     *  same name already exists in database.<br />
     * 
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     * @return created vendor instance
     * @throws EntityAlreadyExistsException 
     */
    @Override
    public Vendor addVendor(String vendorName, String vendorDescription) throws EntityAlreadyExistsException {

        Vendor vendor = entityManager.find(Vendor.class, vendorName);
        if (vendor != null) {
            throw new EntityAlreadyExistsException("A vendor with name " + vendorName + " already exists in database.");
        } else {
            vendor = new Vendor(vendorName, vendorDescription);
            entityManager.persist(vendor);
            return vendor;
        }
    }

    /**
     * Method to delete a vendor from the database.
     * 
     * @param vendorName name of the vendor to delete
     */
    @Override
    public void deleteVendor(String vendorName) {
        Vendor vendor = entityManager.find(Vendor.class, vendorName);
        if(vendor!=null){
            //Collect all the petals provided by the vendor 
            Collection<Petal> petals = vendor.getPetals();
            Iterator<Petal> it = petals.iterator();
            //Remove each of them from the database 
            while(it.hasNext()) {
                Petal p = it.next();
                petalSession.deletePetal(p);
            }

            //Then remove the vendor from the database
            entityManager.remove(vendor);
        }
    }


    /**
     * Method to find a vendor thanks to its name in the database.
     * 
     * @param vendorName name of the vendor to find
     * @return found vendor instance or {@literal null} if no instance found
     */
    @Override
    public Vendor findVendor(String vendorName) {
        return entityManager.find(Vendor.class, vendorName);
    }

    /**
     * Method to collect all petals provided by a vendor with a specified name.<br />
     * Throws an NoEntityFoundException if no vendor correspond to the given vendor name.
     *  
     * @param vendorName the vendor's name to which we collect petals which he provides
     * @return collection of petals which are provided by the specified vendor
     * @throws NoEntityFoundException
     */
    @Override
    public Collection<Petal> collectPetals(String vendorName) throws NoEntityFoundException {
        Vendor vendor = entityManager.find(Vendor.class, vendorName);
        if (vendor != null) {
            return vendor.getPetals();
        } else {
            throw new NoEntityFoundException("No vendor with name " + vendorName + " found in database.");
        }
    }

    /**
     * Method to add a new petal to those provided by a vendor.
     * 
     * @param vendor vendor to which add a new petal to provided petals list
     * @param petal petal to add to provided petals list
     * @return modified vendor entity with update provided petals list
     * @throws NoEntityFoundException
     */
    @Override
    public Vendor addPetal(Vendor vendor, Petal petal) throws NoEntityFoundException{
        // retrieve attached vendor
        Vendor v = findVendor(vendor.getVendorName());
        if(v!=null){
            // retrieve attached petal
            Petal p = petalSession.findPetal(petal.getVendor(), petal.getArtifactId(), petal.getVersion());
            v.getPetals().add(p);
            return entityManager.merge(v);
        }
        else{
            throw new NoEntityFoundException("Vendor " + vendor.getVendorName() + " doesn't exist in database.");
        }
    }

    /**
     * Method to delete a petal from those provided by a vendor.
     * 
     * @param vendor vendor from which remove a petal
     * @param petal petal to remove from the vendor list of provided petals
     * @return modified vendor entity with update provided petals list
     * @throws NoEntityFoundException
     */
    @Override
    public Vendor removePetal(Vendor vendor, Petal petal)throws NoEntityFoundException {
        // retrieve attached vendor
        Vendor v = findVendor(vendor.getVendorName());
        if(v!=null){
            // retrieve attached petal
            Petal p = petalSession.findPetal(petal.getVendor(), petal.getArtifactId(), petal.getVersion());
            v.getPetals().remove(p);
            return entityManager.merge(vendor);
        }
        else{
            throw new NoEntityFoundException("Vendor " + vendor.getVendorName() + " doesn't exist in database.");
        }

    }

    /**
     * Method to manually set the entity manager.
     * 
     * @param entityManager used
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to collect all existing vendor in database.
     * 
     * @return collection of all existing vendors in database
     */
    @Override
    public Collection<Vendor> collectVendors() {
        Query query = entityManager.createNamedQuery("Vendor.findAll");
        @SuppressWarnings("unchecked")
        List<Vendor> vendorList = query.getResultList();
        Set<Vendor> vendorSet = new HashSet<Vendor>(vendorList);
        return vendorSet;   
    }

    @EJB
    public void setPetalSession(ISessionPetal petalSession) {
        this.petalSession = petalSession;
    }

}
