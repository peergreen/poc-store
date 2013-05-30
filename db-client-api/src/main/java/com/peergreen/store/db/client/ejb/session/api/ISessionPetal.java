package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;

public interface ISessionPetal {

    IPetal addPetal(int petalId, String groupId, String artifactId, 
            String version, String description, ICategory category, 
            Collection<ICapability> capabilities, Collection<ISessionRequirement> requirements);

    IPetal findPetal(int petalId);

    Collection <IPetal> collectPetalsByGroup(IGroup group); 

    IPetal updatePetal(int petalId, String groupId, String artifactId, 
            String version, String description, ICategory category, 
            Collection<ICapability> capabilities, Collection<ISessionRequirement> requirements);

    void deletePetal(int petalId); 

    IPetal giveAccesToGroup(IGroup group);

    IPetal removeAccesToGroup(IGroup group);

    IPetal addCapability(ICapability capability);
    IPetal removeCapability(ICapability capability);

    IPetal addRequirement(ISessionRequirement requirement);
    IPetal removeRequirement(ISessionRequirement requirement);
}
