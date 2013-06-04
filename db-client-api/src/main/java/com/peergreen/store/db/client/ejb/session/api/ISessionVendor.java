package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;


public interface ISessionVendor {

	IVendor addVendor(String vendorName, String vendorDescription);
	void deleteVendor(String vendorName);
	IVendor findVendor(String vendorName);
	Collection<IPetal> collectPetals(String vendorName);
	IVendor addPetal(IPetal petal);
	IVendor removePetal(IPetal petal);

}
