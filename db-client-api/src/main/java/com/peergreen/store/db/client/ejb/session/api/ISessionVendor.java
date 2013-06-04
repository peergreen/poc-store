package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;


public interface ISessionVendor {

	Vendor addVendor(String vendorName, String vendorDescription);
	void deleteVendor(String vendorName);
	Vendor findVendor(String vendorName);
	Collection<Petal> collectPetals(String vendorName);
	Vendor addPetal(Petal petal);
	Vendor removePetal(Petal petal);

}
