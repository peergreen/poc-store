package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.Capability;
import com.peergreen.store.db.client.ejb.entity.api.Category;
import com.peergreen.store.db.client.ejb.entity.api.Group;
import com.peergreen.store.db.client.ejb.entity.api.Petal;

public interface ISesssionPetal {

    Petal addPetal(int petalId, String groupId, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<ISessionRequirement> requirements);

    Petal findPetal(int petalId);

    Collection <Petal> collectPetalsByGroup(Group group); 

    Petal updatePetal(int petalId, String groupId, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<ISessionRequirement> requirements);

    void deletePetal(int petalId); 

    Petal giveAccesToGroup(Group group);

    Petal removeAccesToGroup(Group group);

    Petal addCapability(Capability capability);
    Petal removeCapability(Capability capability);

    Petal addRequirement(ISessionRequirement requirement);
    Petal removeRequirement(ISessionRequirement requirement);

}