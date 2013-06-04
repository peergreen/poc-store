package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;

public interface ISessionCapability {

    ICapability addCapability(String namespace, Map<String, String> properties);
    void deleteCapability (int capabilityId);
    ICapability findCapability (int capabilityId);
    Collection<IPetal> collectPetals(int capabilityId);
    ICapability addPetal(IPetal petal);
    ICapability removePetal (IPetal petal);

}