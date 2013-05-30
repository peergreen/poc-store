package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.session.IRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;


public class DefaultPetal implements ISessionPetal {

    @Override
    public IPetal addPetal(int petalId, String groupId, String artifactId, String version, String description,
            ICategory category, Collection<ICapability> capabilities, Collection<ISessionRequirement> requirements) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPetal findPetal(int petalId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPetal> collectPetalsByGroup(IGroup group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPetal updatePetal(int petalId, String groupId, String artifactId, String version, String description,
            ICategory category, Collection<ICapability> capabilities, Collection<ISessionRequirement> requirements) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deletePetal(int petalId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IPetal giveAccesToGroup(IGroup group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPetal removeAccesToGroup(IGroup group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPetal addCapability(ICapability capability) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPetal removeCapability(ICapability capability) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPetal addRequirement(ISessionRequirement requirement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPetal removeRequirement(ISessionRequirement requirement) {
        // TODO Auto-generated method stub
        return null;
    }


}
