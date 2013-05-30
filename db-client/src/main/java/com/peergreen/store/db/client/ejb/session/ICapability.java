package com.peergreen.store.db.client.ejb.session;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;

public interface ICapability {

    Capability addCapability( String namespace, Collection<String> properties);
    void deleteCapability (int capabilityId);
    Capability findCapability (int capabilityId);
    Collection<Petal> collectPetals();
    Capability addPetal(Petal petal);
    Capability removePetal (Petal petal);

}