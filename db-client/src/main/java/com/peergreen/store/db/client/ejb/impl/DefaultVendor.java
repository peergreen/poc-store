package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;

public class DefaultVendor implements ISessionVendor {

	@Override
	public IVendor addVendor(String vendorName, String vendorDescription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVendor(String vendorName) {
		// TODO Auto-generated method stub

	}

	@Override
	public IVendor findVendor(String vendorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IPetal> collectPetals(String vendorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IVendor addPetal(IPetal petal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IVendor removePetal(IPetal petal) {
		// TODO Auto-generated method stub
		return null;
	}

}
