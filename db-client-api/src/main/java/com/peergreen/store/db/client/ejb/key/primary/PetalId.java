package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;


public class PetalId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String vendorName;
	private String artifactId;
	private String version; 

	public PetalId(){
		
	}
	
	public PetalId(String vendor, String artifactId,String version){
		this.vendorName = vendor;
		this.artifactId = artifactId;
		this.version = version;

	}
	
	/**
	 * Method to set the petal's vendor
	 * 
	 * @param vendor the vendor to set for the petal
	 */
	public void setVendor(String vendor2) {
		this.vendorName = vendor2;
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
	public String getVendor()
	{
		return this.vendorName;
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

