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

    /**
     * 
     * @param petalId
     * @param description
     * @param category
     * @param capabilities
     * @param requirements
     * @return
     */
    Petal addPetal(PetalId petalId, String description, Category category, 
            Collection<Capability> capabilities, Collection<Requirement> requirements);

    /**
     * 
     * @param vendor
     * @param artifactId
     * @param version
     * @param description
     * @param category
     * @param capabilities
     * @param requirements
     * @return
     */
    Petal addPetal(Vendor vendor, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<Requirement> requirements);

    /**
     * 
     * @param petalId
     * @return
     */
    Petal findPetal(PetalId petalId);

    /**
     * 
     * @param vendor
     * @param artifactId
     * @param version
     * @return
     */
    Petal findPetal(Vendor vendor, String artifactId,String version);

    /**
     * 
     * @param petal
     * @return
     */
    Collection<Group> collectGroups(Petal petal); 

    /**
     * 
     * @param petal
     * @return
     */
    Collection<Capability> collectCapabilities(Petal petal); 

    /**
     * 
     * @param petal
     * @return
     */
    Collection<Requirement> collectRequirements(Petal petal); 

    Petal updatePetal(Petal oldpetal,Vendor vendor, String artifactId, 
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
