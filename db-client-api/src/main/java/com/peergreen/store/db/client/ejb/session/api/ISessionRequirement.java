package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;

public interface ISessionRequirement {

    IRequirement addRequirement( String namespace, Collection<String> properties);
    void deleteRequirement (int requirementId);
    IRequirement findRequirement (int requirementId);
    Collection<IPetal> collectPetals();
    IRequirement addPetal(IPetal petal);
    IRequirement removePetal(IPetal petal);

}