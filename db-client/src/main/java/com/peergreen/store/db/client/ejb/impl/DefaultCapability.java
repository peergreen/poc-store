package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.ICapability;

@Stateless
public class DefaultCapability implements ICapability{

    @Override
    public Capability addCapability(String namespace, Collection<String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCapability(int capabilityId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Capability findCapability(int capabilityId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Capability addPetal(Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Capability removePetal(Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

}