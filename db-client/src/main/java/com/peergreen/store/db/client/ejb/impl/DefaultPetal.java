package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.IPetal;
import com.peergreen.store.db.client.ejb.session.IRequirement;


public class DefaultPetal implements IPetal {

    @Override
    public Petal addPetal(int petalId, String groupId, String artifactId, String version, String description,
            Category category, Collection<Capability> capabilities, Collection<IRequirement> requirements) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Petal findPetal(int petalId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetalsByGroup(Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Petal updatePetal(int petalId, String groupId, String artifactId, String version, String description,
            Category category, Collection<Capability> capabilities, Collection<IRequirement> requirements) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deletePetal(int petalId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Petal giveAccesToGroup(Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Petal removeAccesToGroup(Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Petal addCapability(Capability capability) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Petal removeCapability(Capability capability) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Petal addRequirement(IRequirement requirement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Petal removeRequirement(IRequirement requirement) {
        // TODO Auto-generated method stub
        return null;
    }

}
