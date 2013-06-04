package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;

public class DefaultRequirement implements ISessionRequirement {

    @Override
    public Requirement addRequirement(String namespace, Map<String, String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRequirement(int requirementId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Requirement findRequirement(int requirementId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(int requirementId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Requirement addPetal(Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Requirement removePetal(Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

   

}
