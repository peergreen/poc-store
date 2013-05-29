package com.peergreen.db.ejb.session.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.db.ejb.entity.Capability;
import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.session.ICapability;

@Stateless
public class DefaultCapability implements ICapability{

    public Capability addCapability(String namespace,
            Collection<String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteCapability(int capabilityId) {
        // TODO Auto-generated method stub

    }

    public Capability findCapability(int capabilityId) {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection<Feature> collectFeatures() {
        // TODO Auto-generated method stub
        return null;
    }

    public Capability addFeature(Feature feature) {
        // TODO Auto-generated method stub
        return null;
    }

    public Capability removeFeature(Feature feature) {
        // TODO Auto-generated method stub
        return null;
    }

}