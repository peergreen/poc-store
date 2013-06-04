package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.Map;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;

@Stateless
public class DefaultCapability implements ISessionCapability{

    @Override
    public ICapability addCapability(String namespace, Map<String, String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCapability(int capabilityId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ICapability findCapability(int capabilityId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPetal> collectPetals(int capabilityId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICapability addPetal(IPetal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICapability removePetal(IPetal petal) {
        // TODO Auto-generated method stub
        return null;
    }

   

}