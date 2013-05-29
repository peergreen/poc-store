package com.peergreen.db.ejb.session;

import java.util.Collection;

import com.peergreen.db.ejb.entity.Capability;
import com.peergreen.db.ejb.entity.Feature;

public interface ICapability {

    Capability addCapability( String namespace, Collection<String> properties);
    void deleteCapability (int capabilityId);
    Capability findCapability (int capabilityId);
    Collection<Feature> collectFeatures();
    Capability addFeature(Feature feature);
    Capability removeFeature (Feature feature);

}