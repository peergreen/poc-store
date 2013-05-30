package com.peergreen.store.db.client.ejb.session;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;

public interface IPetal {

    Petal addPetal(int petalId, String groupId, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<IRequirement> requirements);

    Petal findPetal(int petalId);

    Collection <Petal> collectPetalsByGroup(Group group); 

    Petal updatePetal(int petalId, String groupId, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<IRequirement> requirements);

    void deletePetal(int petalId); 

    Petal giveAccesToGroup(Group group);

    Petal removeAccesToGroup(Group group);

    Petal addCapability(Capability capability);
    Petal removeCapability(Capability capability);

    Petal addRequirement(IRequirement requirement);
    Petal removeRequirement(IRequirement requirement);

}