package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.session.IRequirement;

public class DefaultRequirement implements IRequirement {

    @Override
    public Requirement addRequirement(String namespace, Collection<String> properties) {
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
    public Collection<Petal> collectPetals() {
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
