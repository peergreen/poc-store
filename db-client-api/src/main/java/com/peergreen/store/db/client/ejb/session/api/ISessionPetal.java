package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.key.primary.PetalId;

public interface ISessionPetal {

    Petal addPetal(PetalId petalId, String description, Category category, 
            Collection<Capability> capabilities, Collection<Requirement> requirements);

    Petal addPetal(Vendor vendor, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<Requirement> requirements);

    Petal findPetal(PetalId petalId);

    Petal findPetal(Vendor vendor, String artifactId,String version);

    Collection<Group> collectGroups(Petal petal); 

    Collection<Capability> collectCapabilities(Petal petal); 

    Collection<Requirement> collectRequirements(Petal petal); 

    Petal updatePetal(Vendor vendor, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<Requirement> requirements);

    void deletePetal(Petal petal); 

    Petal giveAccesToGroup(Petal petal, Group group);

    Petal removeAccesToGroup(Petal petal, Group group);

    Petal addCategory(Petal petal, Category category);

    Petal removeCategory(Petal petal, Category category);

    Category getCategory(Petal petal);

    Petal addCapability(Petal petal, Capability capability);

    Petal removeCapability(Petal petal, Capability capability);

    Petal addRequirement(Petal petal, Requirement requirement);

    Petal removeRequirement(Petal petal, Requirement requirement);

}
