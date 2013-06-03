package com.peergreen.store.db.client.ejb.key.primary.api;

import com.peergreen.store.db.client.ejb.entity.api.IVendor;

public interface IPetalId {

	IVendor getVendor();

	String getArtifactId();

	String getVersion();

	boolean equals(Object obj);
	
	int hashCode();

}
