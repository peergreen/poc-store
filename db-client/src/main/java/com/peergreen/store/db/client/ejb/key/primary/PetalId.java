package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.peergreen.store.db.client.ejb.entity.api.IVendor;
import com.peergreen.store.db.client.ejb.key.primary.api.IPetalId;

@Embeddable
public class PetalId implements IPetalId, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IVendor vendor;
	String artifactId;
	String version; 


	public PetalId(IVendor vendor, String artifactId,String version){
		this.vendor = vendor;
		this.artifactId = artifactId;
		this.version = version;

	}
	
	public void setVendor(IVendor vendor2) {
		this.vendor = vendor2;
	}

	public void setArtifactId(String artifactId2) {
		this.artifactId = artifactId2;
	}

	public void setVersion(String version2) {
		this.version = version2;		
	}

	public IVendor getVendor()
	{
		return this.vendor;
	}

	public String getArtifactId()
	{
		return this.artifactId;
	}

	public String getVersion() {
		return this.version;
	}

	public boolean equals(Object obj) {
		boolean resultat = false;
		return resultat;
	}

	public int hashCode() {
		int resultat = 0 ; 
		return resultat;
	}

}

