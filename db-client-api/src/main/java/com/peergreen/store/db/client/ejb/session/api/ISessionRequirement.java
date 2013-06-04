package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;

public interface ISessionRequirement {

    Requirement addRequirement(String namespace, Map<String, String> properties);
    void deleteRequirement (int requirementId);
    Requirement findRequirement (int requirementId);
    Collection<Petal> collectPetals(int requirementId);
    Requirement addPetal(Petal petal);
    Requirement removePetal(Petal petal);

}