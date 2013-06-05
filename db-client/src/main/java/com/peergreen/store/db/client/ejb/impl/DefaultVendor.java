package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;

public class DefaultVendor implements ISessionVendor {

    @Override
    public Vendor addVendor(String vendorName, String vendorDescription) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteVendor(String vendorName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Vendor findVendor(String vendorName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(String vendorName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vendor addPetal(Vendor vendor, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vendor removePetal(Vendor vendor, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

	
}
