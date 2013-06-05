package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;

public class DefaultRequirement implements ISessionRequirement {

    @Override
    public Requirement addRequirement(String requirementName, String filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRequirement(String requirementName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Requirement findRequirement(String requirementName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(String requirementName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Requirement addPetal(Requirement requirement, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Requirement removePetal(Requirement requirement, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

   

   

}
