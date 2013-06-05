package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

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

    /**
     * Method to add a new instance of vendor in the database 
     * The attribute petals are null when creating the group
     * 
     * @param vendorName the vendor's name
     * @param vendorDescription the vendor's description
     * 
     * @return A new instance of Vendor
     */
    @Override
    public Vendor addVendor(String vendorName, String vendorDescription) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to delete a vendor with the name 'vendorName'
     * 
     * @param vendorName the name of the vendor to delete
     */
    @Override
    public void deleteVendor(String vendorName) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all the petals provided by
     * the vendor with the name 'vendorName'
     * 
     * @param vendorName the vendor's name to which we collect petals which he provides
     * 
     * @return A collection of petals which are provided by the vendor with the name 'vendorName'
     */
    @Override
    public Collection<Petal> collectPetals(String vendorName) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }


}
