package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.Map;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;

@Stateless
public class DefaultCapability implements ISessionCapability{

    @Override
    public Capability addCapability(String namespace, Map<String, String> properties) {
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
    public Collection<Petal> collectPetals(int capabilityId) {
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