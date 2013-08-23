package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining all vendor related operations:
 * <ul>
 *      <li>Add a new vendor to the database</li>
 *      <li>Remove a vendor from the database</li>
 *      <li>Find a vendor in the database thanks to its name</li>
 *      <li>Retrieve all petals provided by a specific vendor</li>
 *      <li>Add a petal to a vendor provided petals list</li>
 *      <li>Remove a petal from a vendor provided petals list</li>
 *      <li>Retrieve all vendors present in database</li>
 * </ul>
 * 
 */
public interface ISessionVendor {

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
    Vendor addVendor(String vendorName, String vendorDescription) throws EntityAlreadyExistsException;

    /**
     * Method to delete a vendor from the database.
     * 
     * @param vendorName name of the vendor to delete
     */
    Vendor deleteVendor(String vendorName);

    /**
     * Method to find the vendor with the name 'vendorName'
     * 
     * @param vendorName the name of the vendor to find
     * 
     * @return the vendor with the name 'vendorName'
     */
    Vendor findVendor(String vendorName);

    /**
     * Method to collect all petals provided by a vendor with a specified name.<br />
     * Throws an NoEntityFoundException if no vendor correspond to the given vendor name.
     *  
     * @param vendorName the vendor's name to which we collect petals which he provides
     * @return collection of petals which are provided by the specified vendor
     * @throws NoEntityFoundException
     */
    Collection<Petal> collectPetals(String vendorName) throws NoEntityFoundException;

    /**
     * Method to add a new petal to those provided by a vendor.
     * 
     * @param vendor vendor to which add a new petal to provided petals list
     * @param petal petal to add to provided petals list
     * @return modified vendor entity with update provided petals list
     * @throws NoEntityFoundException
     */
    Vendor addPetal(Vendor vendor, Petal petal) throws NoEntityFoundException;

    /**
     * Method to delete a petal from those provided by a vendor.
     * 
     * @param vendor vendor from which remove a petal
     * @param petal petal to remove from the vendor list of provided petals
     * @return modified vendor entity with update provided petals list
     * @throws NoEntityFoundException
     */
    Vendor removePetal(Vendor vendor, Petal petal) throws NoEntityFoundException;

    /**
     * Method to collect all existing vendor in database.
     * 
     * @return collection of all existing vendors in database
     */
    Collection<Vendor> collectVendors();
}