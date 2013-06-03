package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;

public class DefaultRequirement implements ISessionRequirement {

    @Override
    public IRequirement addRequirement(String namespace, Collection<String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRequirement(int requirementId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IRequirement findRequirement(int requirementId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPetal> collectPetals(int requirementId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRequirement addPetal(IPetal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRequirement removePetal(IPetal petal) {
        // TODO Auto-generated method stub
        return null;
    }

   

}
