package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;

public interface ISessionCapability {

    Capability addCapability(String namespace, Map<String, String> properties);
    void deleteCapability (int capabilityId);
    Capability findCapability (int capabilityId);
    Collection<Petal> collectPetals(int capabilityId);
    Capability addPetal(Petal petal);
    Capability removePetal (Petal petal);

}