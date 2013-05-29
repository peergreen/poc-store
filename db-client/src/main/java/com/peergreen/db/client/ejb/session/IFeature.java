package com.peergreen.db.ejb.session;

import java.util.Collection;

import com.peergreen.db.ejb.entity.*;

public interface IFeature {

    Feature addFeature(int featureId, String groupId, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<IRequirement> requirements);

    Feature findFeature(int featureId);

    Collection <Feature> collectFeaturesByGroup(Group group); 

    Feature updateFeature(int featureId, String groupId, String artifactId, 
            String version, String description, Category category, 
            Collection<Capability> capabilities, Collection<IRequirement> requirements);

    void deleteFeature(int featureId); 

    Feature giveAccesToGroup(Group group);

    Feature removeAccesToGroup(Group group);

    Feature addCapability(Capability capability);
    Feature removeCapability(Capability capability);

    Feature addRequirement(IRequirement requirement);
    Feature removeRequirement(IRequirement requirement);

}