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
    public Capability addCapability(String capabilityName, String namespace, Map<String, String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCapability(String capabilityName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Capability findCapability(String capabilityName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(String capabilityName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Capability addPetal(Capability capability, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Capability removePetal(Capability capability, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

   
}