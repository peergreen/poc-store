package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.Capability;
import com.peergreen.store.db.client.ejb.entity.api.Petal;

public interface ICapability {

    Capability addCapability( String namespace, Collection<String> properties);
    void deleteCapability (int capabilityId);
    Capability findCapability (int capabilityId);
    Collection<Petal> collectPetals();
    Capability addPetal(Petal petal);
    Capability removePetal (Petal petal);

}