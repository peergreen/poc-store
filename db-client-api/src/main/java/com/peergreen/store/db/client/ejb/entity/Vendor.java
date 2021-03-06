package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
    @NamedQuery(
            name = "Vendor.findAll",
            query = "select v from Vendor v")
})
@Entity
public class Vendor {

    /**
     * Name of the vendor.
     */
    @Id
    @Column(name ="name")
    private String vendorName;

    /**
     * Description of the vendor.
     */
    private String vendorDescription;

    /**
     * Set of petals which are provided by this vendor.
     */
    @OneToMany(mappedBy="vendor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Petal> petals;

    /**
     * Default Constructor.
     */
    public Vendor() {

    }

    /**
     * Constructs a vendor with the specified parameters. Petals are empty.
     * @param vendorName name of the vendor to create
     * @param vendorDescription description of the vendor to create.
     */
    public Vendor(String vendorName, String vendorDescription) {
        super();
        this.vendorName = vendorName;
        this.vendorDescription = vendorDescription;
        Set<Petal> petals = new HashSet<Petal>();
        this.setPetals(petals);
    }

    /**
     * Method to get the name of the  vendor
     * 
     * @return the vendorDescription
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Method to get the description's vendor
     * 
     * @return the vendorDescription
     */
    public String getVendorDescription() {
        return vendorDescription;
    }

    /**
     * Method to set a description for the vendor 
     * 
     * @param vendorDescription the description to set for the vendor
     */
    public void setVendorDescription(String vendorDescription) {
        this.vendorDescription = vendorDescription;
    }

    /**
     * Method for retrieve all the petals provided by the vendor instance
     * 
     * @return Set containing petals that are provided by the vendor instance
     */
    public Set<Petal> getPetals() {
        return this.petals;
    }

    /**
     * Method to add petals to the Set of petals that are provided by the vendor instance
     * 
     * @param petals Set containing the petals to set
     */
    public void setPetals(Set<Petal> petals) {
        this.petals = petals;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return vendorName + "-" + vendorDescription;
    }

}
