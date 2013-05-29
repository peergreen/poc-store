package com.peergreen.db.ejb.session.impl;

import java.util.Collection;

import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.entity.Requirement;
import com.peergreen.db.ejb.session.IRequirement;

public class DefaultRequirement implements IRequirement {

    public Requirement addRequirement(String namespace,
            Collection<String> properties) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteRequirement(int requirementId) {
        // TODO Auto-generated method stub

    }

    public Requirement findRequirement(int requirementId) {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection<Feature> collectFeatures() {
        // TODO Auto-generated method stub
        return null;
    }

    public Requirement addFeature(Feature feature) {
        // TODO Auto-generated method stub
        return null;
    }

    public Requirement removeFeature(Feature feature) {
        // TODO Auto-generated method stub
        return null;
    }

}
