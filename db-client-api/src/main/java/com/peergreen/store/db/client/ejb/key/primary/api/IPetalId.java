package com.peergreen.store.db.client.ejb.key.primary.api;

import com.peergreen.store.db.client.ejb.entity.api.IVendor;

public interface IPetalId {

	IVendor getVendor();

	String getArtifactId();

	String getVersion();
	
	void setVendor(IVendor vendor);
	void setArtifactId(String artifactId);
	void setVersion(String version);

	boolean equals(Object obj);

	int hashCode();

}
