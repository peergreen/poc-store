package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.Petal;
import com.peergreen.store.db.client.ejb.entity.api.Requirement;

public interface IRequirement {

    Requirement addRequirement( String namespace, Collection<String> properties);
    void deleteRequirement (int requirementId);
    Requirement findRequirement (int requirementId);
    Collection<Petal> collectPetals();
    Requirement addPetal(Petal petal);
    Requirement removePetal(Petal petal);

}