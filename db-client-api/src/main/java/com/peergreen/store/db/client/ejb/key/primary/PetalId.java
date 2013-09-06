package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

import com.peergreen.store.db.client.ejb.entity.Vendor;

/**
 * Composite primary key of a petal.
 */
public class PetalId implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = -9124101103451351551L;

    /**
     * Vendor of the petal.
     */
    private Vendor vendor;

    /**
     * ArtifactId of the petal.
     */
    private String artifactId;

    /**
     * Version of the petal.
     */
    private String version; 

    /**
     * Generated If of the petal.
     */
    private int pid;

    /**
     * Default Constructor.
     */
    public PetalId() {

    }

    /**
     * Constructs the composite key with the specified parameters.
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version 
     * @param id petal's id
     */
    public PetalId(Vendor vendor, String artifactId,String version, int id) {
        this.vendor = vendor;
        this.artifactId = artifactId;
        this.version = version;
        this.pid = id; 
    }

    /**
     * Method to set the petal's vendor
     * 
     * @param vendor the vendor to set for the petal
     */
    public void setVendor(Vendor vendor2) {
        this.vendor = vendor2;
    }

    /**
     * Method to set the petal's artifactId
     * 
     * @param artifactid An artifactId to set for the petal
     */
    public void setArtifactId(String artifactId2) {
        this.artifactId = artifactId2;
    }

    /**
     * Method to set the petal's version
     * 
     * @param version A version to set for the petal
     */
    public void setVersion(String version2) {
        this.version = version2;		
    }

    /**
     * Method to get the attribute Vendor
     * 
     * @return the vendor of the petal
     */
    public Vendor getVendor()
    {
        return this.vendor;
    }

    /**
     * Method to get the attribute ArtifactId
     * 
     * @return the artifactid of the petal 
     */
    public String getArtifactId()
    {
        return this.artifactId;
    }

    /**
     * Method to get the attribut version 
     * 
     * @return the version of the petal
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * @return the id
     */
    public int getId() {
        return pid;
    }

    /**
     * Method for comparing an object with an instance of petalId
     * 
     * @param obj the object to compare to the petalId instance
     * @return the comparison result
     */
    public boolean equals(Object obj) {
        boolean resultat = false;
        return resultat;
    }

    public int hashCode() {
        int resultat = 0 ; 
        return resultat;
    }

}