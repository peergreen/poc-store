package com.peergreen.store.controller.resolver.wrapper.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.osgi.resource.Capability;
import org.osgi.resource.Requirement;

import com.peergreen.store.controller.resolver.wrapper.IPetalWrapper;
import com.peergreen.store.db.client.ejb.entity.Petal;

public class DefaultPetalWrapper implements IPetalWrapper {

    private List<Capability> capabilities;
    private List<Requirement> requirements;
    
    @Override
    public List<Capability> getCapabilities(String namespace) {
        return this.capabilities;
    }

    @Override
    public List<Requirement> getRequirements(String namespace) {
        return this.requirements;
    }

    @Override
    public void initFromPetal(Petal p) {
        Set<com.peergreen.store.db.client.ejb.entity.Capability> listCapabilities = p.getCapabilities();
        Iterator<com.peergreen.store.db.client.ejb.entity.Capability> it = listCapabilities.iterator();
    }

}
